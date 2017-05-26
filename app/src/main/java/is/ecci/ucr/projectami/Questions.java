package is.ecci.ucr.projectami;

/**
 * Created by alaincruzcasanova on 5/25/17.
 */

public class Questions {

    private String question;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    private String identificador;

    public Questions(String question, String identificador) {
        this.question = question;
        this.identificador = identificador;
    }


}
