package is.ecci.ucr.projectami;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import is.ecci.ucr.projectami.DecisionTree.TreeController;

import java.util.LinkedHashSet;

import android.widget.EditText;


public class QuestionsGUI extends AppCompatActivity {
    final View linearLayout = findViewById(R.id.answers);
    TreeController treeControl;
    LinkedHashSet<String> currentInfo;
    String currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_gui);
        treeControl = new TreeController();
        currentQuestion = "";
        //initializeTree();
    }

    protected void setCurrentQuestion() {
        if (!treeControl.isLeaf()) {
            displayOnScreen(hashLinkedToArray(treeControl.getQuestionAndOptions()));
        } else {
            //Se termina la clasificción y le envía al administrador de clasificacion el insecto encontrado actualmente
            //y un array de string con los posibles alores obtenidos de retroalimentación
            String found = hashLinkedToArray(treeControl.getQuestionAndOptions())[0];
            //this.endClass(found,currentInfo);
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
    *   @param: String[] questionsAndOptions
    */
    protected void displayOnScreen(String[] questionsAndOptions) {
        int arraySize = questionsAndOptions.length;
        if (arraySize > 0) {
            TextView question = (TextView) findViewById(R.id.questionID);
            currentQuestion = questionsAndOptions[0];
            question.setText(currentQuestion);
            LinearLayout answerContainer = (LinearLayout) findViewById(R.id.answers);

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
                        catchAction(pressed);
                    }
                });
                answerContainer.addView(button, 0);
            }
        }
    }

    /*
    *   This method reacts to the button action
     */
    public void catchAction(Button button) {
        String textB = button.getText().toString();
        if (textB.equals("NA")) {
            EditText userAnswer = (EditText) findViewById(R.id.userAnswer);
            currentInfo.add(currentQuestion + ":" + userAnswer.getText());
        } else {
            if (textB.equals("Retroceder")) {
                treeControl.goBack();
            } else {
                treeControl.reply(textB);
            }
        }
        displayOnScreen(hashLinkedToArray(treeControl.getQuestionAndOptions()));
    }

    public void test() {
        for (int i = 0; i < 4; i++) {
            TextView a = new TextView(this);
            a.setText("Hola");
            ((LinearLayout) linearLayout).addView(a);
        }
    }
}
