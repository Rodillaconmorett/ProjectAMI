package is.ecci.ucr.projectami.DecisionTree;

/**
 * Created by Oscar Azofeifa on 19/05/2017.
 * Se agregaron cláusulas a métodos
 * ENTRADAS, SALIDAS y RESTRICCIONES.
 * <p>
 * ENTRADAS -> Listan los valores de entradas que ocupa el metodo (e.g. areaBasal, altura)
 * SALIDA -> Describen qué valor retorna el método, o qué es lo que hace el método. (e.g. retorna el
 * volumen de un cilindro)
 * RESTRICCIONES -> Describen cuando habrían excepciones (e.g. las entradas deben ser enteros > 0)
 */

public class AnswerException extends Exception {
    public AnswerException(){
        super("Answer Not Registred");
    }

    public AnswerException(String _message){
        super(_message);
    }
}
