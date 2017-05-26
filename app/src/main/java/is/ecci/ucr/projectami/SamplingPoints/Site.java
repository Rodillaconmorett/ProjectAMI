package is.ecci.ucr.projectami.SamplingPoints;

import java.io.Serializable;

/**
 * Created by Daniel on 5/10/2017.
 */

public class Site implements Serializable {

    private String objID;
    private String siteName;
    private double latitude;
    private double longitude;
    private String description;

    /**
     *
     * @param objID identificador único del sitio
     * @param siteName nombre del sitio
     * @param latitude latitud con respecto al sistema GPS de la ubicación del sitio en la superficie terrestre
     * @param longitude longitud con respecto al sistema GPS de la ubicación del sitio en la superficie terrestre
     * @param description descripción del sitio
     */
    public Site(String objID, String siteName, double latitude, double longitude, String description){
        this.objID = objID;
        this.siteName = siteName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    /**
     *
     * @return identificador único del sitio
     */
    public String getObjID() {return objID;}

    /**
     *
     * @return nombre del sitio
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     *
     * @return latitud con respecto al sistema GPS de la ubicación del sitio en la superficie terrestre
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     *
     * @return longitud con respecto al sistema GPS de la ubicación del sitio en la superficie terrestre
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     *
     * @return descripción del sitio
     */
    public String getDescription() {
        return description;
    }
}
