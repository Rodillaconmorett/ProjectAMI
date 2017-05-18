package is.ecci.ucr.projectami;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;

import is.ecci.ucr.projectami.DBConnectors.MongoAdmin;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MongoAdmin db = new MongoAdmin(this.getApplicationContext());
        db.getSites();
    }

}
