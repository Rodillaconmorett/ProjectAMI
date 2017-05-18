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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class DBAdmin {
//Cambio


    private String URL = "http://ec2-35-164-117-191.us-west-2.compute.amazonaws.com:8080";
    private String DATABASE_NAME = "LifeFinder";
    private String FINAL_URL = URL+"/"+DATABASE_NAME;
    private String AUTH_KEY = "Authorization";
    private String JSON_CONTENT_TYPE_KEK = "Content-Type";
    private String JSON_CONTENT_TYPE = "application/json";
    private RequestQueue queue;

    public DBAdmin(Context  context) {
        Log.d("CREATION","START");
        //Inicializamos la cola de consulta con el contexto de la aplicación
        queue = Volley.newRequestQueue(context);
    }

    public void addSite(String name, Double latitude, Double longitude, String description, String imagePath) throws JSONException {
        Log.d("ADD SITE","STARTING");
        String url =FINAL_URL+"/Site";
        Map<String, String> params = new HashMap<>();
        String key = "Authorization";
        String encodedString = Base64.encodeToString(String.format("%s:%s", "admin", "q1w2E3r4").getBytes(), Base64.NO_WRAP);
        String value = String.format("Basic %s", encodedString);
        params.put(key,value);//put your parameters here
        params.put("Content-Type","application/json");
        JSONObject coor = new JSONObject();
        coor.put("lat",latitude);
        coor.put("long",longitude);
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("name",name);
        jsonBody.put("coordinates",coor);
        Log.d("JSON",jsonBody.toString());
        if (description != null) jsonBody.put("description", description);
        if (imagePath != null) jsonBody.put("image",imagePath);
        jsonPostRequest(jsonBody,url,params);
    }

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
