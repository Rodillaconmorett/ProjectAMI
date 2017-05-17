package is.ecci.ucr.projectami.DBConnectors;

import is.ecci.ucr.projectami.Bugs.Bug;
import is.ecci.ucr.projectami.UserInfo;

/**
 * Created by Daniel on 5/10/2017.
 */

public class DBAguasConsultant {
    /* ENTRADAS:
    *  SALIDAS:
    *  RESTRICCIONES:
    * */
    public Bug[] getBugs(){
        Bug bugs[]=null;
        return  bugs;
    }

    /* ENTRADAS:
    *  SALIDAS:
    *  RESTRICCIONES:
    * */
    public Bug[] getBugs(String[] bugNames){
        Bug bugs[]=null;
        return  bugs;
    }

    /* ENTRADAS:
    *  SALIDAS:
    *  RESTRICCIONES:
    * */
    public UserInfo[] getUsers(){
        UserInfo userInfo[]=null;
        return  userInfo;
    }

    /* ENTRADAS:
    *  SALIDAS:
    *  RESTRICCIONES:
    * */
    public UserInfo getUser(String email){
        UserInfo userInfo=null;
        return userInfo;
    }
}