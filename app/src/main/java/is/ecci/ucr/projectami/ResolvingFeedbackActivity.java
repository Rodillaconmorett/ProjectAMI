package is.ecci.ucr.projectami;

import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

public class ResolvingFeedbackActivity extends AppCompatActivity {

    ViewPager viewPager;
    ElementFamilyAdapter adapter;
    String[]  linkedNames;
    boolean bioadmin;
    EditText txtFamilyName;
    Button btnNinguno;
    boolean editText = false;
    TextView showerText;
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
                bioadmin = extras.getBoolean("bioadmin");
            }
        } else {
            linkedNames = savedInstanceState.getStringArray("families");
            bioadmin = savedInstanceState.getBoolean("bioadmin");
        }

        Button btnResolve = (Button) findViewById(R.id.btnResolver);
        btnNinguno = (Button) findViewById(R.id.btnNoSelect);
        txtFamilyName = (EditText) findViewById(R.id.txtboxFamilyName);
        viewPager = (ViewPager) findViewById(R.id.familypager);
        showerText = (TextView) findViewById(R.id.txtTextShower);

        if (bioadmin){
            btnNinguno.setText("Agregar nuevo");
            btnNinguno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (editText){
                        txtFamilyName.setVisibility(View.INVISIBLE);
                        viewPager.setVisibility(View.VISIBLE);

                        btnNinguno.setText("Agregar Nuevo");
                        editText = false;
                    }else{
                        txtFamilyName.setVisibility(View.VISIBLE);
                        viewPager.setVisibility(View.INVISIBLE);

                        btnNinguno.setText("Buscar de nuevo");
                        editText = true;
                    }
                }
            });
        } else {
            showerText.setText("Lo sentimos, no pudimos encontrar el MI que buscabas.\n ¿Nos sugerirías una de estas respuestas?");
            btnNinguno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setResult(Activity.RESULT_CANCELED);
                    finish();
                }
            });
        }


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


        btnResolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editText)
                terminarActividad(linkedNames[viewPager.getCurrentItem()]);
                else terminarActividad(txtFamilyName.getText().toString());
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
