package is.ecci.ucr.projectami.DBConnectors;

import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import is.ecci.ucr.projectami.Users.User;

/**
 * Created by alaincruzcasanova on 6/20/17.
 */

public class UserManagers {

    private UserManagers(){}

    static public void logInUser(String email, String password, ServerCallback callback){
        Map<String, String> params = new HashMap<>();
        String getURL = Config.CONNECTION_STRING_USERS+CollectionName.USERS.toString()+"/"+email;
        String encodedString = Base64.encodeToString(String.format("%s:%s", email, password).getBytes(), Base64.NO_WRAP);
        String value = String.format("Basic %s", encodedString);
        params.put(Config.AUTH_KEY,value);
        params.put("authenticationDatabase",Config.DATABASE_NAME_AUTH);
        params.put(Config.JSON_CONTENT_TYPE_KEY,Config.JSON_CONTENT_TYPE);
        MongoAdmin.jsonGetRequestUsers(getURL,params,callback);
    }

    static public void addNewUser(User user, ServerCallback callback){
        Map<String, String> params = new HashMap<>();
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("_id", user.getEmail());
            jsonBody.put("password", user.getPassword());
            JSONArray roles = new JSONArray();
            if (!user.getRoles().isEmpty()) {
                for (int i = 0; i < user.getRoles().size(); i++) {
                    roles.put(user.getRoles().get(i));
                }
                jsonBody.put("roles",roles);
            } else {
                jsonBody.put("roles",roles);
            }
            JSONObject names = new JSONObject();
            names.put("first",user.getFirstName());
            names.put("last",user.getLastName());
            jsonBody.put("name",names);
            jsonBody.put("validated",false);
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(cal.getTime());
            jsonBody.put("date_created",formattedDate.toString());
            JSONArray jsonArray = new JSONArray();
            jsonArray.put("recolector");
            jsonArray.put("usuario");
            jsonBody.put("roles",jsonArray);
            Log.d("****Added User:",jsonBody.toString());
        } catch (JSONException e) {
            Log.i("Error parsing User",e.toString());
        }
        String getURL = Config.CONNECTION_STRING_USERS+CollectionName.USERS;
        params.put(Config.JSON_CONTENT_TYPE_KEY,Config.JSON_CONTENT_TYPE);
        MongoAdmin.jsonPostRequest(jsonBody,getURL,callback);
    }

    static public void updateUser(User user, ServerCallback callback){
        Map<String, String> params = new HashMap<>();
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("password", user.getPassword());
            JSONArray roles = new JSONArray();
            if (!user.getRoles().isEmpty()) {
                for (int i = 0; i < user.getRoles().size(); i++) {
                    roles.put(user.getRoles().get(i));
                }
                jsonBody.put("roles",roles);
            } else {
                jsonBody.put("roles",roles);
            }
            JSONObject names = new JSONObject();
            names.put("first",user.getFirstName());
            names.put("last",user.getLastName());
            jsonBody.put("name",names);
            if(user.isValidated()){
                jsonBody.put("validated",user.isValidated());
            }
            Log.d("****Updated User:",jsonBody.toString());
        } catch (JSONException e) {
            Log.i("Error parsing User",e.toString());
        }
        String getURL = Config.CONNECTION_STRING_USERS+CollectionName.USERS+"/"+user.getEmail();
        params.put(Config.JSON_CONTENT_TYPE_KEY,Config.JSON_CONTENT_TYPE);
        MongoAdmin.jsonPatchRequest(jsonBody,getURL,callback);
    }

}

/*Example #1 - Insert a simple user*/
//        User user = new User("elruso2@gmail.com","super","elruso","super");
//        user.addRoles("consultor");
//        UserManagers.addNewUser(user, new ServerCallback() {
//            @Override
//            public JSONObject onSuccess(JSONObject result) {
//                Log.i("Maybe",result.toString());
//                return result;
//            }
//
//            @Override
//            public JSONObject onFailure(JSONObject result) {
//                Log.i("Maybe",result.toString());
//                return null;
//            }
//        });
