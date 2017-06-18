package is.ecci.ucr.projectami.Activities.Classification;


import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Handler;

import java.util.ArrayList;
import java.util.LinkedList;

import is.ecci.ucr.projectami.Bugs.Bug;

import is.ecci.ucr.projectami.DecisionTree.TreeController;
import is.ecci.ucr.projectami.R;
import is.ecci.ucr.projectami.SampleBugsAdapter;
import is.ecci.ucr.projectami.SamplingPoints.Site;

import static java.security.AccessController.getContext;

public class BugsSampleToRegisterActivity extends AppCompatActivity {


    QuestionsGUIActivity questions;
    private ArrayList<Bug> _bugsListToRegister;
    Button floatingActionButton;
    Site site;
    SampleBugsAdapter adapter;

    TreeController treeControl;

    static private LinkedList<Pair<String, LinkedList<Pair<String, String>>>> retroInfo;
    static Boolean returningFromClassification = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bugs_sample_to_register);

        Intent intent = this.getIntent();
        site = (Site) intent.getExtras().getSerializable("site");                    // Nombre de intent variable

        ListView list = (ListView) findViewById(R.id.lstBugList);

        FloatingActionButton btnAddBug = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        retroInfo = new LinkedList<Pair<String, LinkedList<Pair<String, String>>>>();
        btnAddBug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BugsSampleToRegisterActivity.this, QuestionsGUIActivity.class);
                startActivityForResult(intent, 0XF);
            }
        });

        ImageView btnGoHome = (ImageView) findViewById(R.id.btnGoHome);
        btnGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button btnRegister = (Button) findViewById(R.id.btnRegistrar);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        _bugsListToRegister = new ArrayList<Bug>();

        /*_bugsListToRegister.add(new Bug("Insectivorus", 5, ""));
        _bugsListToRegister.add(new Bug("Cucarachus", 5, ""));
        _bugsListToRegister.add(new Bug("Hormiguis", 5, ""));
        _bugsListToRegister.add(new Bug("Mantis", 5, ""));
        _bugsListToRegister.add(new Bug("Aedes Aegypti", 5, ""));
        _bugsListToRegister.add(new Bug("Mariposarium", 5,""));
        _bugsListToRegister.add(new Bug("Sompo Pa", 5,""));
        _bugsListToRegister.add(new Bug("Scara Bajum", 5, ""));*/

        adapter = new SampleBugsAdapter(this, _bugsListToRegister);
        list.setAdapter(adapter);

        //floatingActionButton = (Button) findViewById(R.id.floatingActionButton);
/*
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Prueba = new Intent(BugsSampleToRegisterActivity.this, QuestionsGUIActivity.class);
                startActivity(Prueba);
            }
        });

       */

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0XF) { // Please, use a final int instead of hardcoded int value
            if (resultCode == RESULT_OK) {
                Boolean value = (Boolean) data.getExtras().getSerializable("returning_from_classification");
                if (value) {
                    registerResult();
                }
            }
        }
    }

    public void addBugToBasket(Bug inBug) {
        _bugsListToRegister.add(inBug);
    }

    public void registerOnScreen() {

    }

    public void registerResult() {
        String currentBug = QuestionsGUIActivity.getCurrentBug();
        LinkedList<Pair<String, String>> currentInfo = QuestionsGUIActivity.getCurrentInfo();

        if (registered(currentBug)) {
            ViewGroup currentView = findItem((ViewGroup) findViewById(R.id.lstBugList), currentBug);
            if (currentView != null) {
                highligthItem(currentView);
                //TextView cantidad = (TextView) currentView.findViewById(R.id.numberPicker);
                //int currentValue = Integer.parseInt((String) cantidad.getText());
                //cantidad.setText(currentValue + 1);
            }
        } else {
            if (!currentBug.equals("Unknown")) {
                addBugToBasket(new Bug(currentBug, 2, ""));
                adapter.notifyDataSetChanged();
            }
        }

        if (currentInfo != null) {
            retroInfo.add(new Pair(currentBug, currentInfo));
        }
    }

    public ViewGroup findItem(ViewGroup currentContext, String family) {
        ViewGroup valueToReturn = null;
        int childCount = currentContext.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = currentContext.getChildAt(i);
            if (child instanceof TextView) {
                if (((TextView) (currentContext.getChildAt(i))).getText().equals(family)) {
                    valueToReturn = currentContext;
                }
            } else if (child instanceof ViewGroup) {
                valueToReturn = findItem((ViewGroup) child, family);
            }
        }
        return valueToReturn;
    }


    public Boolean registered(String currentBug) {
        for (int i = 0; i < _bugsListToRegister.size(); i++) {
            if (currentBug.equals(_bugsListToRegister.get(i).getFamily())) {
                return true;
            }
        }
        return false;
    }

    public void highligthItem(final View view){
        final int color = 0x7DE8B4;
        view.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.getBackground().clearColorFilter();
            }
        }, 2000);
    }

    public static void deleteFromFeedBack(String family) {
        int length = retroInfo.size();
        for (int i = 0; i < length; i++) {
            if (family.equals(retroInfo.get(i).first)) {
                retroInfo.remove(i);
                i = length;
            }
        }
    }
}
