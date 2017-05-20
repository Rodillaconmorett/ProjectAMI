package is.ecci.ucr.projectami;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import is.ecci.ucr.projectami.DecisionTree.TreeController;

public class QuestionsGUI extends AppCompatActivity {
    final View linearLayout =  findViewById(R.id.answers);
    TreeController treeControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_gui);
        initializeTree();
    }

    protected void initializeTree(){
        treeControl = new TreeController();
    }

    protected void setQuestionAndAnswers(String[] questionsAndOptions){
        TextView question = (TextView) findViewById(R.id.questionID);
        for(int i = 0; i < 4 ; i++){
            TextView a = new TextView(this);
            a.setText("hola");
            ((LinearLayout) linearLayout).addView(a);

        }
    }

}
