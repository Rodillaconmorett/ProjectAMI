package is.ecci.ucr.projectami;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.LinkedList;

public class ResolvingFeedbackActivity extends AppCompatActivity {

    ViewPager viewPager;
    ElementFamilyAdapter adapter;
    String[]  linkedNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resolving_feedback);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                linkedNames= null;
            } else {
                linkedNames = extras.getStringArray("families");
            }
        } else {
            linkedNames = savedInstanceState.getStringArray("families");
        }

        viewPager = (ViewPager) findViewById(R.id.familypager);

        ImageView btnGoHome = (ImageView) findViewById(R.id.btnBackToQuestions);
        btnGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        int images[] = new int[linkedNames.length];
        int i = 0;
        for (String familyId:linkedNames){
            images[i] = getResources().getIdentifier("drawable/img_" + familyId.replace(':','_').toLowerCase(), null, this.getApplicationContext().getPackageName());
            i++;
        }

        //String names[] = linkedNames.toArray(new String[linkedNames.size()]);

        adapter = new ElementFamilyAdapter(this, images, linkedNames);
        viewPager.setAdapter(adapter);

        Button btnResolve = (Button) findViewById(R.id.btnResolver);
        btnResolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                terminarActividad(linkedNames[viewPager.getCurrentItem()]);
            }
        });


    }

    public void terminarActividad(String family) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("returning_from_resolving", family);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

}
