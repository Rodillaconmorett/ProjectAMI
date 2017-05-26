package is.ecci.ucr.projectami;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import is.ecci.ucr.projectami.Bugs.Bug;

/**
 * Created by Oscar Azofeifa on 24/05/2017.
 * Se agregaron cláusulas a métodos
 * ENTRADAS, SALIDAS y RESTRICCIONES.
 * <p>
 * ENTRADAS -> Listan los valores de entradas que ocupa el metodo (e.g. areaBasal, altura)
 * SALIDA -> Describen qué valor retorna el método, o qué es lo que hace el método. (e.g. retorna el
 * volumen de un cilindro)
 * RESTRICCIONES -> Describen cuando habrían excepciones (e.g. las entradas deben ser enteros > 0)
 */

public class SampleBugsAdapter extends ArrayAdapter<Bug> {

    private Context _context;
    private ArrayList<Bug> _bugs;
    private int[] _quantityBugs;

    public SampleBugsAdapter(Context context, ArrayList<Bug> bugs){
        super(context,0,bugs);
        _context = context;
        _bugs = bugs;
        _quantityBugs = new int[_bugs.size()];
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // En primer lugar "inflamos" una nueva vista, que será la que se
        // mostrará en la celda del ListView. Para ello primero creamos el
        // inflater, y después inflamos la vista.
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.sample_list_element,parent,false);
        }
        //LayoutInflater inflater = LayoutInflater.from(_context);
        //View item = inflater.inflate(R.layout.sample_list_element, null);

        // A partir de la vista, recogeremos los controles que contiene para
        // poder manipularlos.
        // Recogemos el ImageView y le asignamos una foto.
        //ConstraintLayout fondo = (ConstraintLayout) item.findViewById(R.id.lstSampleElement);
        //fondo.setBackground();


        // Recogemos el TextView para mostrar el nombre y establecemos el
        // nombre.
        TextView nombre = (TextView) convertView.findViewById(R.id.txtName);
        nombre.setText(_bugs.get(position).getFamily());

        // Recogemos el TextView para mostrar el número de celda y lo
        // establecemos.
        //TextView numCelda = (TextView) item.findViewById(R.id.tvField);
        //numCelda.setText(String.valueOf(position));

        // Devolvemos la vista para que se muestre en el ListView.
        return convertView;
    }

    public int getBugQuantity(int i){
        return _quantityBugs[i];
    }
}
