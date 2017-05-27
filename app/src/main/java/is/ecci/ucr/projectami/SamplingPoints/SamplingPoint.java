package is.ecci.ucr.projectami.SamplingPoints;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.ListIterator;

import is.ecci.ucr.projectami.Bugs.Bug;

/**
 * Created by Daniel on 5/10/2017.
 */

public class SamplingPoint implements Serializable {
    private double score;
    private int lowQualBug;
    private int medQualBug;
    private int hghQualBug;
    private LinkedList<Bug> bugList;
    private Site site;

    /**
     * Constructor de la clase
     * @param site El sitio asociado al punto de muestreo
     */
    public SamplingPoint(Site site) {
        score = 0;
        lowQualBug = 0;
        medQualBug = 0;
        hghQualBug = 0;
        bugList = new LinkedList<>();
        this.site = site;
    }

    /**
     * Actualiza el puntaje del punto de muestreo y la cantidad de cantidad de bichos en cada categoría
     */
    public void updateScoreAndQualBug(){
        score = 0;
        lowQualBug = 0;
        medQualBug = 0;
        hghQualBug = 0;
        ListIterator<Bug> iterator = bugList.listIterator();
        while(iterator.hasNext()){
            double bugScore = iterator.next().getScore();
            if(bugScore < 3){
                lowQualBug++;
            }
            else if (bugScore < 7){
                medQualBug++;
            }
            else{
                hghQualBug++;
            }
            score += bugScore;
        }
    }

    /**
     *
     * @param bugList Lista de bichos que será asignada
     */
    public void setBugList(LinkedList<Bug> bugList){
        this.bugList = bugList;
    }

    /**
     *
     * @return La puntuación total del punto de muestreo
     */
    public double getScore(){
        return score;
    }

    /**
     *
     * @return La cantidad de bichos con calificación baja registrada en el punto de muestreo
     */
    public int getLowQualBug(){
        return lowQualBug;
    }

    /**
     *
     * @return La cantidad de bichos con calificación media registrada en el punto de muestreo
     */
    public int getMedQualBug(){
        return medQualBug;
    }

    /**
     *
     * @return La cantidad de bichos con calificación alta registrada en el punto de muestreo
     */
    public int getHghQualBug(){
        return hghQualBug;
    }

    /**
     *
     * @return La lista de bichos registrados en el punto de muestreo
     */
    public LinkedList<Bug> getBugList(){
        return bugList;
    }

    /**
     *
     * @return El sitio asociado al punto de muestreo
     */
    public Site getSite(){
        return site;
    }
}