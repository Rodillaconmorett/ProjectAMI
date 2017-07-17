package is.ecci.ucr.projectami.Activities.UserLog;

import org.json.JSONObject;

/**
 * Created by Milton on 15-Jul-17.
 */

public class SampleLog {
    private String date;
    private String bugName;
    private String sampleID;
    private String siteID;
    private int quantity;
    public SampleLog(String dat, String name, String idSample, String sit, int n){
        date = dat;
        bugName = name;
        sampleID = idSample;
        siteID = sit;
        quantity = n;
    }

    public SampleLog(JSONObject sampleDoc) throws Exception {
        sampleID = sampleDoc.getJSONObject("_id").getString("$oid");
        date = sampleDoc.getString("date");
        bugName = sampleDoc.getJSONObject("results").getString("bug_id");
        quantity = Integer.parseInt(sampleDoc.getJSONObject("results").getString("qty"));
        siteID = sampleDoc.getString("site_id");
    }

    public String getDate() {
        return date;
    }

    public String getBugName() {
        return bugName;
    }

    public String getSampleID() {
        return sampleID;
    }

    public String getSiteID() {
        return siteID;
    }

    public int getQuantity() {
        return quantity;
    }
}
