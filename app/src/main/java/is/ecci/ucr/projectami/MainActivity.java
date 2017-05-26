package is.ecci.ucr.projectami;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import is.ecci.ucr.projectami.Activities.QuestionsGUI;
import is.ecci.ucr.projectami.Activities.SamplePointInfoActivity;
import is.ecci.ucr.projectami.Activities.SubScreenMap;
import is.ecci.ucr.projectami.DBConnectors.CollectionName;
import is.ecci.ucr.projectami.DBConnectors.JsonParserLF;
import is.ecci.ucr.projectami.DBConnectors.MongoAdmin;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import is.ecci.ucr.projectami.Bugs.Bug;
import is.ecci.ucr.projectami.Bugs.BugAdapter;
import is.ecci.ucr.projectami.SamplingPoints.SamplingPoint;
import is.ecci.ucr.projectami.SamplingPoints.Site;

import static is.ecci.ucr.projectami.R.id.map;
import static is.ecci.ucr.projectami.R.id.pruebaText;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback ,ComponentCallbacks2, View.OnCreateContextMenuListener , GoogleMap.OnMarkerClickListener ,GoogleMap.OnInfoWindowClickListener ,NavigationView.OnNavigationItemSelectedListener, Serializable{

    private ArrayList<Bug> bugs;

    BugAdapter adapter;
    ListView lvAnimals;
    ImageView imagen;
    TextView nombre;
    Fragment f;
    MapFragment mMapFragment;
    CameraUpdate cameraUpdate;

    TextView textView;
    Button getSites;
    MongoAdmin db;

    GoogleMap mMap;

    private ArrayList<Site> sites;
    private LinkedList<SamplingPoint> samplingPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db= new MongoAdmin(this.getApplicationContext());//creación del objeto
        samplingPoints = new LinkedList<>();
        /*
        * Aqui debe ir la vara de obtener de la base de datos
        *
        *
        * */

/*
        Iterator<Site> iterator = sites.listIterator();
        while(iterator.hasNext()){
            samplingPoints.add(new SamplingPoint(iterator.next()));
        }
*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //set map
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        //set navegation

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        /*bugs = new ArrayList<>();
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
        bugs.add(new Bug("vaca", R.drawable.vaca));*/
    };

    public void loadMarks(){
        db.getColl(new MongoAdmin.ServerCallback() {
            @Override
            public JSONObject onSuccess(JSONObject result) {
                sites= JsonParserLF.parseSites(result);

                for(int i=0; i< sites.size(); i++) {
                    Site tempSite=sites.get(i);
                    double lat = tempSite.getLatitude();
                    double lon = tempSite.getLongitude();
                    String name = tempSite.getSiteName();
                    String info = tempSite.getObjID()+","+tempSite.getDescription();
                    putMarket(mMap, lat, lon, name, info);
                }

                return null;
            }

            @Override
            public JSONObject onFailure(JSONObject result) {
                //Mensaje de Fallo
                return null;
            }
        }, CollectionName.SITE);

    }
    @Override
    public void onMapReady(GoogleMap map) {
        mMap=map;

        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker arg0) {

                // Getting view from the layout file info_window_layout
                View v = getLayoutInflater().inflate(R.layout.sub_screen_map, null);

                // Getting the position from the marker
                LatLng latLng = arg0.getPosition();

                // Returning the view containing InfoWindow contents
                return v;

            }
        });
        LatLng pt = new LatLng(9.86, -84.20);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pt, 10));
        //putMarket(map,9.86,-84.20,"hola","mundo");
        map.setOnMarkerClickListener(this);
        map.setOnInfoWindowClickListener(this);
        loadMarks();
    }


    public void putMarket( GoogleMap map ,double lat, double lon, String name, String info){
        LatLng pt = new LatLng(lat, lon);
        map.addMarker(new MarkerOptions().position(pt).title(name).snippet(info));
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
        Intent Prueba = new Intent(MainActivity.this, SubScreenMap.class);
        //mandar el site
        double lat=marker.getPosition().latitude;
        double lon=marker.getPosition().longitude;
        String name=marker.getTitle();
        int mid=marker.getSnippet().indexOf(",");
        String objId=marker.getSnippet().substring(0,mid);
        String info=marker.getSnippet().substring(mid,marker.getSnippet().length());
        Site site=new Site(objId,name,lat,lon,info);
        Prueba.putExtra("site",(Serializable) site);
        startActivity(Prueba);
        return false;
    }




    @Override
    public void onInfoWindowClick(Marker marker) {
        //Poner el evento de ir a ver la información de Rio
        Toast.makeText(this, "Info window clicked",
                Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("StatementWithEmptyBody")

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch(id){
            case R.id.map_btn:
                break;
            case R.id.add_bug_btn:
                break;
            case R.id.catalog_btn:
                break;
            case R.id.settings_btn:
                break;

        }
        /*
        if (id == R.id.nav_camera) {
            Intent Prueba = new Intent(MainActivity.this, SubScreenMap.class);
        if (id == R.id.frame_map) {
            Intent Prueba = new Intent(MainActivity.this, SamplePointInfoActivity.class);
            startActivity(Prueba);
        } else if (id == R.id.nav_gallery) {
            Intent Prueba = new Intent(MainActivity.this, QuestionsGUI.class);
            startActivity(Prueba);
        } else if (id == R.id.nav_slideshow) {
            Intent Prueba = new Intent(MainActivity.this, SubScreenMap.class);
            startActivity(Prueba);
        } else if (id == R.id.nav_manage) {
            Intent Prueba = new Intent(MainActivity.this, SubScreenMap.class);
            startActivity(Prueba);
        } else if (id == R.id.nav_share) {
            Intent Prueba = new Intent(MainActivity.this, SubScreenMap.class);
            startActivity(Prueba);
        } else if (id == R.id.nav_send) {
            Intent Prueba = new Intent(MainActivity.this, SubScreenMap.class);
            startActivity(Prueba);
        }
        */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}
