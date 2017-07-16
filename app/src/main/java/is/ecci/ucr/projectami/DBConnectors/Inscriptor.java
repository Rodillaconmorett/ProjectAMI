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
import is.ecci.ucr.projectami.Bugs.BugMap;
import is.ecci.ucr.projectami.LogInfo;

/**
 * Created by alaincruzcasanova on 6/16/17.
 */

public class Inscriptor {

    private Inscriptor(){}

    /*-------------------------- INSERT SECTION -------------------------*/
    /*Métodos que utilizamos para insertar documentos a la base de datos.*/

    static public void insertSampling(LinkedList<Bug> bugs, String siteId, String userId, ServerCallback callback) {
        String url = Config.CONNECTION_STRING+CollectionName.SAMPLE;
        JSONArray arrayObject = new JSONArray();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(cal.getTime());
        try {
            for(int i = 0; i<bugs.size(); i++) {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("site_id", siteId);
                jsonBody.put("user_id", userId);
                jsonBody.put("date", formattedDate);
                JSONObject results = new JSONObject();
                results.put("bug_id", bugs.get(i).getFamily());
                results.put("qty", 1);
                jsonBody.put("results", results);
                arrayObject.put(jsonBody);
            }
            MongoAdmin.jsonPostRequest(arrayObject,url, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    static public void insertSamplingQuantity(LinkedList<BugMap> bugs, String siteId, String userId, ServerCallback callback) {
        String url = Config.CONNECTION_STRING+CollectionName.SAMPLE;
        JSONArray arrayObject = new JSONArray();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(cal.getTime());
        try {
            for(int i = 0; i<bugs.size(); i++) {
                JSONObject jsonBody = new JSONObject();
                JSONObject objIDSite = new JSONObject();
                JSONObject objIDUser = new JSONObject();
                jsonBody.put("site_id", siteId);
                jsonBody.put("user_id", userId);
                jsonBody.put("date", formattedDate);
                JSONObject results = new JSONObject();
                results.put("bug_id", bugs.get(i).getName());
                results.put("qty", bugs.get(i).getQuantity());
                jsonBody.put("results", results);
                arrayObject.put(jsonBody);
            }
            MongoAdmin.jsonPostRequest(arrayObject,url, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    static public void insertBug(String family, String bicho,String desc, int score, ServerCallback callback) {
        String url = Config.CONNECTION_STRING+CollectionName.BUGS;
        //Necesitamos incluir los parametros de datos
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("_id",bicho);
            jsonBody.put("family",family);
            jsonBody.put("score",score);
            if (desc != null) jsonBody.put("desc",desc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MongoAdmin.jsonPostRequest(jsonBody,url, callback);
    }

    static public void insertSite(String name, Double latitude, Double longitude, String description, String imagePath, ServerCallback callback) {
        String url = Config.CONNECTION_STRING+CollectionName.SITE;
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
        MongoAdmin.jsonPostRequest(jsonBody,url, callback);
    }

    static public void deleteAllUserSamples(String userEmail, ServerCallback serverCallback){
        String url = Config.CONNECTION_STRING+CollectionName.SAMPLE;
        url += "*?filter={user_id:\""+ userEmail +"\"}";
        MongoAdmin.jsonDeleteRequest(url,serverCallback);
    }

    static public void deleteDocByID(CollectionName coll, String id, ServerCallback serverCallback){
        String url = Config.CONNECTION_STRING+coll.toString();
        url += "/"+id;
        MongoAdmin.jsonDeleteRequest(url,serverCallback);
    }

}
