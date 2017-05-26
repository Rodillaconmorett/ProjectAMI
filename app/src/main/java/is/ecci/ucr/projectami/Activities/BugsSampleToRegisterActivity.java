package is.ecci.ucr.projectami.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import is.ecci.ucr.projectami.Bugs.Bug;

import is.ecci.ucr.projectami.R;
import is.ecci.ucr.projectami.SampleBugsAdapter;

public class BugsSampleToRegisterActivity extends AppCompatActivity {

    private ArrayList<Bug> _bugsListToRegister;
    Button floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bugs_sample_to_register);
        ListView list = (ListView) findViewById(R.id.lstBugList);

        _bugsListToRegister = new ArrayList<Bug>();
        _bugsListToRegister.add(new Bug("Insectivorus", 5, ""));
        _bugsListToRegister.add(new Bug("Cucarachus", 5, ""));
        _bugsListToRegister.add(new Bug("Hormiguis", 5, ""));
        _bugsListToRegister.add(new Bug("Mantis", 5, ""));
        _bugsListToRegister.add(new Bug("Aedes Aegypti", 5, ""));
        _bugsListToRegister.add(new Bug("Mariposarium", 5,""));
        _bugsListToRegister.add(new Bug("Sompo Pa", 5,""));
        _bugsListToRegister.add(new Bug("Scara Bajum", 5, ""));


        SampleBugsAdapter adapter = new SampleBugsAdapter(this, _bugsListToRegister);
        list.setAdapter(adapter);
        //floatingActionButton = (Button) findViewById(R.id.floatingActionButton);
/*
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Prueba = new Intent(BugsSampleToRegisterActivity.this, QuestionsGUI.class);
                startActivity(Prueba);
            }
        });
*/
    }

    public void addBugToBasket(Bug inBug){
        _bugsListToRegister.add(inBug);
    }


}
