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
    static private RequestQueue queue;
    static private Context context;

    //Singleton
    private MongoAdmin() {
    }

    static public void setContext(Context appContext) {
        Log.d("MONGO_ADMIN_SET_UP","START");
        //Inicializamos la cola de consulta con el contexto de la aplicación
        //Nota: El contexto, es el entorno donde se está ejecutando la aplicación,...
        //.. osea, el main en donde se crea el DB admin.
        context = appContext;
        queue = Volley.newRequestQueue(context);
    }

    /*------------------------- REQUEST SECTION -------------------------*/
    /*Código que útilizamos para realizar las consultas HTTP al servidor.*/


    //GET Request
    static public void jsonGetRequest(String url, Map<String, String> params,final ServerCallback callback) {
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
    static public void jsonPostRequest(JSONObject jsonBody, String url, Map<String, String> params, final ServerCallback callback) {
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


    static public void jsonPostRequest(JSONArray jsonBody, String url, Map<String, String> params, final ServerCallback callback) {
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
}