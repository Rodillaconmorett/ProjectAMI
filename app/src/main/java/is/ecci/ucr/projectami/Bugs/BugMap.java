package is.ecci.ucr.projectami.Bugs;

/**
 * Created by alaincruzcasanova on 6/22/17.
 */

public class BugMap {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private int quantity;

    public BugMap(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
    }

}
