package is.ecci.ucr.projectami.Activities.UserLog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import org.json.JSONObject;
import java.util.ArrayList;
import is.ecci.ucr.projectami.DBConnectors.Consultor;
import is.ecci.ucr.projectami.DBConnectors.JsonParserLF;
import is.ecci.ucr.projectami.DBConnectors.ServerCallback;
import is.ecci.ucr.projectami.R;


public class LogUserActivity extends AppCompatActivity {
    private ArrayList<SampleLog> _sampleList;
    LogUserAdapter adapter;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_user);
        /*Intent intent = this.getIntent();
        userID = ((User) intent.getExtras().getSerializable("user")).getEmail();                    // Nombre de intent variable
         */
        userID = "alain.cruzc@gmail.com";
        ListView list = (ListView) findViewById(R.id.lstSampleList);
        Consultor.getSamplesByUserID(new ServerCallback() {
            @Override
            public JSONObject onSuccess(JSONObject result) {
                _sampleList = JsonParserLF.parseSamples(result);
                return null;
            }
            @Override
            public JSONObject onFailure(JSONObject result) {
                Log.d("Error","Error with samples");
                return null;
            }
        },userID);
        adapter = new LogUserAdapter(this, _sampleList);
        list.setAdapter(adapter);
    }
}

