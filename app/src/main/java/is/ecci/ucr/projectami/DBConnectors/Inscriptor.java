package is.ecci.ucr.projectami.DBConnectors;

import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import is.ecci.ucr.projectami.Bugs.Bug;

/**
 * Created by alaincruzcasanova on 6/16/17.
 */

public class Inscriptor {

    private Inscriptor(){}

    //Este método nos sirve para disminuir la repetición de este código
    //Nota: Para la siguiente etapa, hay que cambiar la autenticación
    static private Map<String,String> getDefaultParams() {
        Map<String, String> params = new HashMap<>();
        String encodedString = Base64.encodeToString(String.format("%s:%s", Config.AUTH_USER, Config.AUTH_USER_PASS).getBytes(), Base64.NO_WRAP);
        String value = String.format("Basic %s", encodedString);
        Log.i("user&pass",encodedString);
        params.put(Config.AUTH_KEY,value);
        params.put(Config.JSON_CONTENT_TYPE_KEY,Config.JSON_CONTENT_TYPE);
        params.put("authenticationDatabase",Config.DATABASE_NAME);
        return params;
    }

    /*-------------------------- INSERT SECTION -------------------------*/
    /*Métodos que utilizamos para insertar documentos a la base de datos.*/

    static public void insertSampling(LinkedList<Bug> bugs, String siteId, String userId, ServerCallback callback) {
        String url = Config.CONNECTION_STRING+CollectionName.SAMPLE;
        Map<String, String> params = getDefaultParams();
        JSONArray arrayObject = new JSONArray();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(cal.getTime());
        try {
            for(int i = 0; i<bugs.size(); i++) {
                JSONObject jsonBody = new JSONObject();
                JSONObject objIDSite = new JSONObject();
                JSONObject objIDUser = new JSONObject();
                objIDSite.put("$oid",siteId);
                jsonBody.put("site_id", objIDSite);
                objIDUser.put("$oid",userId);
                jsonBody.put("user_id", objIDUser);
                jsonBody.put("date", formattedDate);
                JSONObject results = new JSONObject();
                results.put("bug_id", bugs.get(i).getFamily());
                results.put("qty", 1);
                jsonBody.put("results", results);
                arrayObject.put(jsonBody);
            }
            MongoAdmin.jsonPostRequest(arrayObject,url,params, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    static public void insertBug(String family, String desc, Double score, String[] imagesPaths, ServerCallback callback) {
        String url = Config.CONNECTION_STRING+CollectionName.BUGS;
        Map<String, String> params = getDefaultParams();
        //Necesitamos incluir los parametros de datos
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("family",family);
            jsonBody.put("score",score);
            if (imagesPaths != null) {
                JSONArray imageArray = new JSONArray();
                for (int i = 0; i<imagesPaths.length; i++) {
                    imageArray.put(imagesPaths[i]);
                }
                jsonBody.put("images",imageArray);
            }
            if (desc != null) jsonBody.put("desc",desc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MongoAdmin.jsonPostRequest(jsonBody,url,params, callback);
    }

    static public void insertSite(String name, Double latitude, Double longitude, String description, String imagePath, ServerCallback callback) {
        String url = Config.CONNECTION_STRING+CollectionName.SITE;
        Map<String, String> params = getDefaultParams();
        //Necesitamos incluir los parametros de datos
        JSONObject jsonBody = new JSONObject();
        JSONObject coor = new JSONObject();
        try {
            coor.put("lat", latitude);
            coor.put("long", longitude);
            jsonBody.put("name", name);
            jsonBody.put("coordinates", coor);
            Log.d("JSON", jsonBody.toString());
            if (description != null) jsonBody.put("description", description);
            if (imagePath != null) jsonBody.put("image", imagePath);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MongoAdmin.jsonPostRequest(jsonBody,url,params, callback);
    }
}
