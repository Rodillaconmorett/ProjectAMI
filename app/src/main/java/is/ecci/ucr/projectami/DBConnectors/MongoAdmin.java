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
import java.util.HashMap;
import java.util.Map;


public class MongoAdmin {

    //RESTHEART api
    //Para conocer más, ingresen al siguiente enlace: http://restheart.org
    private RequestQueue queue;

    public MongoAdmin(Context  context) {
        Log.d("CREATION","START");
        //Inicializamos la cola de consulta con el contexto de la aplicación
        //Nota: El contexto, es el entorno donde se está ejecutando la aplicación,...
        //.. osea, el main en donde se crea el DB admin.
        queue = Volley.newRequestQueue(context);
    }

    //Este método nos sirve para disminuir la repetición de este código
    //Nota: Para la siguiente etapa, hay que cambiar la autenticación
    private Map<String,String> getDefaultParams() {
        Map<String, String> params = new HashMap<>();
        String encodedString = Base64.encodeToString(String.format("%s:%s", "admin", "q1w2E3r4").getBytes(), Base64.NO_WRAP);
        String value = String.format("Basic %s", encodedString);
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

    public void insertSampling(String bugId, String siteId, int quantity, String userId) {

    }

    private void insertBug(String family, String desc, Double score, String[] imagesPaths) {
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
        jsonPostRequest(jsonBody,url,params);
    }

    public void insertSite(String name, Double latitude, Double longitude, String description, String imagePath) {
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
        jsonPostRequest(jsonBody,url,params);
    }

    /*---------------------------------- SPECIFIC GETS -----------------------------------*/
    /*Gets específicos a cada colleción. Estos no se pueden repetir con otras colecciones.*/

    public void getSitesByName(ServerCallback callback, CollectionName coll, String name) {
        String url = Config.CONNECTION_STRING+CollectionName.SITE+"?filter={name:\""+ name +"\"}";
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
    private void jsonPostRequest(JSONObject jsonBody, String url, Map<String, String> params) {
        final String requestBody = jsonBody.toString();
        Custom_Volly_Request jsonRequest;
        jsonRequest = new Custom_Volly_Request(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response: ", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError response)
                    {
                        Log.d("Response: Error", response.toString());
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

}
