package is.ecci.ucr.projectami.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONException;
import org.json.JSONObject;

import is.ecci.ucr.projectami.DBConnectors.JsonParserLF;
import is.ecci.ucr.projectami.DBConnectors.ServerCallback;
import is.ecci.ucr.projectami.DBConnectors.UserManagers;
import is.ecci.ucr.projectami.LogInfo;
import is.ecci.ucr.projectami.MainActivity;
import is.ecci.ucr.projectami.R;
import is.ecci.ucr.projectami.Users.User;

/**
 * Created by bjgd9 on 12/6/2017.
 */


public class LogActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener  {

    public  GoogleApiClient googleApiClient;//to create the login
    private static GoogleSignInOptions googleSignInOptions;// loggin options
    private SignInButton signInButton;
    private Button logIn;
    private Button signOut;
    private static Boolean  logginWithGmailAPI =false;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_log_activity);

        googleSignInOptions= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)// lo que se va a obtener del cliente
                .requestEmail()
                .build();

        //Login
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,  this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();


        signInButton = (SignInButton) findViewById(R.id.btnLogGoogle);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,777);
            }
        });

        signOut = (Button) findViewById(R.id.btnSignOut);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                signOut();
                signOut.setVisibility(View.INVISIBLE);
            }
        });






        logIn = (Button) findViewById(R.id.buttonLogin);

        logIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText userText = (EditText)findViewById(R.id.userInput);
                EditText passText = (EditText)findViewById(R.id.passwordInput);
                String user, pass;
                if(userText.getText()!=null && passText.getText()!=null) {
                    user = userText.getText().toString();
                    pass = passText.getText().toString();
                    logInUser(user, pass);
                }
            }
        });
    }

    public void logInUser(String user, String pass){
        UserManagers.logInUser(user, pass, new ServerCallback() {
            @Override
            public JSONObject onSuccess(JSONObject result) {
                User user = JsonParserLF.parseUsers(result);
                LogInfo.setEmail(user.getEmail());
                LogInfo.setPassword(user.getPassword());
                if(user.getFirstName()!=null && user.getLastName()!=null){
                    LogInfo.setFirstName(user.getFirstName());
                    LogInfo.setLastName(user.getLastName());
                    Toast.makeText(getApplicationContext(),"Hello, "+LogInfo.getFirstName()+" "+LogInfo.getLastName()+"!",Toast.LENGTH_SHORT).show();
                    //signOut.setVisibility(View.VISIBLE);
                }
                Toast.makeText(getApplicationContext(),"Hello, "+LogInfo.getEmail()+"!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LogActivity.this, MainActivity.class);
                //signOut.setVisibility(View.VISIBLE);
                startActivity(intent);
                return null;
            }

            @Override
            public JSONObject onFailure(JSONObject result) {
                Toast.makeText(getApplicationContext(),"Log failed. Please, re-try.",Toast.LENGTH_SHORT).show();
                return null;
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {// to show message to user: error case
        Toast.makeText(this,"Log fail manejado",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==777){
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Toast.makeText(this,"Log status "+ " "+ googleSignInResult.isSuccess(),Toast.LENGTH_SHORT).show();
            handleSignal(googleSignInResult);
        }

    }

    private void handleSignal(GoogleSignInResult googleSignInResult) {
        if(googleSignInResult.isSuccess()){
            loadPrincipalActivity(googleSignInResult);

            logginWithGmailAPI=true;

        }else{
            Toast.makeText(this,"Log fail",Toast.LENGTH_SHORT).show();
        }
    }

    private void loadPrincipalActivity(GoogleSignInResult googleSignInResult) {
        GoogleSignInAccount acct = googleSignInResult.getSignInAccount();
        String personName = acct.getDisplayName();
        String personGivenName = acct.getGivenName();
        String personFamilyName = acct.getFamilyName();
        String personEmail = acct.getEmail();
        String personId = acct.getId();
        Uri personPhoto = acct.getPhotoUrl();
        final User user = new User(personEmail,personId,personGivenName,personFamilyName);
        UserManagers.logInUser(personEmail, personId, new ServerCallback() {
            @Override
            public JSONObject onSuccess(JSONObject result) {
                User user = JsonParserLF.parseUsers(result);
                LogInfo.setEmail(user.getEmail());
                LogInfo.setPassword(user.getPassword());
                if(user.getFirstName()!=null && user.getLastName()!=null){
                    LogInfo.setFirstName(user.getFirstName());
                    LogInfo.setLastName(user.getLastName());
                    Toast.makeText(getApplicationContext(),"Hello, "+LogInfo.getFirstName()+" "+LogInfo.getLastName()+"!",Toast.LENGTH_SHORT).show();
                }
                LogInfo.setRoles(user.getRoles());
                Toast.makeText(getApplicationContext(),"Hello, "+LogInfo.getEmail()+"!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LogActivity.this, MainActivity.class);
                startActivity(intent);
                return null;
            }
            @Override
            public JSONObject onFailure(JSONObject result) {
                String resultString = "";
                try {
                    resultString = result.getString("failed").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(resultString.matches(".*AuthFailureError")){
                    UserManagers.addNewUser(user, new ServerCallback() {
                        @Override
                        public JSONObject onSuccess(JSONObject result) {
                            Toast.makeText(getApplicationContext(),"Hello, "+LogInfo.getEmail()+"!",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LogActivity.this, MainActivity.class);
                            startActivity(intent);
                            return null;
                        }

                        @Override
                        public JSONObject onFailure(JSONObject result) {
                            Toast.makeText(getApplicationContext(),"Sorry, there was a problem. Please, re-try.",Toast.LENGTH_SHORT).show();
                            return null;
                        }
                    });
                }
                Toast.makeText(getApplicationContext(),"Sorry, there was a problem. Please, re-try.",Toast.LENGTH_SHORT).show();
                return null;
            }
        });
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // ...
                    }
                });
    }

}
