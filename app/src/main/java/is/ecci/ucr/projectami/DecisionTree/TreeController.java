package is.ecci.ucr.projectami.DecisionTree;


import android.util.Pair;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.TreeMap;

/**
 * Created by Milton and Oscar on 17-May-17.
 */

public class TreeController implements Serializable {

    private enum InductionState {
        FOUND_COMMITED, FOUND_NOT_COMMITED, POSSIBLE_TO_FIND, IMPOSSIBLE_TO_FIND,
    }
    /*-----------------------------------------ATTRIBUTES-----------------------------------------*/
    private Node _actualNode;
    private ID3 _actualInstance;
    private Matrix _features;
    private Matrix _labels;
    private LinkedList<Pair< Node, String >> _realizedQuestions;   // Almacena la lista de las
    // preguntas realizadas con sus respectivas
    // respuestas
    private InductionState _MIFoundState;
    private int _questionsCounter;
    private int _feedbackQuestions;

    /*----------------------------------------CONSTRUCTORS----------------------------------------*/
    public TreeController(){

        // decisionTree = new decisionTree();
    }

    public TreeController(Matrix mainMatrix){

        _features = new Matrix(mainMatrix, 0, 1, mainMatrix.rows(), mainMatrix.cols()-1);
        _labels = new Matrix(mainMatrix, 0, 0, mainMatrix.rows(), 1);

        _actualInstance = new ID3();
        _actualNode = _actualInstance.buildTree(_features,_labels);

        _realizedQuestions = new LinkedList< Pair< Node, String > >();

        _MIFoundState = InductionState.POSSIBLE_TO_FIND;
        _questionsCounter = 0;
    }

    /*------------------------------------------METHODS-------------------------------------------*/

    /* E:  -
    *  S:  Retorna una pregunta con sus respuestas para inducir un macroinvertebrado.
    *       ESPECIFICACIÃ“N DEL FORMATO
    *       result =[{Pregunta},{R_1},{R_2},...,{R_n}]      si _actualNode es nodo interno
    *       result =[]                                      si _actualNode es nodo hoja
    *  R:  -
    * */
    public LinkedHashSet<String> getQuestionAndOptions(){
        LinkedHashSet<String> result = new LinkedHashSet< String >();
        String buffer = "";
        if (_MIFoundState == InductionState.POSSIBLE_TO_FIND ){
            result.add(new String(_actualNode.getAttribute().getName()));
            for (Double value :_actualNode.getAttribute().getValues()){
                result.add(_features.attrValue(_actualNode.getAttribute().getColumnPositionID(),value.intValue()));
            }
        } else if (_MIFoundState == InductionState.FOUND_NOT_COMMITED){
            result.add(_actualNode.getLabel().getStrValue());
            result.add("TRUE"); result.add("FALSE");
        } else if (_MIFoundState == InductionState.IMPOSSIBLE_TO_FIND){
            if (_actualNode.getLabel().getValue() == -1){
                result.add(new String(_actualNode.getAttribute().getName()));
                for (Double value :_actualNode.getAttribute().getValues()){
                    result.add(_features.attrValue(_actualNode.getAttribute().getColumnPositionID(),value.intValue()));
                }
            }else {
                result.add(_actualNode.getLabel().getStrValue());
                result.add("TRUE"); result.add("FALSE");
            }
        }
        return result;
    }

    /* E: Recibe una respuesta que conteste la pregunta actual.
    *  S:
    *  R: La respuesta debe ser una de las opciones
    * */
    public void reply(String response) throws AnswerException{
        //int estadoRespuesta = -1;           //(11111111)Si -1 => INVALID
        if (_MIFoundState == InductionState.POSSIBLE_TO_FIND){
            for (Double value : _actualNode.getAttribute().getValues()){
                if (response.equals(_features.attrValue(_actualNode.getAttribute().getColumnPositionID(), value.intValue()))){
                    //estadoRespuesta = 0;        //00000000

                    Pair< Node, String > pair = new Pair< Node,String >(_actualNode, response);
                    _realizedQuestions.add(pair);

                    _actualNode = _actualNode.getBranches().get(value);

                    if (_actualNode.getLabel().getValue() != -1){
                        _MIFoundState = InductionState.FOUND_NOT_COMMITED;
                    }
                    _questionsCounter++;
                    return;
                }
            }
            //if (estadoRespuesta == -1){
            throw new AnswerException();
            //}
        } else if (_MIFoundState == InductionState.FOUND_NOT_COMMITED){
            if (response.equals("TRUE")){
                Pair < Node, String > pair = new Pair<Node, String>(_actualNode, response);
                _realizedQuestions.add(pair);

                _MIFoundState = InductionState.FOUND_COMMITED;
            }
            else if (response.equals("FALSE")){
                _MIFoundState = InductionState.IMPOSSIBLE_TO_FIND;
                int i = 0;
                for (Pair<Node, String> pair: _realizedQuestions){
                    if (pair.second == "NA"){
                        _actualNode = pair.first;
                        _realizedQuestions.remove(i);
                        break;
                    }
                    i++;
                }
                if ( _realizedQuestions.size() == i ){
                    boolean hasBeenAsked = true;
                    Random numberNumber;
                    int num;														// NO ABARCA TODOS LOS NODOS
                    Node randomQuestion;
                    do {
                        numberNumber = new Random();
                        num = numberNumber.nextInt(_features.cols());				// NO ABARCA TODOS LOS NODOS
                        randomQuestion = this.getNode(num);
                        int j = 0;
                        for (Pair< Node, String > questionAndAnswer: _realizedQuestions){
                            if (questionAndAnswer.first.getNodeID() == randomQuestion.getNodeID()){
                                break;
                            }
                            j++;
                        }
                        if (j == _realizedQuestions.size()){
                            hasBeenAsked = false;
                        }
                    } while (hasBeenAsked);
                    _actualNode = randomQuestion;

                    //Si es un nodo hoja, abre posiblidad abre posibilidad de responder TRUE o FALSE.
                    if (_actualNode.getLabel().getValue() != -1){
                        _MIFoundState = InductionState.FOUND_NOT_COMMITED;
                    }
                    _questionsCounter++;
                }

            }
            else{
                throw new AnswerException();
            }
            return;
        } else if (_MIFoundState == InductionState.IMPOSSIBLE_TO_FIND){
            //boolean areThereDoubts = false;
            for (Double value: _actualNode.getAttribute().getValues()){ //Compara cada una de las respuestas con la ingresada
                if (response.equals(_features.attrValue(_actualNode.getAttribute().getColumnPositionID(), value.intValue()))){
                    //estadoRespuesta = 0;        //00000000

                    //Se agrega la pregunta con su respuesta a la lista de preguntas realizadas.
                    Pair< Node, String > pair = new Pair< Node,String >(_actualNode, response);
                    _realizedQuestions.add(pair);

                    //Busca una pregunta con respuesta NA dentro de la lista de preguntas realizadas.
                    int i = 0;
                    for (Pair< Node, String > questionAndAnswer: _realizedQuestions){
                        if (questionAndAnswer.second == "NA"){
                            _actualNode = questionAndAnswer.first;
                            _realizedQuestions.remove(i);
                            break;
                        }
                        i++;
                    }

                    //Si no se encuentra ninguna, busca un nodo aleatorio no preguntado.
                    if ( _realizedQuestions.size() == i ){
                        boolean hasBeenAsked = true;
                        Random numberNumber;
                        int num;														// NO ABARCA TODOS LOS NODOS
                        Node randomQuestion;
                        do {
                            numberNumber = new Random();
                            num = numberNumber.nextInt(_features.cols());				// NO ABARCA TODOS LOS NODOS
                            randomQuestion = this.getNode(num); 						// Sacamos un nodo aleatorio
                            int j = 0;													// Verificamos si el nodo ya se preguntó
                            for (Pair< Node, String > questionAndAnswer: _realizedQuestions){
                                if (questionAndAnswer.first.getNodeID() == randomQuestion.getNodeID()){
                                    break;
                                }
                                j++;
                            }
                            if (j == _realizedQuestions.size()){
                                hasBeenAsked = false;
                            }
                        } while (hasBeenAsked);
                        _actualNode = randomQuestion;
                    }

                    //Si es un nodo hoja, abre posiblidad abre posibilidad de responder TRUE o FALSE.
                    if (_actualNode.getLabel().getValue() != -1){
                        _MIFoundState = InductionState.FOUND_NOT_COMMITED;
                    }
                    _questionsCounter++;
                    return;
                }
            }
            throw new AnswerException();
        }

    }

    /* E: Recibe una respuesta para la pregunta actual, si la respuesta es NA, utiliza newProposalOption
                para generar una retroalimentaciÃ³n.
    *  S:
    *  R:
    * */
    public void reply(String response, String newProposalOption) throws AnswerException{
        //int estadoRespuesta = -1; //Si -1 => INVALID
        for (Double value : _actualNode.getAttribute().getValues()){
            if (response == _features.attrValue(_actualNode.getAttribute().getColumnPositionID(), value.intValue())) {
                Pair<Node, String> pair;
                if (response != "NA") {
                    //estadoRespuesta = 0;        //00000000
                    pair = new Pair<Node, String>(_actualNode, response);
                } else {
                    //estadoRespuesta = 1;
                    pair = new Pair<Node, String>(_actualNode, newProposalOption);
                }
                _realizedQuestions.add(pair);

                _actualNode = _actualNode.getBranches().get(value);
                _questionsCounter++;
                return;
            }
        }
        throw new AnswerException();
    }

    private Node getNode(int n){
        if (_realizedQuestions.size() != 0){
            return getNodeAux(_realizedQuestions.getFirst().first, 0, n).first;
        }else {
            return getNodeAux(_actualNode, 0, n).first;
        }
    }

    private Pair< Node, Integer > getNodeAux(Node currentNode, int i, int n){
        if (i == n){
            return new Pair< Node, Integer >(currentNode,i);
        }else if (currentNode.getLabel().getValue() == -1) {
            Pair< Node, Integer > pair;
            for(Double value: currentNode.getAttribute().getValues()){
                pair = getNodeAux(currentNode.getBranches().get(value), i + 1, n);
                i = pair.second;
                if (i == n){
                    return pair;
                }
            }
            return new Pair<Node,Integer>(currentNode,i);
        }else {
            return new Pair< Node, Integer >(currentNode, i + 1);
        }
    }

    /* E: attribute-> En este caso es la pregunta que se responde, value-> La respuesta a la pregunta
     * S: Agrega la pregunta a la lista de preguntas respondidas
     * R: La lista contenga al menos una pregunta, con atributos distintos a attribute.
     * */
    public void addAnswer(String attribute, String value){
        //Restricciones sin implementar
        Node node = new Node(-1);
        LinkedHashSet<Double> values = new LinkedHashSet<Double>();
        values.add(0.0);
        Attribute attri = new Attribute(attribute, values, _realizedQuestions.size());
        Label lbl = new Label(value, 0.0);
        node.setAttribute(attri);
        node.setLabel(lbl);

        _realizedQuestions.add(new Pair< Node, String >(node,"TRUE"));
    }

    /* E:  -
    *  S:  Resetea el nodoActual al nodo raÃ­z del Ã¡rbol de decisiÃ³n.
    *  R:  -
    * */
    public void reset(){
        if (_questionsCounter > 0){
            _actualNode = _realizedQuestions.getFirst().first;
            _realizedQuestions.clear();
            _questionsCounter = 0;
        }
    }

    /* EFECTO: Setea la pregunta a la pregunta anterior
    *  REQUIERE: -
    *  MODIFICA: -
    * */
    public void goBack(){
        if (_questionsCounter > 0){
            _actualNode = _realizedQuestions.getLast().first;
            _realizedQuestions.removeLast();
            _questionsCounter--;
        }

    }

    /* E:
       S:
       R:
    * */
    public Matrix getFeedbackMatrix(){
        Matrix matrix = new Matrix();
        matrix.setSize(1,_realizedQuestions.size());
        int i = 0;
        for (Pair< Node, String > question : _realizedQuestions){
            if (question.first.getLabel().getValue() != -1){
                matrix.setAttrName(i, "familia");

                TreeMap<Integer, String > its = new TreeMap< Integer, String >();
                TreeMap< String, Integer > sti = new TreeMap< String, Integer >();

                its.put(0, question.first.getLabel().getStrValue());
                sti.put(question.first.getLabel().getStrValue(), 0);

                matrix.addStringToValue(i, sti);
                matrix.addValueToString(i, its);

                matrix.set(0,i,0);
            }else{
                boolean thereIsntTheAnswer = true;
                for (Double value : question.first.getAttribute().getValues()){//Mejorable
                    if (_features.attrValue(question.first.getAttribute().getColumnPositionID(),value.intValue()) == question.second){
                        thereIsntTheAnswer = false;
                    }
                }
                if (thereIsntTheAnswer){
                    //Agrega el valor al atributo
                    matrix.setAttrName(i,question.first.getAttribute().getName());

                    TreeMap<Integer, String > its = new TreeMap< Integer, String >();
                    TreeMap< String, Integer > sti = new TreeMap< String, Integer >();

                    its.put(0, question.second);
                    sti.put(question.second, 0);

                    matrix.addStringToValue(i, sti);
                    matrix.addValueToString(i, its);

                    matrix.set(0,i,0);
                }
            }
            i++;
        }
        return matrix;
    }

    public LinkedList<Pair< String, String >> getQuestionsRealized(){
        LinkedList<Pair<String, String>> result = new LinkedList< Pair<String, String>>();

        for (Pair<Node, String> pair : _realizedQuestions){
            if (pair.first.getLabel().getValue() == -1)
            result.add(new Pair(pair.first.getAttribute().getName(),pair.second));
            else result.add(new Pair(pair.first.getLabel().getStrValue(), pair.second));
        }
        return result;
    }

    /* E: -
    *  S: Retorna TRUE si el nodo actual es un nodo hoja.
    *  R: -
    * */
    public boolean isLeaf(){return _actualNode.getLabel().getValue() != -1;}


}
