package is.ecci.ucr.projectami.Users;

/**
 * Created by alaincruzcasanova on 7/17/17.
 */

public class StaticUser {

    public static User user;

    private StaticUser(){

    }

    public static void setUser(User newUser) {
        user = newUser;
    }

}
