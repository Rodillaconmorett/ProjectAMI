package is.ecci.ucr.projectami.DBConnectors;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

import is.ecci.ucr.projectami.Bugs.Bug;
import is.ecci.ucr.projectami.Bugs.BugFamily;
import is.ecci.ucr.projectami.Questions;
import is.ecci.ucr.projectami.SamplingPoints.Site;
import is.ecci.ucr.projectami.UserLog.SampleLog;
import is.ecci.ucr.projectami.Users.User;

/**
 * Created by alaincruzcasanova on 5/22/17.
 */

public class JsonParserLF {

    //Parsers
    private JsonParserLF() {

    }

    public static ArrayList<Site> parseSites(JSONObject response) {
        ArrayList<Site> sites = new ArrayList<>();
        try {
        if (response.has("_embedded")) {
            JSONArray jsonArray = response.getJSONArray("_embedded");
            for (int i = 0; i<jsonArray.length(); i++) {
                JSONObject docJson = jsonArray.getJSONObject(i);
                sites.add(readSite(docJson));
            }
        } else {
            sites.add(readSite(response));
        }} catch (JSONException e) {
            e.printStackTrace();
        }
        return  sites;
    }

    public static User parseUsers(JSONObject response) {
        User user = new User("","","","");
        try{
            if(response.has("_embedded")){
                JSONArray jsonArray = response.getJSONArray("_embedded");
                for (int i = 0; i<jsonArray.length(); i++) {
                    JSONObject docJson = jsonArray.getJSONObject(i);
                    user = readUser(docJson);
                }
            } else {
                user = readUser(response);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static ArrayList<User> parseArrayUsers(JSONObject response) {
        ArrayList<User> user = new ArrayList<>();
        try{
            if(response.has("_embedded")){
                JSONArray jsonArray = response.getJSONArray("_embedded");
                for (int i = 0; i<jsonArray.length(); i++) {
                    JSONObject docJson = jsonArray.getJSONObject(i);
                    user.add(readUser(docJson));
                }
            } else {
                user.add(readUser(response));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static LinkedList<Bug> parseBugs(JSONObject response) {
        LinkedList<Bug> bugs = new LinkedList<>();
        try {
            if (response.has("_embedded")) {
                JSONArray jsonArray = response.getJSONArray("_embedded");
                for (int i = 0; i<jsonArray.length(); i++) {
                    JSONObject docJson = jsonArray.getJSONObject(i);
                    bugs.add(readBug(docJson));
                }
            } else {
                bugs.add(readBug(response));
            }} catch (JSONException e) {
            e.printStackTrace();
        }
        return  bugs;
    }

    public static ArrayList<Bug> parseBugsArray(JSONObject response) {
        ArrayList<Bug> bugs = new ArrayList<>();
        try {
            if (response.has("_embedded")) {
                JSONArray jsonArray = response.getJSONArray("_embedded");
                for (int i = 0; i<jsonArray.length(); i++) {
                    JSONObject docJson = jsonArray.getJSONObject(i);
                    bugs.add(readBug(docJson));
                }
            } else {
                bugs.add(readBug(response));
            }} catch (JSONException e) {
            e.printStackTrace();
        }
        return  bugs;
    }

    public static ArrayList<BugFamily> parseBugsFamilyArrays(JSONObject response) {
        ArrayList<BugFamily> bugFamilies = new ArrayList<>();
        try {
            if (response.has("_embedded")) {
                JSONArray jsonArray = response.getJSONArray("_embedded");
                for (int i = 0; i<jsonArray.length(); i++) {
                    JSONObject docJson = jsonArray.getJSONObject(i);
                    bugFamilies.add(readBugFamily(docJson));
                }
            } else {
                bugFamilies.add(readBugFamily(response));
            }} catch (JSONException e) {
            e.printStackTrace();
        }
        return  bugFamilies;
    }

    public static ArrayList<String> parseSampleBugList(JSONObject response) {
        ArrayList<String> bugs = new ArrayList<>();
        try {
            if (response.has("_embedded")) {
                JSONArray jsonArray = response.getJSONArray("_embedded");
                for (int i = 0; i<jsonArray.length(); i++) {
                    JSONObject docJson = jsonArray.getJSONObject(i);
                    bugs.add(readBugID(docJson));
                }
            } else {
                bugs.add(readBugID(response));
            }} catch (JSONException e) {
            e.printStackTrace();
        }
        return bugs;
    }

    public static ArrayList<SampleLog> parseSamples(JSONObject response) {
        ArrayList<SampleLog> userSamples = new ArrayList<>();

        try {
            if (response.has("_embedded")) {
                JSONArray jsonArray = response.getJSONArray("_embedded");
                for (int i = 0; i<jsonArray.length(); i++) {
                    JSONObject docJson = jsonArray.getJSONObject(i);
                    userSamples.add(new SampleLog(docJson));
                }
            } else {
                userSamples.add(new SampleLog(response));
            }} catch (Exception e) {
            e.printStackTrace();
        }
        return  userSamples;
    }
    public static ArrayList<Questions> parseQuestionsList(JSONObject response) {
        ArrayList<Questions> questions = new ArrayList<>();
        try {
            if (response.has("_embedded")) {
                JSONArray jsonArray = response.getJSONArray("_embedded");
                for (int i = 0; i<jsonArray.length(); i++) {
                    JSONObject docJson = jsonArray.getJSONObject(i);
                    questions.add(readQuestion(docJson));
                }
            } else {
                questions.add(readQuestion(response));
            }} catch (JSONException e) {
            e.printStackTrace();
        }
        return questions;
    }

    //Readers

    private static User readUser(JSONObject userDoc) throws JSONException {
        String email = userDoc.getString("_id");
        String pass = userDoc.getString("password");
        String firstName = userDoc.getJSONObject("name").getString("first");
        String lastName = userDoc.getJSONObject("name").getString("last");
        String validated = userDoc.getString("validated");
        ArrayList<String> arrayRoles = new ArrayList<>();
        boolean validatedBool;
        validatedBool = validated != "false";
        String date = userDoc.getString("date_created");
        JSONArray roles = userDoc.getJSONArray("roles");
        for(int i = 0; i<roles.length(); i++){
            arrayRoles.add(roles.get(i).toString());
        }
        User user = new User(email,pass,firstName,lastName,date,validatedBool);
        user.setRoles(arrayRoles);
        return user;
    }

    private static Site readSite(JSONObject siteDoc) throws JSONException {
        String objID = siteDoc.getJSONObject("_id").getString("$oid");
        String name = siteDoc.getString("name");
        String desc;
        JSONObject coor = siteDoc.getJSONObject("coordinates");
        Double latitude = coor.getDouble("lat");
        Double longitude = coor.getDouble("long");
        if (siteDoc.has("desc")) {
            desc = siteDoc.getString("desc");
        } else {
            desc = "N/A";
        }
        return new Site(objID,name,latitude,longitude,desc);
    }

    private static Questions readQuestion(JSONObject questionDoc) throws  JSONException {
        String question = questionDoc.getString("Pregunta");
        String identifier = questionDoc.getString("identificador");
        return new Questions(question,identifier);
    }

    private static Bug readBug(JSONObject bugDoc) throws JSONException {
        String objID = bugDoc.getString("_id");
        String familia = bugDoc.getString("familia");
        String desc;
        double score = bugDoc.getDouble("score");
        if (bugDoc.has("desc")) {
            desc = bugDoc.getString("desc");
        } else {
            desc = "N/A";
        }
        return new Bug(familia,score,desc);
    }

    private static BugFamily readBugFamily(JSONObject bugFamDoc) throws JSONException {
        String family = bugFamDoc.getString("_id");
        double score = bugFamDoc.getDouble("avg_score");
        return new BugFamily(family,score,0);
    }

    private static String readBugID(JSONObject sampleDoc) throws  JSONException {
        JSONObject jsonObject = sampleDoc.getJSONObject("results");
        String bugID = jsonObject.getString("bug_id");
        return bugID;
    }

    public static String convert(String string) {
        byte[] bytes = null;
        try {
            bytes = string.getBytes("ISO-8859-1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(bytes);
    }

}
