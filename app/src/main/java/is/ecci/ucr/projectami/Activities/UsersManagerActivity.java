package is.ecci.ucr.projectami.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.sip.SipAudioCall;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

import is.ecci.ucr.projectami.Bugs.BugFamilyAdapter;
import is.ecci.ucr.projectami.DBConnectors.CollectionName;
import is.ecci.ucr.projectami.DBConnectors.Consultor;
import is.ecci.ucr.projectami.DBConnectors.JsonParserLF;
import is.ecci.ucr.projectami.DBConnectors.ServerCallback;
import is.ecci.ucr.projectami.DBConnectors.UserManagers;
import is.ecci.ucr.projectami.R;
import is.ecci.ucr.projectami.Users.User;
import is.ecci.ucr.projectami.Users.UserAdapter;

public class UsersManagerActivity extends AppCompatActivity {

    UserAdapter userAdapter;
    ArrayList<User> userArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_manager);
        fillListWithDB(this.getApplicationContext());
    }

    private  void fillListWithDB(final Context context){
        userArray = new ArrayList<>();
        UserManagers.getUsers(new ServerCallback() {
            @Override
            public JSONObject onSuccess(JSONObject result) {
                userArray = JsonParserLF.parseArrayUsers(result);
                userAdapter = new UserAdapter(getApplicationContext(),userArray);
                ListView listView = (ListView) findViewById(R.id.user_list);
                listView.setAdapter(userAdapter);
                return null;
            }
            @Override
            public JSONObject onFailure(JSONObject result) {
                Toast.makeText(context,"Couldn't load users. Please refresh the data.",4).show();
                return null;
            }
        });
    }
}
