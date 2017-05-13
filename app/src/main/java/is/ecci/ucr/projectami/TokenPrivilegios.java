package is.ecci.ucr.projectami;

import java.util.BitSet;

/**
 * Created by Oscar Azofeifa on 13/05/2017.
 *
 * DESCRIPCIÓN: Token que almacena la información de logueo, el usuario y sus
 * privilegios.
 */

public class TokenPrivilegios {

    /*Formato de los privilegios: PENDIENTE*/
    private BitSet actionPrivileges;
    private BitSet adminPrivileges;
    int codKey;
    String username;
    String name;

    /* E:
    *  S:
    *  R:
    * */
    public TokenPrivilegios(){

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

    }


    /* E:-
    *  S: Retorna si se tienen privilegios sobre el catalog
    *  R: -
    * */
    public boolean reqCatalogRgts(){

    }
}
