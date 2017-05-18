package is.ecci.ucr.projectami;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import is.ecci.ucr.projectami.DBConnectors.MongoAdmin;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button getSites;
    MongoAdmin db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prueba_mongoadmin);
        textView = (TextView)findViewById(R.id.pruebaText);
        textView.setText("Prueba");
        getSites = (Button) findViewById(R.id.buttonPrueba);
        getSites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSitesAction();
            }

        });
        db = new MongoAdmin(this.getApplicationContext());
    }

    private void getSitesAction() {
        db.getSites(new MongoAdmin.ServerCallback() {
            @Override
            public JSONObject onSuccess(JSONObject result) {
                textView.setText(result.toString().substring(0,500));
                return result;
            }
        });
    }


}
