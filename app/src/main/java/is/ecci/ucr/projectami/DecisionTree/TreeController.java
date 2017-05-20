package is.ecci.ucr.projectami.DecisionTree;

import android.util.Pair;

import java.util.LinkedHashSet;

/**
 * Created by Milton and Oscar on 17-May-17.
 */

public class TreeController {

    private enum InductionState {
        FOUND, POSSIBLE_TO_FIND, IMPOSSIBLE_TO_FIND
    }
    /*-----------------------------------------ATTRIBUTES-----------------------------------------*/
    private Node _actualNode;
    private ID3 _actualInstance;
    private LinkedHashSet< Pair< Node, String > > _realizedQuestions;   // Almacena la lista de las
                                                // preguntas realizadas con sus respectivas respuestas
    private InductionState _MIFoundState;
    private int _questionsCounter;

    /*----------------------------------------CONSTRUCTORS----------------------------------------*/
    public TreeController(){

       // decisionTree = new decisionTree();
    }

    public TreeController(Matrix mainMatrix){

        Matrix features = new Matrix(mainMatrix, 0, 1, mainMatrix.rows(), mainMatrix.cols()-1);
        Matrix labels = new Matrix(mainMatrix, 0, 0, mainMatrix.rows(), 1);

        _actualInstance = new ID3();
        _actualNode = _actualInstance.buildTree(features,labels);

        _MIFoundState = InductionState.POSSIBLE_TO_FIND;
        _questionsCounter = 0;
    }

    /*------------------------------------------METHODS-------------------------------------------*/

    /* E:  -
    *  S:  Retorna una pregunta con sus respuestas para inducir un microenvertebrado.
    *  R:  -
    * */
    public LinkedHashSet<String> getQuestionAndOptions(){
        LinkedHashSet<String> result = new LinkedHashSet< String >();
        return result;
    }

    /* E: Recibe una respuesta para la pregunta actual, si la respuesta no está dentro de las
                opciones se toma como valor = NA y se almacena para retroalimentación del banco de
                preguntas (base de datos).
    *  S:
    *  R:
    * */
    public void reply(String[] response){

    }

    /* E:  -
    *  S:  Resetea el nodoActal al nodo raíz del árbol de decisión.
    *  R:  -
    * */
    public void reset(){
    }

    /* EFECTO: Setea la pregunta a la pregunta anterior
    *  REQUIERE: -
    *  MODIFICA: -
    * */
    public void goBack(){

    }

    /* E: -
    *  S: Retorna TRUE si el nodo actual es un nodo hoja.
    *  R: -
    * */
    public boolean isLeaf(){
        return false;
    }


}
