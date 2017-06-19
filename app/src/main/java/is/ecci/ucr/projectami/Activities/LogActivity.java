package is.ecci.ucr.projectami.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

import is.ecci.ucr.projectami.MainActivity;
import is.ecci.ucr.projectami.R;

/**
 * Created by bjgd9 on 12/6/2017.
 */


public class LogActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener  {

    public  GoogleApiClient googleApiClient;//to create the login
    private static GoogleSignInOptions googleSignInOptions;// loggin options
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


        signInButton = (SignInButton) findViewById(R.id.btnLogGoogle);
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
            loadPrincipalActivity(googleSignInResult);
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

        Toast.makeText(this,"person Name : "+ personName+
                "\npersonGivenName : "+personGivenName+
                "\npersonFamilyName : "+ personFamilyName +
                "\npersonEmail : "+personEmail+
                "\npersonId : "+personId+
                "\npersonPhoto : "+personPhoto,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LogActivity.this, MainActivity.class);//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP  |Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK   );
        startActivity(intent);
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

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.

            GoogleSignInResult result = opr.get();
            handleSignal(result);

        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.

            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    handleSignal(googleSignInResult);

                }
            });
        }
    }

}
