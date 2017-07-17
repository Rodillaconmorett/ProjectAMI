package is.ecci.ucr.projectami;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Oscar Azofeifa on 17/07/2017.
 * Se agregaron cláusulas a métodos
 * ENTRADAS, SALIDAS y RESTRICCIONES.
 * <p>
 * ENTRADAS -> Listan los valores de entradas que ocupa el metodo (e.g. areaBasal, altura)
 * SALIDA -> Describen qué valor retorna el método, o qué es lo que hace el método. (e.g. retorna el
 * volumen de un cilindro)
 * RESTRICCIONES -> Describen cuando habrían excepciones (e.g. las entradas deben ser enteros > 0)
 */

public class ElementFamilyAdapter extends PagerAdapter {
    Context context;
    int images[];
    String names[];
    LayoutInflater layoutInflater;

    public ElementFamilyAdapter(Context context, int images[], String names[]){
        this.context = context;
        this.images = images;
        this.names = names;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((android.support.constraint.ConstraintLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.ele_family_view, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.adapterImgView);
        imageView.setImageResource(images[position]);

        TextView textView = (TextView) itemView.findViewById(R.id.adapterTxtView);
        textView.setText(names[position]);

        container.addView(itemView);

        //listening to image click
        /*imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show();
            }
        });*/
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((android.support.constraint.ConstraintLayout) object);
    }
}
