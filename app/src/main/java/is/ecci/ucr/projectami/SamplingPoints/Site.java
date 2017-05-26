package is.ecci.ucr.projectami.SamplingPoints;

/**
 * Created by Daniel on 5/10/2017.
 */

public class Site {

    private String objID;
    private String siteName;
    private double latitude;
    private double longitude;
    private String description;

    public Site(String objID, String siteName, double latitude, double longitude, String description){
        this.objID = objID;
        this.siteName = siteName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    public String getObjID() {return objID;}

    public String getSiteName() {
        return siteName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getDescription() {
        return description;
    }

    public String toString() { return "_id:"+ objID + ", name:" + siteName + ", lat:" + latitude + ", long:" + longitude;}

}
