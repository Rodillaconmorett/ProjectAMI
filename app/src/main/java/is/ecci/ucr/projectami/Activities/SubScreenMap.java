package is.ecci.ucr.projectami.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import is.ecci.ucr.projectami.R;
import is.ecci.ucr.projectami.SamplingPoints.SamplingPoint;

/**
 * Created by Daniel on 5/20/2017.
 */

public class SubScreenMap extends Activity {
    SamplingPoint samplingPoint;

    Button buttonInfo;
    Button buttonRegister;

    TextView siteName;
    TextView siteScore;
    TextView textHghQualBugs;
    TextView textMedQualBugs;
    TextView textLowQualBugs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_screen_map);
        Intent intent = getIntent();
        samplingPoint = intent.getParcelableExtra("samplingPoint");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        getWindow().setLayout(1000, 1000);
        getWindow().setLayout(1000,1000);


        siteName = (TextView) findViewById(R.id.siteName);
        /*
        siteName.setText(samplingPoint.getSite().getSiteName());
        */
        siteScore = (TextView) findViewById(R.id.siteScore);
        /*
        siteScore.setText(String.valueOf(samplingPoint.getScore()));
        */
        textHghQualBugs = (TextView) findViewById(R.id.textHghQualBugs);
        /*
        textHghQualBugs.setText(samplingPoint.getHghQualBug());
        */
        textMedQualBugs = (TextView) findViewById(R.id.textMedQualBugs);
        /*
        textMedQualBugs.setText(samplingPoint.getMedQualBug());
        */
        textLowQualBugs = (TextView) findViewById(R.id.textLowQualBugs);
        /*
        textLowQualBugs.setText(samplingPoint.getLowQualBug());
        */
        buttonInfo = (Button) findViewById(R.id.buttonInfo);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonInfo.setOnClickListener(btnInfoHandler);
        buttonRegister.setOnClickListener(btnRegstrHandler);

    }

    View.OnClickListener btnInfoHandler = new View.OnClickListener() {
        public void onClick(View v){
            Intent intent = new Intent(SubScreenMap.this, SamplePointInfoActivity.class);
            intent.putExtra("SamplePointInfoActivity", (Parcelable) samplingPoint);
            startActivity(intent);
        }
    };

    View.OnClickListener btnRegstrHandler = new View.OnClickListener() {
        public void onClick(View v){
            Intent intent = new Intent(SubScreenMap.this, QuestionsGUI.class);
            intent.putExtra("SamplePointInfoActivity", (Parcelable) samplingPoint);
            startActivity(intent);
        }
    };
}
