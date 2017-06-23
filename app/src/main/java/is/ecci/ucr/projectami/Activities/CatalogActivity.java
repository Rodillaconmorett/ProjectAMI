package is.ecci.ucr.projectami.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import is.ecci.ucr.projectami.Bugs.BugFamily;
import is.ecci.ucr.projectami.Bugs.BugFamilyAdapter;
import is.ecci.ucr.projectami.DBConnectors.MongoAdmin;
import is.ecci.ucr.projectami.R;

/**
 * Created by bjgd9 on 18/6/2017.
 */

public class CatalogActivity extends AppCompatActivity {

    private ArrayList<BugFamily> bugFamilies;
    MongoAdmin mongoAdmin;
    BugFamilyAdapter bugFamilyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        fillManually();
        bugFamilyAdapter= new BugFamilyAdapter(this,bugFamilies);
        ListView listView = (ListView) findViewById(R.id.lvAnimals);
        listView.setAdapter(bugFamilyAdapter);
    }



    private  void fillListWithDB(){

        bugFamilies= null;//acá cambiar por la consulta que me da todos las familias

    }

    private void fillManually(){
        bugFamilies = new ArrayList<>();
        bugFamilies.add(new BugFamily("Amphipoda", 3.0,R.drawable.img_amphipoda));
        bugFamilies.add(new BugFamily("Annelida", 2.0,R.drawable.img_annelida));
        bugFamilies.add(new BugFamily("Amphipoda", 3.0,R.drawable.img_amphipoda));
        bugFamilies.add(new BugFamily("Annelida", 2.0,R.drawable.img_annelida));
        bugFamilies.add(new BugFamily("Amphipoda", 3.0,R.drawable.img_amphipoda));
        bugFamilies.add(new BugFamily("Annelida", 2.0,R.drawable.img_annelida));
        bugFamilies.add(new BugFamily("Amphipoda", 3.0,R.drawable.img_amphipoda));
        bugFamilies.add(new BugFamily("Annelida", 2.0,R.drawable.img_annelida));
    }

    private int getIcoQuality(BugFamily bugFamily){
        if(bugFamily.getPoints() < 3){
            return R.drawable.lifefinder_icon_red;
        }
        else if (bugFamily.getPoints() < 7){
            return R.drawable.lifefinder_icon_orange;
        }
        else {
            return R.drawable.lifefinder_icon_red;
        }
    }


}