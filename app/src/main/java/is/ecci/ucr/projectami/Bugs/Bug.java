package is.ecci.ucr.projectami.Bugs;

/**
 * Created by Daniel on 5/10/2017.
 */

public class Bug {

    private String id;
    private String family;
    private double score;
    private String description;
    private int imageID;

    /**
     *
     * @param family El nombre de la familia del macroinvertebrado
     * @param score El puntaje de vida del macroinvertebrado que representa para el punto de muestreo
     * @param description Descripción del macroinvertebrado
     */
    public Bug(String family, double score, String description){
        this.family = family;
        this.description = description;
        this.score = score;
    }

    //Initializer with family and ID
    public Bug(String id,String family, double score, String description){
        this.id = id;
        this.family = family;
        this.score = score;
        this.description = description;
    }

    /**
     *
     * @return El nombre del la familia del macroinvertado
     */
    public String getFamily() {
        return family;
    }

    /**
     *
     * @return El puntaje de vida del macroinvertebrado que representa para el punto de muestreo
     */
    public double getScore() {
        return score;
    }

    /**
     *
     * @param description Modifica la descripción del macroinvertebrado
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return Descripción del macroinvertebrado
     */
    public String getDescription() {
        return description;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getImageID() {
        return imageID;
    }
}
