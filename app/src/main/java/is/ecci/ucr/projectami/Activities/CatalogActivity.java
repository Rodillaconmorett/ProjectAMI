package is.ecci.ucr.projectami.Activities;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

import is.ecci.ucr.projectami.Bugs.BugFamily;
import is.ecci.ucr.projectami.Bugs.BugFamilyAdapter;
import is.ecci.ucr.projectami.DBConnectors.Consultor;
import is.ecci.ucr.projectami.DBConnectors.JsonParserLF;
import is.ecci.ucr.projectami.DBConnectors.MongoAdmin;
import is.ecci.ucr.projectami.DBConnectors.ServerCallback;
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
        setContentView(R.layout.acivity_catalog);
        fillListWithDB(this.getApplicationContext());
    }



    private  void fillListWithDB(final Context context){
        bugFamilies = new ArrayList<>();
        Consultor.getBugsFamily(new ServerCallback() {
            @Override
            public JSONObject onSuccess(JSONObject result) {
                bugFamilies = JsonParserLF.parseBugsFamilyArrays(result);
                for (int i = 0; i<bugFamilies.size();i++){
                    String imageName = getImageName(bugFamilies.get(i).getNameFamily());
                    int resourceId = getResources().getIdentifier("drawable/"+imageName,null,context.getPackageName());
                    bugFamilies.get(i).setImageID(resourceId);
                }
                bugFamilyAdapter = new BugFamilyAdapter(context,bugFamilies);
                ListView listView = (ListView) findViewById(R.id.lvAnimals);
                listView.setAdapter(bugFamilyAdapter);
                return null;
            }

            @Override
            public JSONObject onFailure(JSONObject result) {
                Toast.makeText(context,"Couldn't load bugs",4);
                return null;
            }
        });
    }

    private String getImageName(String bugFamily){
        String finalString = (bugFamily.replace(":","_")).toLowerCase();
        return "img_"+finalString;
    }

    private void fillManually(){
        bugFamilies = new ArrayList<>();
        bugFamilies.add(new BugFamily("Amphipoda", 3.0,R.drawable.img_amphipoda));
        bugFamilies.add(new BugFamily("Annelida", 2.0,R.drawable.img_annelida));
        bugFamilies.add(new BugFamily("Amphipoda", 3.0,R.drawable.img_amphipoda));
        bugFamilies.add(new BugFamily("Annelida", 2.0,R.drawable.img_annelida));
    }




}