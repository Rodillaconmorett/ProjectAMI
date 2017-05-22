package is.ecci.ucr.projectami;

import android.widget.ListView;

import is.ecci.ucr.projectami.Bugs.Bug;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Oscar Azofeifa on 12/05/2017.
 Se agregaron cláusulas
 ENTRADAS, SALIDAS y RESTRICCIONES.

 ENTRADAS -> Listan los valores de entradas que ocupa el metodo (e.g. areaBasal, altura)
 SALIDA -> Describen qué valor retorna el método, o qué es lo que hace el método. (e.g. retorna el
 volumen de un cilindro)
 RESTRICCIONES-> Describen cuando habrían excepciones (e.g. las entradas deben ser enteros > 0)
 */


public class Catalog {
    private List<Bug> bugList;
    BDAdministrator bdAdministrator;
    /* ENTRADAS:
    *  SALIDAS:
    *  RESTRICCIONES:
    * */
    public Catalog(){
        bugList= new ArrayList<>();

    }

    /* ENTRADAS:
    *  SALIDAS:
    *  RESTRICCIONES:
    * */
    public void loadImagesToApplication(){

    }

    /* ENTRADAS:
    *  SALIDAS:
    *  RESTRICCIONES:
    * */
    public void loadBugFamilies(){

    }

    /* ENTRADAS:
    *  SALIDAS:
    *  RESTRICCIONES:
    * */
    public Bug getBugInformation(){
        Bug paraQueCompile = null;
        return paraQueCompile;
    }
}
