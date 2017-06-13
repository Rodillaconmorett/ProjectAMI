package is.ecci.ucr.projectami.DBConnectors;
/**
 * Created by alaincruzcasanova on 5/12/17.
 */

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import is.ecci.ucr.projectami.Bugs.Bug;


public class MongoAdmin {

    //RESTHEART api
    //Para conocer más, ingresen al siguiente enlace: http://restheart.org
    private RequestQueue queue;
    private Context context;

    public MongoAdmin(Context  context) {
        Log.d("CREATION","START");
        //Inicializamos la cola de consulta con el contexto de la aplicación
        //Nota: El contexto, es el entorno donde se está ejecutando la aplicación,...
        //.. osea, el main en donde se crea el DB admin.
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    //Este método nos sirve para disminuir la repetición de este código
    //Nota: Para la siguiente etapa, hay que cambiar la autenticación
    private Map<String,String> getDefaultParams() {
        Map<String, String> params = new HashMap<>();
        String encodedString = Base64.encodeToString(String.format("%s:%s", Config.AUTH_USER, Config.AUTH_USER_PASS).getBytes(), Base64.NO_WRAP);
        String value = String.format("Basic %s", encodedString);
        Log.i("user&pass",encodedString);
        params.put(Config.AUTH_KEY,value);
        params.put(Config.JSON_CONTENT_TYPE_KEY,Config.JSON_CONTENT_TYPE);
        return params;
    }

    public interface ServerCallback{
        JSONObject onSuccess(JSONObject result);
        JSONObject onFailure(JSONObject result);
    }

    //Buscamos un documento dentro de una colección por medio de su _id
    public void getDocById(ServerCallback callback, CollectionName coll, String docId) {
        String getURL = Config.CONNECTION_STRING+coll.toString()+"/"+docId;
        Map<String, String> params = getDefaultParams();
        jsonGetRequest(getURL,params,callback);
    }

    //Buscamos todos los documentos dentro de una colección
    public void getColl(ServerCallback callback, CollectionName coll) {
        String getURL = Config.CONNECTION_STRING+coll.toString();
        Map<String, String> params = getDefaultParams();
        jsonGetRequest(getURL,params,callback);
    }

    //Lo usamos para restringir la cantidad de documentos que queremos mostrar
    public void getColl(ServerCallback callback, CollectionName coll, int topDocs) {
        String getURL = Config.CONNECTION_STRING+coll.toString()+"?pagesize="+topDocs;
        Map<String, String> params = getDefaultParams();
        jsonGetRequest(getURL,params,callback);
    }

    /*-------------------------- INSERT SECTION -------------------------*/
    /*Métodos que utilizamos para insertar documentos a la base de datos.*/

    public void insertSampling(LinkedList<Bug> bugs, String siteId, String userId, ServerCallback callback) {
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
            jsonPostRequest(arrayObject,url,params, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void insertBug(String family, String desc, Double score, String[] imagesPaths, ServerCallback callback) {
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
        jsonPostRequest(jsonBody,url,params, callback);
    }

    public void insertSite(String name, Double latitude, Double longitude, String description, String imagePath, ServerCallback callback) {
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
        jsonPostRequest(jsonBody,url,params, callback);
    }

    /*---------------------------------- SPECIFIC GETS -----------------------------------*/
    /*Gets específicos a cada colleción. Estos no se pueden repetir con otras colecciones.*/

    public void getSitesByName(ServerCallback callback, String name) {
        String url = Config.CONNECTION_STRING+CollectionName.SITE+"?filter={name:\""+ name +"\"}";
        Map<String, String> params = getDefaultParams();
        jsonGetRequest(url,params,callback);
    }

    public void getBugsByIdRange(ServerCallback callback, ArrayList<String> bugs){
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
        jsonGetRequest(url,getDefaultParams(),callback);
    }

    public void getSamplesBySiteID(ServerCallback callback, String id) {
        String url = Config.CONNECTION_STRING+CollectionName.SAMPLE+"?filter={site_id:{\"$oid\":\""+ id +"\"}}";
        Log.d("URL:",url);
        Map<String, String> params = getDefaultParams();
        jsonGetRequest(url,params,callback);
    }

    //Las fechas son inclusivas (mayor o igual y menor o igual)
    //Formato de las fechas "YYYY-MM-DD"
    public void getSamplesBySiteID(ServerCallback callback, String id, String initDate, String finalDate) {
        String connectionString = Config.CONNECTION_STRING+CollectionName.SAMPLE;
        String url = connectionString+"?filter={site_id:{\"$oid\":\""+ id +"\"},date:{\"$gte\":{\"$date\":\""+initDate+"\"},\"$lte\":{\"$date\":\"" + finalDate + "\"}}}";
        Log.d("URL:",url);
        Map<String, String> params = getDefaultParams();
        jsonGetRequest(url,params,callback);
    }

    /*------------------------- REQUEST SECTION -------------------------*/
    /*Código que útilizamos para realizar las consultas HTTP al servidor.*/


    //GET Request
    private void jsonGetRequest(String url, Map<String, String> params,final ServerCallback callback) {
        Custom_Volly_Request jsonRequest;
        jsonRequest = new Custom_Volly_Request(Request.Method.GET, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response: ", response.toString());
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError response)
                    {
                        Log.d("Response: Error", response.toString());
                        JSONObject jsonFailed = new JSONObject();
                        try {
                            jsonFailed.put("failed","true");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onFailure(jsonFailed);
                    }
                });
        queue.add(jsonRequest);
    }

    //POST Request
    private void jsonPostRequest(JSONObject jsonBody, String url, Map<String, String> params, final ServerCallback callback) {
        final String requestBody = jsonBody.toString();
        Custom_Volly_Request jsonRequest;
        jsonRequest = new Custom_Volly_Request(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response: ", response.toString());
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError response)
                    {
                        Log.d("Response: Error", response.toString());
                        JSONObject jsonFailed = new JSONObject();
                        try {
                            jsonFailed.put("failed","true");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onFailure(jsonFailed);
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Encoding no valido de %s usando %s", requestBody, "utf-8");
                    return null;
                }
            }};
        queue.add(jsonRequest);
    }


    private void jsonPostRequest(JSONArray jsonBody, String url, Map<String, String> params, final ServerCallback callback) {
        final String requestBody = jsonBody.toString();
        Custom_Volly_Request jsonRequest;
        jsonRequest = new Custom_Volly_Request(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response: ", response.toString());
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError response)
                    {
                        Log.d("Response: Error", response.toString());
                        JSONObject jsonFailed = new JSONObject();
                        try {
                            jsonFailed.put("failed","true");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onFailure(jsonFailed);
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Encoding no valido de %s usando %s", requestBody, "utf-8");
                    return null;
                }
            }};
        queue.add(jsonRequest);
    }

    /*--------------- KEYS UPDATE ----------------*/
    /*Actualizamos el archivo CSV con las llaves.*/


    public void updateKeys() {
        this.getColl(new ServerCallback() {
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
}


/*--------------- Ejemplos ------------------*/

// ------- Ejm#1: Buscamos todos los bichos encontrados en un sitio dado en un rango de fechas

//db = new MongoAdmin(this.getApplicationContext());
//        //Recibimos el server callback, el id del sitio y las fechas (las fecha son opcionales)
//        //Nota: Existe el mismo método, pero sin parametros de fechas
//        db.getSamplesBySiteID(new MongoAdmin.ServerCallback() {
//            @Override
//            public JSONObject onSuccess(JSONObject result) {
//                ArrayList<String> bugs = JsonParserLF.parseSampleBugList(result);
//                db.getBugsByIdRange(new MongoAdmin.ServerCallback() {
//                    @Override
//                    public JSONObject onSuccess(JSONObject result) {
//                        return null;
//                    }
//
//                    @Override
//                    public JSONObject onFailure(JSONObject result) {
//                        return null;
//                    }
//                },bugs);
//                return null;
//            }
//
//            @Override
//            public JSONObject onFailure(JSONObject result) {
//                return null;
//            }
//        },"591c832d409c8f2661424e99","2017-05-20","2018-01-01");