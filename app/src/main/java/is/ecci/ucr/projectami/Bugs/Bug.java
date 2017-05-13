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

    private String family;
    private double score;
    private String[] imageDir;
    private int totalImages;

    public Bug(String family, double score, String imageDir){
        this.family = family;
        this.score = score;
        this.imageDir[0] = imageDir;
        totalImages = 1;
    }

    public Bug(String family, double score, String imageDir1, String imageDir2){
        this.family = family;
        this.score = score;
        this.imageDir[0] = imageDir1;
        this.imageDir[1] = imageDir2;
        totalImages = 2;
    }

    public Bug(String family, double score, String imageDir1, String imageDir2, String imageDir3){
        this.family = family;
        this.score = score;
        this.imageDir[0] = imageDir1;
        this.imageDir[1] = imageDir2;
        this.imageDir[2] = imageDir3;
        totalImages = 3;
    }

    public String getFamily() {
        return family;
    }

    public double getScore() {
        return score;
    }

    public String getImageDir(int imgNumb) {
        if(imgNumb >= 0 && imgNumb < totalImages){
            return imageDir[imgNumb];
        }
        else{
            return imageDir[0];
        }
    }
}
