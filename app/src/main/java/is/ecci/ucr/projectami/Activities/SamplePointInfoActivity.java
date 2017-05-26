package is.ecci.ucr.projectami.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import is.ecci.ucr.projectami.DBConnectors.CollectionName;
import is.ecci.ucr.projectami.DBConnectors.JsonParserLF;
import is.ecci.ucr.projectami.DBConnectors.MongoAdmin;
import is.ecci.ucr.projectami.MainActivity;
import is.ecci.ucr.projectami.R;
import is.ecci.ucr.projectami.SamplingPoints.SamplingPoint;
import is.ecci.ucr.projectami.Activities.QuestionsGUI;
import is.ecci.ucr.projectami.SamplingPoints.Site;

/**
 * Created by Daniel on 5/16/2017.
 */

public class SamplePointInfoActivity extends AppCompatActivity implements View.OnClickListener, Serializable {

    private SamplingPoint samplingPoint ;
    private Site site;

    private String initialDate;
    private String finalDate;
    MongoAdmin db;
    private TextView siteName;
    private TextView siteDescription;
    private TextView textTotSpecies;
    private TextView textTotScore;

    private DatePicker datePicker;

    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        String pathSamplePointImage = "path";
        File imgFile = new File(pathSamplePointImage);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView myImage = (ImageView) findViewById(R.id.siteImage);
            myImage.setImageBitmap(myBitmap);
        }
        */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_sample_point);
        Intent intent = getIntent();
        site = (Site) intent.getSerializableExtra("site");
        setSamplingPoint();
        setTextViews();
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        Calendar today = Calendar.getInstance();
        setInitialDate();
        db= new MongoAdmin(this.getApplicationContext());

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(btnBackHandler);

        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH),
            new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                initialDate = String.valueOf(year);
                initialDate += "-";
                if(monthOfYear < 10){
                    initialDate += "0";
                }
                initialDate += String.valueOf(monthOfYear);
                initialDate += "-";
                if(dayOfMonth < 10){
                    initialDate += "0";
                }
                initialDate += String.valueOf(dayOfMonth);
                setSamplingPoint();
                }
            }
        );

    }

    private void setInitialDate(){
        Calendar today = Calendar.getInstance();
        initialDate = String.valueOf(today.get(Calendar.YEAR));
        initialDate += "-";
        int month = today.get(Calendar.MONTH);
        if(month < 10){
            initialDate += "0";
        }
        initialDate += String.valueOf(today.get(Calendar.MONTH));
        initialDate += "-";
        int day = today.get(Calendar.DAY_OF_MONTH);
        if(day < 10){
            initialDate += "0";
        }
        initialDate += String.valueOf(today.get(Calendar.DAY_OF_MONTH));
        finalDate = initialDate;
        setTextViews();
    }

    private void setTextViews(){
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
        db.getSamplesBySiteID(new MongoAdmin.ServerCallback() {
              @Override
              public JSONObject onSuccess(JSONObject result) {
                  ArrayList<String> bugs = JsonParserLF.parseSampleBugList(result);
                  db.getBugsByIdRange(new MongoAdmin.ServerCallback() {
                      @Override
                      public JSONObject onSuccess(JSONObject result) {
                          samplingPoint = new SamplingPoint(site);
                          samplingPoint.setBugList(JsonParserLF.parseBugs(result));
                          samplingPoint.updateScoreAndQualBug();
                          return null;
                      }

                      @Override
                      public JSONObject onFailure(JSONObject result) {
                          return null;
                      }
                  },bugs);
                  return null;
              }

              @Override
              public JSONObject onFailure(JSONObject result) {
                  return null;
              }
          },site.getObjID(),initialDate,finalDate
        );
    }

    View.OnClickListener btnBackHandler = new View.OnClickListener() {
        public void onClick(View v){
            finish();
        }
    };

    @Override
    public void onClick(View v) {

    }
}
