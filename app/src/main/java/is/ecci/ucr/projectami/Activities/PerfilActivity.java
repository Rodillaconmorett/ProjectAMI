package is.ecci.ucr.projectami.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import is.ecci.ucr.projectami.LogInfo;
import is.ecci.ucr.projectami.R;

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


        if(LogInfo.isLogged()){


        }


        Button btn= (Button) findViewById(R.id.btn_aceptar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });

    }

}
