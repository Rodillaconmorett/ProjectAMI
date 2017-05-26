package is.ecci.ucr.projectami.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;

import is.ecci.ucr.projectami.R;
import is.ecci.ucr.projectami.SamplingPoints.SamplingPoint;

/**
 * Created by Daniel on 5/16/2017.
 */

public class SamplePointInfoActivity extends AppCompatActivity {

    private SamplingPoint samplingPoint ;

    TextView siteName;
    TextView siteDescription;
    TextView textTotSpecies;
    TextView textTotScore;
    ListView datesList;

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
        samplingPoint = intent.getParcelableExtra("samplingPoint");

        siteName = (TextView) findViewById(R.id.siteName);
        siteName.setText(samplingPoint.getSite().getSiteName());
        siteDescription = (TextView) findViewById(R.id.siteDescription);
        siteDescription.setText(samplingPoint.getSite().getDescription());
        textTotScore = (TextView) findViewById(R.id.textTotScore);
        textTotScore.setText(String.valueOf(samplingPoint.getScore()));
        textTotSpecies = (TextView) findViewById(R.id.textTotSpecies);
        textTotSpecies.setText(String.valueOf(samplingPoint.getBugList().size()));
        datesList = (ListView) findViewById(R.id.datesList);


    }
}
