package is.ecci.ucr.projectami.Bugs;

/**
 * Created by Daniel on 5/10/2017.
 */

public class Bug {


    private String family;
    private double score;
    private String description;

    public Bug(String family, double score, String description){
        this.family = family;
        this.score = score;
        this.description = description;
    }


    public String getFamily() {
        return family;
    }

    public double getScore() {
        return score;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
