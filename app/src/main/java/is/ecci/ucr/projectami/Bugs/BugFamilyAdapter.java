package is.ecci.ucr.projectami.Bugs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import is.ecci.ucr.projectami.R;

/**
 * Created by bjgd9 on 18/6/2017.
 */

public class BugFamilyAdapter extends ArrayAdapter<BugFamily> {

    private Context context;
    private ArrayList<BugFamily> datos;

    public BugFamilyAdapter(Context context, ArrayList<BugFamily> datos) {
        super(context, R.layout.catalog_frame ,datos);
        // Guardamos los parámetros en variables de clase.
        this.context = context;
        this.datos = datos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // En primer lugar "inflamos" una nueva vista, que será la que se
        // mostrará en la celda del ListView. Para ello primero creamos el
        // inflater, y después inflamos la vista.
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.catalog_frame, null);

        // A partir de la vista, recogeremos los controles que contiene para
        // poder manipularlos.
        // Recogemos el ImageView y le asignamos una foto.
        ImageView imagen = (ImageView) item.findViewById(R.id.imgFamily);
        imagen.setImageResource(datos.get(position).getImageID());

        ImageView qualImg = (ImageView) item.findViewById(R.id.imgQualBug);
        qualImg.setImageResource(getBugQualImg(datos.get(position).getPoints()));

        // Recogemos el TextView para mostrar el nombre y establecemos el
        // nombre.
        TextView nombre = (TextView) item.findViewById(R.id.nameFamily);
        nombre.setText( datos.get(position).getNameFamily());

        TextView txtScore = (TextView) item.findViewById(R.id.txtScore);
        txtScore.setText( datos.get(position).getPoints() + " pts");

//        TextView txtCount = (TextView) item.findViewById(R.id.txtCount);
//        txtScore.setText( "Cont: " + datos.get(position).getQuantity);


        // Recogemos el TextView para mostrar el número de celda y lo
        // establecemos.

        // Devolvemos la vista para que se muestre en el ListView.
        return item;
    }

    public int getBugQualImg(double score){
        if(score < 3){
            return R.drawable.nimbu_icon_red;
        }
        else if(score < 7){
            return R.drawable.nimbu_icon_orange;
        }
        else {
            return R.drawable.nimbu_icon_green;
        }
    }

}
