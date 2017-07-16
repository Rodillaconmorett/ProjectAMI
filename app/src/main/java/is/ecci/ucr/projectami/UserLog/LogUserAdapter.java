package is.ecci.ucr.projectami.UserLog;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

import is.ecci.ucr.projectami.Activities.Classification.BugsSampleToRegisterActivity;
import is.ecci.ucr.projectami.DBConnectors.CollectionName;
import is.ecci.ucr.projectami.DBConnectors.Consultor;
import is.ecci.ucr.projectami.DBConnectors.JsonParserLF;
import is.ecci.ucr.projectami.DBConnectors.ServerCallback;
import is.ecci.ucr.projectami.R;

/**
 * Created by Milton on 15-Jul-17.
 */

public class LogUserAdapter extends ArrayAdapter<SampleLog> {
    private Context _context;
    private ArrayList<SampleLog> _samples;

    public LogUserAdapter(Context context, ArrayList<SampleLog> sample) {
        super(context, 0, sample);
        _context = context;
        _samples = sample;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // En primer lugar "inflamos" una nueva vista, que será la que se
        // mostrará en la celda del ListView. Para ello primero creamos el
        // inflater, y después inflamos la vista.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_log_user, parent, false);
        }
        // Recogemos el TextView para mostrar el nombre y establecemos el
        // nombre.

        TextView nombre = (TextView) convertView.findViewById(R.id.txtNameLog);
        nombre.setText(_samples.get(position).getBugName());

        Button deleteBtn = (Button)convertView.findViewById(R.id.del_log_item);
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                _samples.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });

        Button del_item = (Button) convertView.findViewById(R.id.del_log_item);
        del_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(_context, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(_context);
                }
                builder.setTitle("Borrar registro")
                        .setMessage("Se borrará el registro de la clasificación del insecto")
                        .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String sampleID = _samples.get(position).getSampleID();

                                _samples.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        TextView date = (TextView) convertView.findViewById(R.id.dateSample);
        date.setText(_samples.get(position).getDate().toString());

        TextView site = (TextView) convertView.findViewById(R.id.logSite);
        Consultor.getDocById(new ServerCallback() {
            @Override
            public JSONObject onSuccess(JSONObject result) {
                //site.setText(JsonParserLF.);
                return null;
            }

            @Override
            public JSONObject onFailure(JSONObject result) {
                return null;
            }
        }, CollectionName.SITE,_samples.get(position).getSite());


        // Devolvemos la vista para que se muestre en el ListView.
        return convertView;
    }
}

