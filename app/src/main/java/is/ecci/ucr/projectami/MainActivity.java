package is.ecci.ucr.projectami;

import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import is.ecci.ucr.projectami.Activities.SubScreenMap;
import is.ecci.ucr.projectami.DBConnectors.MongoAdmin;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import is.ecci.ucr.projectami.Bugs.Bug;
import is.ecci.ucr.projectami.Bugs.BugAdater;
import is.ecci.ucr.projectami.DBConnectors.DBAdmin;
import is.ecci.ucr.projectami.SamplingPoints.SamplingPoint;
import is.ecci.ucr.projectami.SamplingPoints.Site;

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

    private LinkedList<Site> sites;
    private LinkedList<SamplingPoint> samplingPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        samplingPoints = new LinkedList<>();
        /*
        * Aqui debe ir la vara de obtener de la base de datos
        *
        *
        * */

        Iterator<Site> iterator = sites.listIterator();
        while(iterator.hasNext()){
            samplingPoints.add(new SamplingPoint(iterator.next()));
        }



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

    private void fillSitesList() {
        sites = new LinkedList<>();
        Site currentSite;
        double latitud = 0;
        double longitud = 0;

        currentSite = new Site("Cascada Azul", latitud, longitud, "");
        sites.add(currentSite);

        currentSite = new Site("Estadio", latitud, longitud, "");
        sites.add(currentSite);

        currentSite = new Site("Ranchito", latitud, longitud, "");
        sites.add(currentSite);

        currentSite = new Site("Mallorca", latitud, longitud, "");
        sites.add(currentSite);

        currentSite = new Site("Horti Fruti", latitud, longitud, "");
        sites.add(currentSite);

        currentSite = new Site("Tacaco", latitud, longitud, "");
        sites.add(currentSite);

        currentSite = new Site("Pío", latitud, longitud, "");
        sites.add(currentSite);

        currentSite = new Site("Pinares", latitud, longitud, "");
        sites.add(currentSite);

        currentSite = new Site("Monteran", latitud, longitud, "");
        sites.add(currentSite);

        currentSite = new Site("Montesacro", latitud, longitud, "");
        sites.add(currentSite);
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
         putMarkets(map);
        map.setOnMarkerClickListener(this);
        map.setOnInfoWindowClickListener(this);


    }

     public void putMarkets(GoogleMap map){
         Iterator<SamplingPoint> iterator = samplingPoints.listIterator();
         while(iterator.hasNext()){
             SamplingPoint currentSamplePoint = iterator.next();
             LatLng latLng = new LatLng(currentSamplePoint.getSite().getLatitude(),currentSamplePoint.getSite().getLongitude());
             map.addMarker(new MarkerOptions().position(latLng).title(currentSamplePoint.getSite().getSiteName()).snippet(currentSamplePoint.getSite().getSiteName()));
         }
     }

     public SamplingPoint getSamplingPoint(String name){
         Iterator<SamplingPoint> iterator = samplingPoints.listIterator();
         SamplingPoint currentSamplePoint;
         while (iterator.hasNext()){
             currentSamplePoint = iterator.next();
             if(currentSamplePoint.getSite().getSiteName().contentEquals(name)){
                 return currentSamplePoint;
             }
         }
         return null;
     }

    @Override
    public boolean onMarkerClick(Marker marker) {
        startActivity(new Intent(MainActivity.this,SubScreenMap.class));

        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        //Poner el evento de ir a ver la información de Rio
        Toast.makeText(this, "Info window clicked",
                Toast.LENGTH_SHORT).show();
    }
}
