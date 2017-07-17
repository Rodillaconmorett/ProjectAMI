 package is.ecci.ucr.projectami.Activities;

        import android.app.DatePickerDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.media.Image;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AbsListView;
        import android.widget.DatePicker;
        import android.widget.ImageButton;
        import android.widget.ListAdapter;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import org.json.JSONObject;

        import java.io.Serializable;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Iterator;
        import java.util.LinkedList;

        import is.ecci.ucr.projectami.Bugs.Bug;
        import is.ecci.ucr.projectami.Bugs.BugAdapter;
        import is.ecci.ucr.projectami.Bugs.BugFamily;
        import is.ecci.ucr.projectami.Bugs.BugFamilyAdapter;
        import is.ecci.ucr.projectami.DBConnectors.Consultor;
        import is.ecci.ucr.projectami.DBConnectors.JsonParserLF;
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
    private BugFamilyAdapter bugAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_sample_point);
        /************** LOAD SITE *********************/
        Intent intent = getIntent();
        site = (Site) intent.getExtras().getSerializable("site");

        /*********** INITIALIZE THE DATES ***************/
        Calendar today = Calendar.getInstance();
        finalDate = String.valueOf(today.get(Calendar.YEAR));
        finalDate += "-";
        int month = today.get(Calendar.MONTH);
        if(month < 9){
            finalDate += "0";
        }
        finalDate += String.valueOf(today.get(Calendar.MONTH)+1);
        finalDate += "-";
        int day = today.get(Calendar.DAY_OF_MONTH);
        if(day < 10){
            finalDate += "0";
        }
        finalDate += String.valueOf(today.get(Calendar.DAY_OF_MONTH));
        today.add(Calendar.MONTH, -1);
        initialDate = String.valueOf(today.get(Calendar.YEAR));
        initialDate += "-";
        month = today.get(Calendar.MONTH);
        if(month < 9){
            initialDate += "0";
        }
        initialDate += String.valueOf(today.get(Calendar.MONTH)+1);
        initialDate += "-";
        day = today.get(Calendar.DAY_OF_MONTH);
        if(day < 10){
            initialDate += "0";
        }
        initialDate += String.valueOf(today.get(Calendar.DAY_OF_MONTH));

        //bugAdapter = new BugAdapter(getApplicationContext());
        setSamplingPoint();

        /*********** INITIALIZE THE BACK BUTTON *************/
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(btnBackHandler);

        /************** INITIALIZE THE LIST ******************/
        lvBugs = (ListView) findViewById(R.id.lvBugs);
        //lvBugs.setAdapter(bugFamilyAdapter);

        /*************** LOAD THE DATEPICKER **************/
        btnDate = (ImageButton) findViewById(R.id.btnDate);
        btnDate.setOnClickListener(btnDateHandler);
        txtInitialDate = (TextView) findViewById(R.id.txtInitialDate);
        txtFinalDate = (TextView) findViewById(R.id.txtFinalDate);
        txtInitialDate.setText(initialDate);
        txtFinalDate.setText(finalDate);
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
        ArrayList<BugFamily> bugArrayList = castLinked2Array(samplingPoint.getBugList());
        for (int i = 0; i < bugArrayList.size(); i++){
            String imageName = getImageName(bugArrayList.get(i).getNameFamily());
            int resourceId = getResources().getIdentifier("drawable/"+imageName, null, getApplicationContext().getPackageName());
            bugArrayList.get(i).setImageID(resourceId);
        }
        bugAdapter = new BugFamilyAdapter(getApplicationContext(),bugArrayList);
        lvBugs.setAdapter(bugAdapter);
    }

    private ArrayList<BugFamily> castLinked2Array(LinkedList<Bug> linkedList){
        Iterator<Bug> iterator = linkedList.listIterator();
        ArrayList<BugFamily> arrayList =  new ArrayList<>();
        while(iterator.hasNext()) {
            Bug bug = iterator.next();
            arrayList.add(new BugFamily(bug.getFamily(),bug.getScore(),bug.getImageID()));
        }
        return arrayList;
    }

    private String getImageName(String bugFamily){
        String finalString = (bugFamily.replace(":","_")).toLowerCase();
        return "img_"+finalString;
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
                         Toast.makeText(getApplicationContext(),"No se pudo cargar de la base de datos",Toast.LENGTH_SHORT).show();
                         return null;
                     }
                 },bugs);
                 return null;
             }

             @Override
             public JSONObject onFailure(JSONObject result) {
                 Toast.makeText(getApplicationContext(),"No se pudo cargar de la base de datos",Toast.LENGTH_SHORT).show();
                 return null;
             }
         },site.getObjID()
        //);
        ,initialDate,finalDate);
    }

    View.OnClickListener btnBackHandler = new View.OnClickListener() {
        public void onClick(View v){
            finish();
        }
    };

    DatePickerDialog.OnDateSetListener dateI = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            initialDate = String.valueOf(year);
            initialDate += "-";
            if(monthOfYear < 9){
                initialDate += "0";
            }
            initialDate += String.valueOf(monthOfYear + 1);
            initialDate += "-";
            if(dayOfMonth < 10){
                initialDate += "0";
            }
            initialDate += String.valueOf(dayOfMonth);
            txtInitialDate.setText(initialDate);
        }
    };

    DatePickerDialog.OnDateSetListener dateF = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            finalDate = String.valueOf(year);
            finalDate += "-";
            if(monthOfYear < 9){
                finalDate += "0";
            }
            finalDate += String.valueOf(monthOfYear + 1);
            finalDate += "-";
            if(dayOfMonth < 10){
                finalDate += "0";
            }
            finalDate += String.valueOf(dayOfMonth);
            txtFinalDate.setText(finalDate);
            setSamplingPoint();
        }
    };

    View.OnClickListener btnDateHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(SamplePointInfoActivity.this, "Ingrese la fecha inicial y luego la fecha final", 1000).show();
            new DatePickerDialog(SamplePointInfoActivity.this, dateF, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            new DatePickerDialog(SamplePointInfoActivity.this, dateI, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    };

    @Override
    public void onClick(View v) {

    }
}