package is.ecci.ucr.projectami.Users;

import java.util.ArrayList;

/**
 * Created by alaincruzcasanova on 6/16/17.
 */

public class User {

    //Username
    private String email, password, firstName, lastName, dateCreated;

    private boolean validated;
    ArrayList<String> roles;

    public User(String newEmail, String newPassword, String newFirstName, String newLastName){
        this.email = newEmail;
        this.password = newPassword;
        this.firstName = newFirstName;
        this.lastName = newLastName;
        this.roles = new ArrayList<>();
    }

    public User(String newEmail, String newPassword, String newFirstName, String newLastName, String dateCreated, boolean validated){
        this.email = newEmail;
        this.password = newPassword;
        this.firstName = newFirstName;
        this.lastName = newLastName;
        this.dateCreated = dateCreated;
        this.validated = validated;
        this.roles = new ArrayList<>();
    }

    public void setRoles(ArrayList<String> newRoles){
        this.roles = newRoles;
    }

    public void addRoles(String role){
        this.roles.add(role);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }
}
