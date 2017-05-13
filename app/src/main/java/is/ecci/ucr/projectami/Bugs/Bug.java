package is.ecci.ucr.projectami.Bugs;

/**
 * Created by Daniel on 5/10/2017.
 */

public class Bug {
    public String getBugName() {
        return bugName;
    }

    public String getFamily() {
        return family;
    }

    public double getScore() {
        return score;
    }

    public void setBugName(String bugName) {
        this.bugName = bugName;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getImages() {
        return images;
    }

    private String bugName;
    private String family;
    private double score;
    private String images;

}
