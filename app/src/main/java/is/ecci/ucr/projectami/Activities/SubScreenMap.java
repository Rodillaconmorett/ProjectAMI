package is.ecci.ucr.projectami.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

import is.ecci.ucr.projectami.Bugs.Bug;
import is.ecci.ucr.projectami.DBConnectors.JsonParserLF;
import is.ecci.ucr.projectami.DBConnectors.MongoAdmin;
import is.ecci.ucr.projectami.MainActivity;
import is.ecci.ucr.projectami.R;
import is.ecci.ucr.projectami.SamplingPoints.SamplingPoint;
import is.ecci.ucr.projectami.SamplingPoints.Site;

/**
 * Created by Daniel on 5/20/2017.
 */

public class SubScreenMap extends Activity {
    SamplingPoint samplingPoint;
    Site site;

    Button buttonInfo;
    Button buttonRegister;

    TextView siteName;
    TextView siteScore;
    TextView textHghQualBugs;
    TextView textMedQualBugs;
    TextView textLowQualBugs;
    MongoAdmin db;
    RelativeLayout container_all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_screen_map);
        site = MainActivity._actualSite;
        Intent intent = getIntent();

        site = (Site)intent.getExtras().getSerializable("site");

        samplingPoint = new SamplingPoint(site);

        db= new MongoAdmin(this.getApplicationContext());
        setSamplingPoint();
        buttonInfo = (Button) findViewById(R.id.buttonInfo);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        buttonInfo.setOnClickListener(btnInfoHandler);
        buttonRegister.setOnClickListener(btnRegstrHandler);





    }

    private void setSamplingPoint(){
        db.getSamplesBySiteID(new MongoAdmin.ServerCallback() {
            @Override
            public JSONObject onSuccess(JSONObject result) {
                ArrayList<String> bugs = JsonParserLF.parseSampleBugList(result);
                db.getBugsByIdRange(new MongoAdmin.ServerCallback() {
                    @Override
                    public JSONObject onSuccess(JSONObject result) {
                        Log.i("","Yellgue");
                        LinkedList<Bug> bugs = JsonParserLF.parseBugs(result);
                        samplingPoint.setBugList(bugs);
                        samplingPoint.updateScoreAndQualBug();

                        siteName = (TextView) findViewById(R.id.siteName);
                        siteName.setText(samplingPoint.getSite().getSiteName());
                        siteScore = (TextView) findViewById(R.id.siteScore);
                        siteScore.setText(String.valueOf(samplingPoint.getScore()));
                        textHghQualBugs = (TextView) findViewById(R.id.textHghQualBugs);
                        textHghQualBugs.setText(String.valueOf(samplingPoint.getHghQualBug()));
                        textMedQualBugs = (TextView) findViewById(R.id.textMedQualBugs);
                        textMedQualBugs.setText(String.valueOf(samplingPoint.getMedQualBug()));
                        textLowQualBugs = (TextView) findViewById(R.id.textLowQualBugs);
                        textLowQualBugs.setText(String.valueOf(samplingPoint.getLowQualBug()));
                        buttonInfo = (Button) findViewById(R.id.buttonInfo);
                        buttonRegister = (Button) findViewById(R.id.buttonRegister);

                        return null;
                    }

                    @Override
                    public JSONObject onFailure(JSONObject result) {
                        Log.i("","Falle");
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
        );
    }

    View.OnClickListener btnInfoHandler = new View.OnClickListener() {
        public void onClick(View v){
            Intent intent = new Intent(SubScreenMap.this, SamplePointInfoActivity.class);
            intent.putExtra("site", site);
            startActivity(intent);
        }
    };

    View.OnClickListener btnRegstrHandler = new View.OnClickListener() {
        public void onClick(View v){
            Intent intent = new Intent(SubScreenMap.this, BugsSampleToRegisterActivity.class);
            intent.putExtra("site", site);
            startActivity(intent);
        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        container_all = (RelativeLayout) findViewById(R.id.container_all);
        getWindow().setLayout(container_all.getWidth(),container_all.getHeight());
    }
}
