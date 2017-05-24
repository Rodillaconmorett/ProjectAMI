package is.ecci.ucr.projectami.DBConnectors;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by alaincruzcasanova on 5/24/17.
 */

public class CSVExporter {

    Context context;

    public CSVExporter(Context context) {
        this.context = context;
    }

    public void exportJsonToCSVWithHeaders(JSONObject jsonBody, String fileName) {
        ArrayList<String> csvData = new ArrayList<>();
        try {
            if (jsonBody.has("_embedded")) {
                JSONArray jsonArray = jsonBody.getJSONArray("_embedded");
                JSONObject headers = jsonArray.getJSONObject(0);
                csvData.add(getHeaders(headers));
                for (int i = 0; i<jsonArray.length(); i++) {
                    JSONObject docJson = jsonArray.getJSONObject(i);
                    csvData.add(parseToString(docJson));
                }
            } else {
                getHeaders(jsonBody);
                parseToString(jsonBody);
            }} catch (JSONException e) {
            e.printStackTrace();
        }
        //printArrayValues(csvData);

        exportToCSV(csvData, fileName);
    }

    public void exportJsonToCSVNoHeaders(JSONObject jsonBody, String fileName, String dirPath) {
        ArrayList<String> csvData = new ArrayList<>();
        try {
            if (jsonBody.has("_embedded")) {
                JSONArray jsonArray = jsonBody.getJSONArray("_embedded");
                for (int i = 0; i<jsonArray.length(); i++) {
                    JSONObject docJson = jsonArray.getJSONObject(i);
                    csvData.add(parseToString(docJson));
                }
            } else {
            }} catch (JSONException e) {
            e.printStackTrace();
        }
        exportToCSV(csvData, fileName);
    }

    private String parseToString(JSONObject jsonBody) {
        String result = "";
        Iterator<String> iter = jsonBody.keys();
        while (iter.hasNext()) {
            if (iter.next() != "_id") {
                try {
                    result += jsonBody.get(iter.next().toString()) + ",";
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        result = result.substring(0,result.length()-1);
        return result;
    }

    private String getHeaders(JSONObject jsonBody) {
        String result = "";
        Iterator<String> iter = jsonBody.keys();
        while (iter.hasNext()) {
            if (iter.next() != "_id") {
                result += iter.next() + ",";
            }
        }
        result = result.substring(0,result.length()-1);
        return result;
    }

    private void exportToCSV(ArrayList<String> array, String fileName) {
        try {
            File root = new File(context.getExternalFilesDir(null), "KeysData");
            File path = new File(root,fileName);
            if (!path.exists()) {
                path.createNewFile();
            }
            FileWriter writer = new FileWriter(path);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i<array.size(); i++) {
                sb.append(array.get(i));
                sb.append(System.getProperty("line.separator"));
            }
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {

        }
    }

    public File getKeysFile() {
        File root = new File(context.getExternalFilesDir(null), "KeysData");
        File path = new File(root,Config.CSV_KEYS);
        if (path.exists()){
            return path;
        } else {
            return null;
        }
    }

//    public void readFromFile(String fileName) {
//        try {
//            File root = new File(context.getExternalFilesDir(null), "KeysData");
//            File path = new File(root,fileName);
//            if (path.exists()){
//                FileInputStream fis = new FileInputStream(path);
//                InputStreamReader isr = new InputStreamReader(fis);
//                BufferedReader bufferedReader = new BufferedReader(isr);
//                StringBuilder sb = new StringBuilder();
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//                    sb.append(line);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
