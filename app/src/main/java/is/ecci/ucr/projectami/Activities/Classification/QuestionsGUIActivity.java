package is.ecci.ucr.projectami.Activities.Classification;

import is.ecci.ucr.projectami.DBConnectors.CollectionName;
import is.ecci.ucr.projectami.DBConnectors.Consultor;
import is.ecci.ucr.projectami.DBConnectors.JsonParserLF;
import is.ecci.ucr.projectami.DBConnectors.ServerCallback;
import is.ecci.ucr.projectami.DecisionTree.Matrix;
import is.ecci.ucr.projectami.DecisionTree.TreeController;
import is.ecci.ucr.projectami.DecisionTree.AnswerException;
import is.ecci.ucr.projectami.Questions;
import is.ecci.ucr.projectami.R;

import android.app.Activity;
import android.content.Intent;
import android.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;


public class QuestionsGUIActivity extends AppCompatActivity {

    //Declaración de variables

    //Variables estáticas que requieren de muchos recursos, que se busca que se creen pocas veces
    static TreeController treeControl;
    static HashMap<String, String> questions;
    static boolean openedBefore = false;
    static Matrix matrix = new Matrix();

    //Variables estáticas que se llaman desde otras clases, para las cuales existen métodos
    private static String currentBug;
    private static LinkedList<Pair<String, String>> currentInfo;

    //Variables de la clase
    String currentQuestion;
    boolean extraQuestion = false;
    int currentExtraQuestions = 3;

    /**
     * This method describe the instance of the class
      * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_gui);

        //Clase dedicada para pasar parámetro
        currentBug = "Unknown";
        currentInfo = null;

         if (!openedBefore) {   //Si el árbol ya había sido inicializado, no se vuelve a inicializar
            questions = new HashMap<String, String>();
            try {
                matrix.loadArff(getResources().openRawResource(R.raw.dataset));
                this.loadQuestions();
            } catch (Exception e) {
                //File not found
            }
            openedBefore = true;
        }

        treeControl = new TreeController(matrix);
        //Linking between static buttons and actions
        ImageView btnGoHome = (ImageView) findViewById(R.id.btnBack);
        btnGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button backB = (Button) (findViewById(R.id.backButton));
        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button pressed = (Button) v;
                try {
                    catchAction(pressed);
                } catch (Exception e) {
                    //Capturar la exceptión
                }
            }
        });

        Button contB = (Button) (findViewById(R.id.naButton));
        contB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button pressed = (Button) v;
                try {
                    catchAction(pressed);
                } catch (Exception e) {
                    //Capturar la exceptión
                }
            }
        });
        currentQuestion = "";
        this.initialize();
    }

    /**
     * Inicialización de árbol
     */
    protected void initialize() {

        this.setCurrentQuestion();
    }

    /**
     * Set the current question and sends it to other method to publish it on the gui
     */
    protected void setCurrentQuestion() {

        if (extraQuestion) {
            if (currentExtraQuestions > 0) {
                displayOnScreen(hashLinkedToArray(treeControl.getQuestionAndOptions()));
                currentExtraQuestions--;
            } else {
                currentInfo = treeControl.getQuestionsRealized();
                try {
                    System.out.println("Finishin the activity");
                    terminarActividad();
                } catch (Exception e) {
                    //
                    System.out.println("Error finishing the frame");
                }
            }
        } else {
            boolean j = treeControl.isLeaf();
            if (!j) {
                displayOnScreen(hashLinkedToArray(treeControl.getQuestionAndOptions()));
            } else {
                extraQuestion = true;
            }
        }
    }

    /*
    *   Convert a LikedHashSet to an array of strings
    *   @param: LinkedHashSet<String> array
     */
    protected String[] hashLinkedToArray(LinkedHashSet<String> array) {
        String[] strArr = new String[array.size()];
        array.toArray(strArr);
        return strArr;
    }

    /*
    *   This method receive an array with the current questions and answers
    *   to choice to display on the screen.
    *   It generates dynamically buttons (options) depending on the current questions and answers,
    *   publishing it on the corresponding layout.
    *   @param: String[] questionsAndOptions
    *
    */
    protected void displayOnScreen(String[] questionsAndOptions) {
        ((LinearLayout) findViewById(R.id.dynamicAnswers)).removeAllViews();
        int arraySize = questionsAndOptions.length;
        if (arraySize > 0) {
            TextView question = (TextView) findViewById(R.id.questionID);
            String string = questions.get(questionsAndOptions[0]);
            currentQuestion = (string == null) ? questionsAndOptions[0] : string;
            question.setText(currentQuestion);
            LinearLayout answerContainer = (LinearLayout) findViewById(R.id.dynamicAnswers);

            for (int i = 1; i < arraySize; i++) {
                Button button = new Button(this);
                button.setText(questionsAndOptions[i]);
                button.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button pressed = (Button) v;
                        try {
                            catchAction(pressed);
                        } catch (Exception e) {
                            //Capturar la exceptión
                        }
                    }
                });
                answerContainer.addView(button, 0);
            }
        } else {
            currentBug = currentQuestion;
            currentInfo = treeControl.getQuestionsRealized();
            try {
                terminarActividad();
            } catch (Throwable e) {
                //
            }
        }
    }

    /**
     *   This method reacts to the button action
     *   It´s the listener for the buttons. It has a different action depending on the input
     * @param button
     * @throws AnswerException
     */
    public void catchAction(Button button) throws AnswerException {
        String textB = button.getText().toString();
        if (textB.equals("NA")) {
            ((LinearLayout) findViewById(R.id.dynamicAnswers)).removeAllViews();
            ((LinearLayout) findViewById(R.id.userAnswerLayout)).setVisibility(View.VISIBLE);
        } else if (textB.equals("Continuar")) {
            EditText answerBox = (EditText) findViewById(R.id.userAnswer);
            String userAnswer = answerBox.getText().toString();
            if (userAnswer.trim().equals("")) {
                treeControl.reply("NA");
            } else {
                treeControl.reply("NA",userAnswer);
            }
            ((LinearLayout) findViewById(R.id.userAnswerLayout)).setVisibility(View.INVISIBLE);
        } else {
            if (textB.equals("Volver a pregunta anterior")) {
                treeControl.goBack();
            } else {
                treeControl.reply(textB);
            }
        }
        displayOnScreen(hashLinkedToArray(treeControl.getQuestionAndOptions()));
    }

    /**
     * This method load the set of questions from the database
     * @throws Exception
     */
    public void loadQuestions() throws Exception {
        Consultor.getColl(new ServerCallback() {
            @Override
            public JSONObject onSuccess(JSONObject result) {
                ArrayList<Questions> questionsArray = JsonParserLF.parseQuestionsList(result);
                int totalQuestions = questionsArray.size();
                for (int i = 0; i < totalQuestions; i++) {
                    try {
                        questions.put(convert(questionsArray.get(i).getIdentificador().trim()), convert(questionsArray.get(i).getQuestion()));
                        Log.v("R:", convert(questionsArray.get(i).getIdentificador().trim()) + " " + convert(questionsArray.get(i).getQuestion()));

                    } catch (java.io.UnsupportedEncodingException e) {

                    }
                }
                return null;
            }

            @Override
            public JSONObject onFailure(JSONObject result) {
                System.out.println("Error descargando de BD");
                return null;
            }
        }, CollectionName.QUESTIONS);
    }



    /**
     * Converts the charset of the string sended by the DB.
     * @param string
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public String convert(String string) throws java.io.UnsupportedEncodingException {
        byte[] bytes = string.getBytes("ISO-8859-1");
        return new String(bytes);
    }

    public void terminarActividad(){
        Intent resultIntent = new Intent();
        resultIntent.putExtra("returning_from_classification", true);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    static String getCurrentBug(){
        return currentBug;
    }

    static LinkedList<Pair<String,String>> getCurrentInfo(){
        return currentInfo;
    }



}

