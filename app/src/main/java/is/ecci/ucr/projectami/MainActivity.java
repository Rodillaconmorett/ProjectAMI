package is.ecci.ucr.projectami;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import is.ecci.ucr.projectami.Bugs.Bug;
import is.ecci.ucr.projectami.Bugs.BugAdater;
import is.ecci.ucr.projectami.DBConnectors.DBAdmin;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Bug> bugs;
    BugAdater adapter;
    ListView lvAnimals;
    ImageView imagen;
    TextView nombre;
    DBAdmin dbAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_catalog);
        Context context;

        fillArrayList();
        lvAnimals = (ListView) findViewById(R.id.lvAnimals);
        nombre = (TextView) findViewById(R.id.textView);
        adapter = new BugAdater(this, bugs);
        lvAnimals.setAdapter(adapter);
        //lvAnimals.setOnItemClickListener(this);

        
    }


    private void fillArrayList() {
        //this filll would be  with the bugs that are in the DB
        bugs = new ArrayList<>();
        bugs.add(new Bug("aguila", R.drawable.aguila));
        bugs.add(new Bug("ballena", R.drawable.ballena));
        bugs.add(new Bug("caballo", R.drawable.caballo));
        bugs.add(new Bug("camaleon", R.drawable.camaleon));
        bugs.add(new Bug("canario", R.drawable.canario));
        bugs.add(new Bug("cerdo", R.drawable.cerdo));
        bugs.add(new Bug("delfin", R.drawable.delfin));
        bugs.add(new Bug("gato", R.drawable.gato));
        bugs.add(new Bug("iguana", R.drawable.iguana));
        bugs.add(new Bug("lince", R.drawable.lince));
        bugs.add(new Bug("lobo", R.drawable.lobo_9));
        bugs.add(new Bug("morena", R.drawable.morena));
        bugs.add(new Bug("orca", R.drawable.orca));
        bugs.add(new Bug("perro", R.drawable.perro));
        bugs.add(new Bug("vaca", R.drawable.vaca));
    }
}
