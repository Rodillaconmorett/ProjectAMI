package is.ecci.ucr.projectami.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import is.ecci.ucr.projectami.Bugs.BugFamily;
import is.ecci.ucr.projectami.Bugs.BugFamilyAdapter;
import is.ecci.ucr.projectami.DBConnectors.Consultor;
import is.ecci.ucr.projectami.DBConnectors.JsonParserLF;
import is.ecci.ucr.projectami.DBConnectors.MongoAdmin;
import is.ecci.ucr.projectami.DBConnectors.ServerCallback;
import is.ecci.ucr.projectami.R;
import is.ecci.ucr.projectami.SamplingPoints.SamplingPoint;
import is.ecci.ucr.projectami.SamplingPoints.Site;

/**
 * Created by Daniel on 5/16/2017.
 */

public class SamplePointInfoActivity extends AppCompatActivity implements View.OnClickListener, Serializable {

    private SamplingPoint samplingPoint ;
    private Site site;

    private String initialDate;
    private String finalDate;
    private TextView siteName;
    private TextView siteDescription;
    private TextView textTotSpecies;
    private TextView textTotScore;

    private ImageButton btnDate;

    private Calendar calendar;
    private TextView txtInitialDate;
    private TextView txtFinalDate;

    private ImageButton btnBack;

    private ListView lvBugs;
    private ArrayList<BugFamily> bugFamilies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_sample_point);
        /************** LOAD SITE *********************/
        Intent intent = getIntent();
        site = (Site) intent.getExtras().getSerializable("site");

        /*********** INITIALIZE THE DATES ***************/
        Calendar today = Calendar.getInstance();
        initialDate = String.valueOf(today.get(Calendar.YEAR));
        initialDate += "-";
        int month = today.get(Calendar.MONTH);
        if(month < 9){
            initialDate += "0";
        }
        initialDate += String.valueOf(today.get(Calendar.MONTH)+1);
        initialDate += "-";
        int day = today.get(Calendar.DAY_OF_MONTH);
        if(day < 10){
            initialDate += "0";
        }
        initialDate += String.valueOf(today.get(Calendar.DAY_OF_MONTH));
        finalDate = initialDate;
        setSamplingPoint();

        /*********** INITIALIZE THE BACK BUTTON *************/
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(btnBackHandler);

        /************** INITIALIZE THE LIST ******************/
        lvBugs = (ListView) findViewById(R.id.lvBugs);
        fillManually();
        BugFamilyAdapter bugFamilyAdapter = new BugFamilyAdapter(this,bugFamilies);
        lvBugs.setAdapter(bugFamilyAdapter);

        /*************** LOAD THE DATEPICKER **************/
        btnDate = (ImageButton) findViewById(R.id.btnDate);
        btnDate.setOnClickListener(btnDateHandler);
        txtInitialDate = (TextView) findViewById(R.id.txtInitialDate);
        txtFinalDate = (TextView) findViewById(R.id.txtFinalDate);
        calendar = Calendar.getInstance();
    }

    private void refreshData(){
        siteName = (TextView) findViewById(R.id.siteName);
        siteName.setText(samplingPoint.getSite().getSiteName());
        siteDescription = (TextView) findViewById(R.id.siteDescription);
        siteDescription.setText(samplingPoint.getSite().getDescription());
        textTotScore = (TextView) findViewById(R.id.textTotScore);
        textTotScore.setText(String.valueOf(samplingPoint.getScore()));
        textTotSpecies = (TextView) findViewById(R.id.textTotSpecies);
        textTotSpecies.setText(String.valueOf(samplingPoint.getBugList().size()));
    }

    private void setSamplingPoint(){
        Log.i("","");
        Consultor.getSamplesBySiteID(new ServerCallback() {
              @Override
              public JSONObject onSuccess(JSONObject result) {
                  ArrayList<String> bugs = JsonParserLF.parseSampleBugList(result);
                  Consultor.getBugsByIdRange(new ServerCallback() {
                      @Override
                      public JSONObject onSuccess(JSONObject result) {
                          samplingPoint = new SamplingPoint(site);
                          samplingPoint.setBugList(JsonParserLF.parseBugs(result));
                          samplingPoint.updateScoreAndQualBug();
                          refreshData();
                          return null;
                      }

                      @Override
                      public JSONObject onFailure(JSONObject result) {
                          Log.i("","");
                          return null;
                      }
                  },bugs);
                  return null;
              }

              @Override
              public JSONObject onFailure(JSONObject result) {

                  return null;
              }
          },site.getObjID()
        ,initialDate,finalDate);
    }

    View.OnClickListener btnBackHandler = new View.OnClickListener() {
        public void onClick(View v){
            finish();
        }
    };

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        }
    };

    View.OnClickListener btnDateHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new DatePickerDialog(SamplePointInfoActivity.this, date, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            initialDate = String.valueOf(calendar.YEAR);
            initialDate += "-";
            if(calendar.MONTH < 9){
                initialDate += "0";
            }
            initialDate += String.valueOf(calendar.MONTH + 1);
            initialDate += "-";
            if(calendar.DAY_OF_MONTH < 10){
                initialDate += "0";
            }
            initialDate += String.valueOf(calendar.DAY_OF_MONTH);
            txtInitialDate.setText(initialDate);
            new DatePickerDialog(SamplePointInfoActivity.this, date, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            finalDate = String.valueOf(calendar.YEAR);
            finalDate += "-";
            if(calendar.MONTH < 9){
                finalDate += "0";
            }
            finalDate += String.valueOf(calendar.MONTH + 1);
            finalDate += "-";
            if(calendar.DAY_OF_MONTH < 10){
                finalDate += "0";
            }
            finalDate += String.valueOf(calendar.DAY_OF_MONTH);
            txtFinalDate.setText(finalDate);
            setSamplingPoint();
        }
    };

    private void fillManually(){
        bugFamilies = new ArrayList<>();
        bugFamilies.add(new BugFamily("Amphipoda", 3.0,R.drawable.img_amphipoda));
        bugFamilies.add(new BugFamily("Annelida", 2.0,R.drawable.img_annelida));
        bugFamilies.add(new BugFamily("Amphipoda", 3.0,R.drawable.img_amphipoda));
        bugFamilies.add(new BugFamily("Annelida", 2.0,R.drawable.img_annelida));
        bugFamilies.add(new BugFamily("Amphipoda", 3.0,R.drawable.img_amphipoda));
        bugFamilies.add(new BugFamily("Annelida", 2.0,R.drawable.img_annelida));
        bugFamilies.add(new BugFamily("Amphipoda", 3.0,R.drawable.img_amphipoda));
        bugFamilies.add(new BugFamily("Annelida", 2.0,R.drawable.img_annelida));
    }

    @Override
    public void onClick(View v) {

    }
}
