package is.ecci.ucr.projectami.SamplingPoints;

/**
 * Created by Daniel on 5/10/2017.
 */

public class Site {

    private String siteName;
    private double latitude;
    private double longitude;
    private String description;

    public Site(String siteName, double latitude, double longitude, String description){
        this.siteName = siteName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

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
}
