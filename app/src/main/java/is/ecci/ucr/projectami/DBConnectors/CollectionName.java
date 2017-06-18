package is.ecci.ucr.projectami.DBConnectors;

/**
 * Created by alaincruzcasanova on 5/20/17.
 */

public enum CollectionName {

    SITE("/Site",0),
    BUGS("/Bugs",1),
    SAMPLE("/Sample",2),
    USERS("/users",3),
    KEYS("/ClassKey",4),
    QUESTIONS("/Questions",5);

    private String stringValue;
    private int intValue;

    CollectionName(String toString, int value){
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
