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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Request;
import com.android.volley.Response;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class DBAdmin {
//Cambio

    //66c3a153-6438-44dd-bc15-fb57e44df93f

    public DBAdmin(Context  context) {
        Log.d("CREATION","START");
        //PRUEBAS
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://10.0.2.2:8080/LifeFinder/P4";

        Custom_Volly_Request jsonRequest;
        //JsonObjectRequest jsonRequest;
        Map<String, String> params = new HashMap<>();
        String key = "Authorization";
        String encodedString = Base64.encodeToString(String.format("%s:%s", "admin", "1234").getBytes(), Base64.NO_WRAP);
        String value = String.format("Basic %s", encodedString);
        params.put("Content-Type", "application/json");
        params.put(key,value);//put your parameters here
        jsonRequest = new Custom_Volly_Request(Request.Method.PUT, url, params,
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
                }
        );
//        jsonRequest = new JsonObjectRequest(Request.Method.PUT, url, null,
//                new Response.Listener<JSONObject>()
//                {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // display response
//                        Log.d("Response", response.toString());
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("Error.Response", error.toString());
//                    }
//                });
 //Add the request to the RequestQueue.
        queue.add(jsonRequest);
    }

    private void getConnection() {

    }


}

