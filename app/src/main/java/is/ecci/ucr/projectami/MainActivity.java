package is.ecci.ucr.projectami;
import is.ecci.ucr.projectami.Animal;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayList<Animal> animales;
    AnimalesAdapter adapter;
    ListView lvAnimals;
    ImageView imagen;
    TextView nombre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rellenarArrayList();
        lvAnimals = (ListView) findViewById(R.id.lvAnimals);
        nombre = (TextView) findViewById(R.id.textView);
        adapter = new AnimalesAdapter(this, animales);
        lvAnimals.setAdapter(adapter);
        lvAnimals.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position,
                            long ID) {
        // Al hacer click sobre uno de los items del ListView mostramos los
        // datos en los TextView.

        nombre.setText(animales.get(position).getNombre());
        String nombre = animales.get(position).getNombre();

    }

    private void rellenarArrayList() {
        animales = new ArrayList<>();
        animales.add(new Animal("aguila", R.drawable.aguila));
        animales.add(new Animal("ballena", R.drawable.ballena));
        animales.add(new Animal("caballo", R.drawable.caballo));
        animales.add(new Animal("camaleon", R.drawable.camaleon));
        animales.add(new Animal("canario", R.drawable.canario));
        animales.add(new Animal("cerdo", R.drawable.cerdo));
        animales.add(new Animal("delfin", R.drawable.delfin));
        animales.add(new Animal("gato", R.drawable.gato));
        animales.add(new Animal("iguana", R.drawable.iguana));
        animales.add(new Animal("lince", R.drawable.lince));
        animales.add(new Animal("lobo", R.drawable.lobo_9));
        animales.add(new Animal("morena", R.drawable.morena));
        animales.add(new Animal("orca", R.drawable.orca));
        animales.add(new Animal("perro", R.drawable.perro));
        animales.add(new Animal("vaca", R.drawable.vaca));
    }
}
