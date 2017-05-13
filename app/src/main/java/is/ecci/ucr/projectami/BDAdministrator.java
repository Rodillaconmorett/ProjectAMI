package is.ecci.ucr.projectami;

import is.ecci.ucr.projectami.Bugs.Bug;
import is.ecci.ucr.projectami.SamplingPoints.Site;

/**
 * Created by Oscar Azofeifa on 12/05/2017.
 Se agregaron cláusulas a métodos
 ENTRADAS, SALIDAS y RESTRICCIONES.

 ENTRADAS -> Listan los valores de entradas que ocupa el metodo (e.g. areaBasal, altura)
 SALIDA -> Describen qué valor retorna el método, o qué es lo que hace el método. (e.g. retorna el
 volumen de un cilindro)
 RESTRICCIONES -> Describen cuando habrían excepciones (e.g. las entradas deben ser enteros > 0)
 */

public class BDAdministrator {
    /*--------------------------------------ATRIBUTOS---------------------------------------------*/
    private String connectionString;

    /*---------------------------------------MÉTODOS----------------------------------------------*/
    /* ENTRADAS:
    *  SALIDAS:
    *  RESTRICCIONES:
    * */
    public Bug[] getBugs(){

    }

    /* ENTRADAS:
    *  SALIDAS:
    *  RESTRICCIONES:
    * */
    public Bug[] getBugs(int[] bugsIds){ //Entradas estaban como 'bugsIds: [ids]' tipo ids no existe

    }

    /* ENTRADAS:
    *  SALIDAS:
    *  RESTRICCIONES:
    * */
    public void insertSampling(int bugId, int siteId, int quantity){ //Entradas estaban como 'bugId: id, siteID:id, quantity: int'. No existe tipo id.

    }

    /* ENTRADAS:
    *  SALIDAS:
    *  RESTRICCIONES:
    * */
    public void insertBug(Bug bugInfo){

    }

    /* ENTRADAS:
    *  SALIDAS:
    *  RESTRICCIONES:
    * */
    public void modifyBug(int bugId, Bug bugInfo){ // Entrada estaba como 'bugId: id', no existe tipo id.

    }

    /* ENTRADAS:
    *  SALIDAS:
    *  RESTRICCIONES:
    * */
    public void deleteBug(int bugId){ // Entrada estaba como 'bugId: id', no existe tipo id.

    }

    /* ENTRADAS:
    *  SALIDAS:
    *  RESTRICCIONES:
    * */
    public UserInfo[] getUserInfo(String email){

    }

    /* ENTRADAS:
    *  SALIDAS:
    *  RESTRICCIONES:
    * */
    public boolean userExists(int userId){ // Entrada estaba como 'userId: id', no existe tipo id.

    }

    /* ENTRADAS:
    *  SALIDAS:
    *  RESTRICCIONES:
    * */
    public boolean userExists(String email) {

    }

    /* ENTRADAS:
    *  SALIDAS:
    *  RESTRICCIONES:
    * */
    public void insertUser(String email, String password, UserInfo userInfo){// Entrada estaba como 'userInfo: User', no existe tipo User.

    }

    /* ENTRADAS:
    *  SALIDAS:
    *  RESTRICCIONES:
    * */
    public void deleteUser(String email){

    }

    /* ENTRADAS:
    *  SALIDAS:
    *  RESTRICCIONES:
    * */
    public void deleteUserEntries(String email){

    }

    /* ENTRADAS:
    *  SALIDAS:
    *  RESTRICCIONES:
    * */
    public void modifyUser(String email, UserInfo userinfo){// Entrada estaba como 'userInfo: User', no existe tipo User.

    }

    /* ENTRADAS:
    *  SALIDAS:
    *  RESTRICCIONES:
    * */
    public String getUserRole(String email){

    }

    /* ENTRADAS:
    *  SALIDAS:
    *  RESTRICCIONES:
    * */
    public void loginUser(String email, String password){

    }

    /* ENTRADAS:
    *  SALIDAS:
    *  RESTRICCIONES:
    * */
    public String getSites(int siteId){// Entrada estaba como 'siteId: id', no existe tipo id.

    }

    /* ENTRADAS:
    *  SALIDAS:
    *  RESTRICCIONES:
    * */
    public Site[] getSiteInfo(int siteId){ // Entrada estaba como 'siteId: id', no existe tipo id.

    }
}
