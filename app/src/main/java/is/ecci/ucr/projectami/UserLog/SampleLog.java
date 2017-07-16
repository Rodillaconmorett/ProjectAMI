package is.ecci.ucr.projectami.UserLog;

import java.util.Date;

/**
 * Created by Milton on 15-Jul-17.
 */

public class SampleLog {
    private Date date;
    private String bugName;
    private String sampleID;
    private String site;
    public SampleLog(Date dat, String name, String idSample, String sit){
        date = dat;
        bugName = name;
        sampleID = idSample;
        site = sit;
    }

    public Date getDate() {
        return date;
    }

    public String getBugName() {
        return bugName;
    }

    public String getSampleID() {
        return sampleID;
    }

    public String getSite() {
        return site;
    }
}
