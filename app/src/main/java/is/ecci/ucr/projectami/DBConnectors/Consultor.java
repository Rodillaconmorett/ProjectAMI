package is.ecci.ucr.projectami.DBConnectors;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import is.ecci.ucr.projectami.LogInfo;

/**
 * Created by alaincruzcasanova on 6/16/17.
 */

public class Consultor {

    //Singleton
    private Consultor(){
    }

    //Buscamos todos los documentos dentro de una colección
    static public void getColl(ServerCallback callback, CollectionName coll) {
        String getURL = Config.CONNECTION_STRING+coll.toString();
        MongoAdmin.jsonGetRequest(getURL,callback);
    }

    //Lo usamos para restringir la cantidad de documentos que queremos mostrar
    static public void getColl(ServerCallback callback, CollectionName coll, int topDocs) {
        String getURL = Config.CONNECTION_STRING+coll.toString()+"?pagesize="+topDocs;
        MongoAdmin.jsonGetRequest(getURL,callback);
    }

    //Buscamos un documento dentro de una colección por medio de su _id
    static public void getDocById(ServerCallback callback, CollectionName coll, String docId) {
        String getURL = Config.CONNECTION_STRING+coll.toString()+"/"+docId;
        MongoAdmin.jsonGetRequest(getURL,callback);
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
        MongoAdmin.jsonGetRequest(url,callback);
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
        MongoAdmin.jsonGetRequest(url,callback);
    }

    static public void getSamplesBySiteID(ServerCallback callback, String id) {
        String url = Config.CONNECTION_STRING+CollectionName.SAMPLE+"?filter={site_id:\""+ id +"\"}";
        Log.d("URL:",url);
        MongoAdmin.jsonGetRequest(url,callback);
    }

    //Las fechas son inclusivas (mayor o igual y menor o igual)
    //Formato de las fechas "YYYY-MM-DD"
    static public void getSamplesBySiteID(ServerCallback callback, String id, String initDate, String finalDate) {
        String connectionString = Config.CONNECTION_STRING+CollectionName.SAMPLE;
        String url = connectionString+"?filter={site_id:{\"$oid\":\""+ id +"\"},date:{\"$gte\":{\"$date\":\""+initDate+"\"},\"$lte\":{\"$date\":\"" + finalDate + "\"}}}";
        Log.d("URL:",url);
        MongoAdmin.jsonGetRequest(url,callback);
    }

    static public void getBugsFamily(ServerCallback callback){
        String connectionString = Config.CONNECTION_STRING+CollectionName.BUGS;
        String url = connectionString+"/_aggrs/family_aggr";
        MongoAdmin.jsonGetRequest(url,callback);
    }
}



//****** Ejemplo para bajar las familias de bichos con sus respectivos scores ******
//    ArrayList<BugFamily> bugs = new ArrayList<>();
//        Consultor.getBugsFamily(new ServerCallback() {
//@Override
//public JSONObject onSuccess(JSONObject result) {
//        getBugs(JsonParserLF.parseBugsFamilyArrays(result));
//        return null;
//        }
//
//@Override
//public JSONObject onFailure(JSONObject result) {
//        return null;
//        }
//        });
//
//
//    private void getBugs(ArrayList<BugFamily> bugs) {
//        for (int i = 0; i<bugs.size(); i++) {
//            Log.v("##### Look at me #####",bugs.get(i).getNameFamily());
//        }
//    }