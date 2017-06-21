package is.ecci.ucr.projectami;

import android.widget.Switch;

import java.util.BitSet;

import is.ecci.ucr.projectami.Users.User;

/**
 * Created by Oscar Azofeifa on 13/05/2017.
 *
 * DESCRIPCIÓN: Token que almacena la información de logueo, el usuario y sus
 * privilegios.
 */

public class TokenPrivilegios {

    /* PRIVILEGES FORMAT:

    *
    * *******************************USERs TYPES
    * Consultors Rights:
    *   -See the map with sample points.
    *   -Show general sample points data.
    *   -Search sample points.
    *   -Access to bugs catalog.
    * Collectors Rights:
    *   -Register samples sets.
    *   -Make feedbacks based on images.
    * Administrators Rights (Inherits Collector):
    *   -Add, modify and delete sample points.
    *   -Add, modify and delete users.
    * BioAdministrators Rights:
    *   -Add, modify and delete invertebrates.
    *   -Make feedbacks without images.
    * */

    public enum UserProfile {
        CONSULTOR,
        COLLECTOR,
        ADMINISTRATOR,
        BIOADMINISTRATOR
    }

    private BitSet _actionPrivileges;
    private BitSet _adminPrivileges;
    private int _codKey;
    private String _username;
    private String _name;


    /* E: User to create
    *  S: Creates a token with privileges related with the user specified.
    *  R: If input is different than any kind of user, creates a Consultor Token by default.
    * */
    public TokenPrivilegios(String username, String name, UserProfile kindaUser){

        _username = username;
        _name = name;

        switch (kindaUser) {
            case CONSULTOR:
                break;
            case COLLECTOR:
                break;
            case ADMINISTRATOR:
                break;
            case BIOADMINISTRATOR:
                break;
        }
    }

    public TokenPrivilegios(String username, String name, long customizedPrivilges){

    }

    /* E:
       S:
       R:
    * */
    public void Codification(){

    }


    /* E:-
    *  S: Retorna si se tienen privilegios sobre el árbol
    *  R: -
    * */
    public boolean reqTreeRgts(){
        return true;
    }


    /* E:-
    *  S: Retorna si se tienen privilegios sobre el catalog
    *  R: -
    * */
    public boolean reqCatalogRgts(){
        return true;
    }
}
