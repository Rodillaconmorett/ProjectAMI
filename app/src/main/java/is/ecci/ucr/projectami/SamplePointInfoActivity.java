package is.ecci.ucr.projectami;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import is.ecci.ucr.projectami.SamplingPoints.SamplingPoint;

/**
 * Created by Daniel on 5/16/2017.
 */

public class SamplePointInfoActivity extends AppCompatActivity {

    private SamplingPoint samplingPoint;
/*
    public SamplePointInfoActivity(SamplingPoint samplingPoint){
        this.samplingPoint = samplingPoint;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String pathSamplePointImage = "path";
        File imgFile = new File(pathSamplePointImage);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView myImage = (ImageView) findViewById(R.id.siteImage);
            myImage.setImageBitmap(myBitmap);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_sample_point);
        TextView textView;
        // Definir nombre del sitio
        textView = (TextView) findViewById(R.id.siteName);
        textView.setText(samplingPoint.getSite().getSiteName());
        // Definir descripcion
        textView = (TextView) findViewById(R.id.siteDescription);
        textView.setText(samplingPoint.getSite().getDescription());
        // Definir puntaje total
        textView = (TextView) findViewById(R.id.textTotScore);
        textView.setText(String.valueOf(samplingPoint.getScore()));
        // Definir total de especies
        textView = (TextView) findViewById(R.id.textTotSpecies);
        textView.setText(String.valueOf(samplingPoint.getBugList().size()));
    }
}
