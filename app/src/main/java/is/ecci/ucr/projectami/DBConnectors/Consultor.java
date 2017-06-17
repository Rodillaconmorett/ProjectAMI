package is.ecci.ucr.projectami.DBConnectors;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alaincruzcasanova on 6/16/17.
 */

public class Consultor {

    //Singleton
    private Consultor(){
    }

    //Default headers for the queries
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

    //Buscamos todos los documentos dentro de una colección
    static public void getColl(ServerCallback callback, CollectionName coll) {
        String getURL = Config.CONNECTION_STRING+coll.toString();
        Map<String, String> params = getDefaultParams();
        MongoAdmin.jsonGetRequest(getURL,params,
                callback);
    }

    //Lo usamos para restringir la cantidad de documentos que queremos mostrar
    static public void getColl(ServerCallback callback, CollectionName coll, int topDocs) {
        String getURL = Config.CONNECTION_STRING+coll.toString()+"?pagesize="+topDocs;
        Map<String, String> params = getDefaultParams();
        MongoAdmin.jsonGetRequest(getURL,params,callback);
    }

    //Buscamos un documento dentro de una colección por medio de su _id
    static public void getDocById(ServerCallback callback, CollectionName coll, String docId) {
        String getURL = Config.CONNECTION_STRING+coll.toString()+"/"+docId;
        Map<String, String> params = getDefaultParams();
        MongoAdmin.jsonGetRequest(getURL,params,callback);
    }

    /*--------------- KEYS UPDATE ----------------*/
    /*Actualizamos el archivo CSV con las llaves.*/

    static public void updateKeys(final Context context) {
        getColl(new ServerCallback() {
            @Override
            public JSONObject onSuccess(JSONObject result) {
                CSVExporter csvExporter = new CSVExporter(context);
                csvExporter.exportJsonToCSVWithHeaders(result,Config.CSV_KEYS);
                return null;
            }

            @Override
            public JSONObject onFailure(JSONObject result) {
                return null;
            }
        },CollectionName.KEYS);
    }

    /*---------------------------------- SPECIFIC GETS -----------------------------------*/
    /*Gets específicos a cada colleción. Estos no se pueden repetir con otras colecciones.*/

    static public void getSitesByName(ServerCallback callback, String name) {
        String url = Config.CONNECTION_STRING+CollectionName.SITE+"?filter={name:\""+ name +"\"}";
        Map<String, String> params = getDefaultParams();
        MongoAdmin.jsonGetRequest(url,params,callback);
    }

    static public void getBugsByIdRange(ServerCallback callback, ArrayList<String> bugs){
        String connectionString = Config.CONNECTION_STRING+CollectionName.BUGS;
        String filter = "?filter={_id:{\"$in\":[";
        for (int i = 0; i<bugs.size(); i++) {
            filter += "\""+bugs.get(i).toString()+"\"";
            if (i != bugs.size()-1) {
                filter += ",";
            }
        }
        filter += "]}}";
        String url = connectionString + filter;
        MongoAdmin.jsonGetRequest(url,getDefaultParams(),callback);
    }

    static public void getSamplesBySiteID(ServerCallback callback, String id) {
        String url = Config.CONNECTION_STRING+CollectionName.SAMPLE+"?filter={site_id:{\"$oid\":\""+ id +"\"}}";
        Log.d("URL:",url);
        Map<String, String> params = getDefaultParams();
        MongoAdmin.jsonGetRequest(url,params,callback);
    }

    //Las fechas son inclusivas (mayor o igual y menor o igual)
    //Formato de las fechas "YYYY-MM-DD"
    static public void getSamplesBySiteID(ServerCallback callback, String id, String initDate, String finalDate) {
        String connectionString = Config.CONNECTION_STRING+CollectionName.SAMPLE;
        String url = connectionString+"?filter={site_id:{\"$oid\":\""+ id +"\"},date:{\"$gte\":{\"$date\":\""+initDate+"\"},\"$lte\":{\"$date\":\"" + finalDate + "\"}}}";
        Log.d("URL:",url);
        Map<String, String> params = getDefaultParams();
        MongoAdmin.jsonGetRequest(url,params,callback);
    }
}
