package is.ecci.ucr.projectami.Bugs;

/**
 * Created by bjgd9 on 18/6/2017.
 */

public class BugFamily {

    private String nameFamily;
    private Double points;
    private int imageID;


    public BugFamily(String nameFamily, Double points, int imageID) {
        this.nameFamily = nameFamily;
        this.points = points;
        this.imageID = imageID;
    }

    public String getNameFamily() {
        return nameFamily;
    }

    public Double getPoints() {
        return points;
    }

    public int getImageID() {
        return imageID;
    }

    public void setNameFamily(String nameFamily) {
        this.nameFamily = nameFamily;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

}
