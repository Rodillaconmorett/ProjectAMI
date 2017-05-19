package is.ecci.ucr.projectami.Bugs;

import is.ecci.ucr.projectami.Bugs.Bug;
import is.ecci.ucr.projectami.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
/**
 * Created by bjgd9 on 17/5/2017.
 */

public class BugAdater extends ArrayAdapter {
    
    private Context context;
    private ArrayList<Bug> datos;

    public BugAdater(Context context, ArrayList datos) {
        super(context, R.layout.catalog_frame, datos);
        // Guardamos los parámetros en variables de clase.
        this.context = context;
        this.datos = datos;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        // En primer lugar "inflamos" una nueva vista, que será la que se
        // mostrará en la celda del ListView. Para ello primero creamos el
        // inflater, y después inflamos la vista.
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.catalog_frame, null);

        // A partir de la vista, recogeremos los controles que contiene para
        // poder manipularlos.
        // Recogemos el ImageView y le asignamos una foto.
        ImageView imagen = (ImageView) item.findViewById(R.id.imgAnimal);
        imagen.setImageResource((int) datos.get(position).getScore());

        // Recogemos el TextView para mostrar el nombre y establecemos el
        // nombre.
        TextView nombre = (TextView) item.findViewById(R.id.tvContent);
        nombre.setText(datos.get(position).getFamily());

        // Recogemos el TextView para mostrar el número de celda y lo
        // establecemos.
        TextView numCelda = (TextView) item.findViewById(R.id.tvField);
        numCelda.setText(String.valueOf(position));

        // Devolvemos la vista para que se muestre en el ListView.
        return item;

    }

}
