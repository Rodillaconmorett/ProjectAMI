package is.ecci.ucr.projectami;

/**
 * Created by Oscar Azofeifa on 12/05/2017.
 */

public class LogInfo {

    /*--------------------------------------ATRIBUTOS---------------------------------------------*/
    private static String email, password, firstName, lastName;
    private static int age;
    private static boolean logged;

    public LogInfo(){

    }

    static public String getEmail() {
        return email;
    }

    static public void setEmail(String email) {
        LogInfo.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        LogInfo.password = password;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String firstName) {
        LogInfo.firstName = firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        LogInfo.lastName = lastName;
    }

    public static int getAge() {
        return age;
    }

    public static void setAge(int age) {
        LogInfo.age = age;
    }

    public static boolean isLogged() {
        return logged;
    }

    public static void setLogged(boolean logged) {
        LogInfo.logged = logged;
    }

}
