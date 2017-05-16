package is.ecci.ucr.projectami;

/**
 * Created by bjgd9 on 14/5/2017.
 */

public class Animal {
    private String nombre;
    private int drawableImageID;

    public Animal(String nombre, int drawableImageID) {
        this.nombre = nombre;
        this.drawableImageID = drawableImageID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDrawableImageID() {
        return drawableImageID;
    }

    public void setDrawableImageID(int drawableImageID) {
        this.drawableImageID = drawableImageID;
    }

}
