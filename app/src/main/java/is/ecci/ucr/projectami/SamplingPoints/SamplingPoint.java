package is.ecci.ucr.projectami.SamplingPoints;

import java.util.LinkedList;

/**
 * Created by Daniel on 5/10/2017.
 */

public class SamplingPoint {
    private double score;
    private int lowQualBug;
    private int medQualBug;
    private int hghQualBug;
    //private ConsultorBDAguas consultant;
    /*
    * Cambiar String por la clase bicho, no solo aqui sino tambien en getBugList y en el constructor
    * */
    private LinkedList<String> bugList;
    private Site site;

    public SamplingPoint(Site site) {
        score = 0;
        lowQualBug = 0;
        medQualBug = 0;
        hghQualBug = 0;
        bugList = new LinkedList<String>();
        this.site = site;
    }

    public void updateScore(){

    }

    public void updateLowQualBug(){

    }

    public void updateMedQualBug(){

    }

    public void updateHghQualBug(){

    }

    public void updateBugList(){

    }

    public double getScore(){
        return score;
    }

    public int getLowQualBug(){
        return lowQualBug;
    }

    public int getMedQualBug(){
        return medQualBug;
    }

    public int getHghQualBug(){
        return hghQualBug;
    }

    public LinkedList<String> getBugList(){
        return bugList;
    }
}