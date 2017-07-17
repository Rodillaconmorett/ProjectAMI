package is.ecci.ucr.projectami.Users;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import is.ecci.ucr.projectami.Activities.Classification.BugsSampleToRegisterActivity;
import is.ecci.ucr.projectami.Activities.SubScreenMap;
import is.ecci.ucr.projectami.Activities.UsersManagerActivity;
import is.ecci.ucr.projectami.Bugs.BugFamily;
import is.ecci.ucr.projectami.R;
import is.ecci.ucr.projectami.Activities.UserLog.LogUserActivity;

/**
 * Created by alaincruzcasanova on 7/16/17.
 */

public class UserAdapter extends ArrayAdapter<User> {

    private Context context;
    private ArrayList<User> datos;

    public UserAdapter(Context context, ArrayList<User> datos) {
        super(context, R.layout.catalog_frame ,datos);
        // Guardamos los parámetros en variables de clase.
        this.context = context;
        this.datos = datos;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        // En primer lugar "inflamos" una nueva vista, que será la que se
        // mostrará en la celda del ListView. Para ello primero creamos el
        // inflater, y después inflamos la vista.
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.catalog_frame, null);


        TextView nombre = (TextView) item.findViewById(R.id.user_name);
        String userName = datos.get(position).getFirstName();
        String userLast = datos.get(position).getLastName();
        if(userName==null){
            userName = "";
        }
        if(userLast==null){
            userLast = "";
        }
        nombre.setText( userName+" "+userLast);

        TextView txtScore = (TextView) item.findViewById(R.id.user_email);
        txtScore.setText( datos.get(position).getEmail());

        Button editButton = (Button)convertView.findViewById(R.id.edit_user_btn);
        Button historyButton = (Button)convertView.findViewById(R.id.check_user_hist_btn);

        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
        historyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), LogUserActivity.class);
                intent.putExtra("user", (Serializable) datos.get(position));
                parent.getContext().startActivity(intent);
            }
        });

        return item;
    }

}
