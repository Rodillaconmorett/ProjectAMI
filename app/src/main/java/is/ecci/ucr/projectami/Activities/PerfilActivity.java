package is.ecci.ucr.projectami.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import is.ecci.ucr.projectami.LogInfo;
import is.ecci.ucr.projectami.MainActivity;
import is.ecci.ucr.projectami.R;
import is.ecci.ucr.projectami.Users.User;

/**
 * Created by bjgd9 on 16/7/2017.
 */

    public class PerfilActivity extends AppCompatActivity {


    EditText name;
    EditText lastName;
    EditText email;

    EditText currentPassword;
    EditText newPassword;
    EditText repeatPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        name= (EditText) findViewById(R.id.editTextNombre);

        lastName= (EditText) findViewById(R.id.editTextApellido);

        email = (EditText) findViewById(R.id.editCorreo);

        currentPassword = (EditText)findViewById(R.id.editContrasenaAc);

        newPassword = (EditText)findViewById(R.id.editNueva);

        repeatPassword = (EditText)findViewById(R.id.editRepite);


        if(LogInfo.getRoles()!=null){
            name.setText(LogInfo.getFirstName());
            lastName.setText(LogInfo.getLastName());
            email.setText(LogInfo.getEmail());
            email.setEnabled(false);
            currentPassword.setText(LogInfo.getPassword());
        }

        Button btn= (Button) findViewById(R.id.btn_aceptar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(LogInfo.getRoles()!=null) {
                String uName = name.getText().toString();
                String uLastName = lastName.getText().toString();
                String uEmail = email.getText().toString();

                String currentP = currentPassword.getText().toString();
                String newP = newPassword.getText().toString();
                String repeatP = repeatPassword.getText().toString();
                if (currentP.equalsIgnoreCase("") || newP.equalsIgnoreCase("") || repeatP.equalsIgnoreCase("")) {
                    System.out.println("cant update");
                } else {
                    if (currentP.compareTo( LogInfo.getPassword())==0) {

                        if (newP.compareTo( repeatP)==0) {
                            User user = new User(uEmail, newP, uName, uLastName);
                            LogInfo.setPassword(newP);
                            //Llamar al update TODO
                            Intent intent = new Intent(PerfilActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }else{
                Toast.makeText(getApplicationContext(),"Please, first loggin",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PerfilActivity.this, LogActivity.class);
                startActivity(intent);
            }
            }
        });

    }

}
