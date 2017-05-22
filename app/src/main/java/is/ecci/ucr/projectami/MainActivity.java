package is.ecci.ucr.projectami;

import android.app.FragmentTransaction;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import is.ecci.ucr.projectami.DBConnectors.MongoAdmin;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import is.ecci.ucr.projectami.Bugs.Bug;
import is.ecci.ucr.projectami.Bugs.BugAdater;
import is.ecci.ucr.projectami.DBConnectors.DBAdmin;

import static is.ecci.ucr.projectami.R.id.map;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback ,ComponentCallbacks2, View.OnCreateContextMenuListener , GoogleMap.OnMarkerClickListener ,GoogleMap.OnInfoWindowClickListener {

    private ArrayList<Bug> bugs;
    BugAdater adapter;
    ListView lvAnimals;
    ImageView imagen;
    TextView nombre;
    DBAdmin dbAdmin;
    Fragment f;
    MapFragment mMapFragment;
    CameraUpdate cameraUpdate;

    TextView textView;
    Button getSites;
    MongoAdmin db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        /*
        Context context;
        mMapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.grid2, mMapFragment);
        fragmentTransaction.commit();
        */
        //fillArrayList();
        //lvAnimals = (ListView) findViewById(R.id.lvAnimals);
        //nombre = (TextView) findViewById(R.id.textView);
        //adapter = new BugAdater(this, bugs);
        //lvAnimals.setAdapter(adapter);
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

    @Override
    public void onMapReady(GoogleMap map) {

         LatLng pt = new LatLng(9.86, -84.20);
         map.moveCamera(CameraUpdateFactory.newLatLngZoom(pt, 10));
         putMarket(map,9.86,-84.20);
        map.setOnMarkerClickListener(this);
        map.setOnInfoWindowClickListener(this);


    }


     public void putMarket( GoogleMap map ,double lat, double lon){
         LatLng pt = new LatLng(lat, lon);
         map.addMarker(new MarkerOptions().position(pt).title("Nombre Río").snippet("ver información"));
     }


    @Override
    public boolean onMarkerClick(Marker marker) {
        String title=marker.getTitle();
        Log.d("Prueba ","Imprimiendo seleccionado "+ title);
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        //Poner el evento de ir a ver la información de Rio
        Toast.makeText(this, "Info window clicked",
                Toast.LENGTH_SHORT).show();
    }
}
