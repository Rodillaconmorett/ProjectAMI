package is.ecci.ucr.projectami.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import is.ecci.ucr.projectami.MainActivity;
import is.ecci.ucr.projectami.R;

/**
 * Created by bjgd9 on 12/6/2017.
 */


public class LogActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener  {

    private GoogleApiClient googleApiClient;//to create the login
    private GoogleSignInOptions googleSignInOptions;// loggin options
    private SignInButton signInButton;

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

        signInButton = (SignInButton) findViewById(R.id.btnLogGoogled);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,777);

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
            loadPrincipalActivity();
        }else{
            Toast.makeText(this,"Log fail",Toast.LENGTH_SHORT).show();
        }
    }

    private void loadPrincipalActivity() {
        Intent intent = new Intent(LogActivity.this, MainActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP  |Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK   );
        startActivity(intent);
    }
}
