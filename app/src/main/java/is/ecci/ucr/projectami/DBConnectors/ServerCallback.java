package is.ecci.ucr.projectami.DBConnectors;

import org.json.JSONObject;

/**
 * Created by alaincruzcasanova on 6/16/17.
 */

public interface ServerCallback {

    JSONObject onSuccess(JSONObject result);
    JSONObject onFailure(JSONObject result);
}
