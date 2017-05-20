package is.ecci.ucr.projectami.DBConnectors;

/**
 * Created by alaincruzcasanova on 5/16/17.
 */

import android.util.Base64;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

//La clase original no tiene los headers dentro de los métodos
//Decide extenderla y agregarle la funcionalidad
public class Custom_Volly_Request extends Request<JSONObject> {
    private Response.Listener<JSONObject> listener;
    private Map<String, String> headers;

    public Custom_Volly_Request(String url, Map<String, String> params,
                                Response.Listener<JSONObject> reponseListener,
                                Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.listener = reponseListener;
        this.headers = params;
    }

    public Custom_Volly_Request(int method, String url, Map<String, String> params,
                                Response.Listener<JSONObject> reponseListener,
                                Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.headers = params;
    }

    @Override
    public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {
        return headers;
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}
