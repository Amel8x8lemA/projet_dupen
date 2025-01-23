package com.example.projet_dupen;

import static android.content.ContentValues.TAG;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyTask extends AsyncTask<String, String, String> {

    private final OnPostExecutedCallback onPostExecutedCallback;

    public MyTask(OnPostExecutedCallback onPostExecutedCallback) {
        this.onPostExecutedCallback = onPostExecutedCallback;
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuilder result = new StringBuilder();
        try {
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL("https://trouve-mot.fr/api/sizemin/6");

                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader isw = new InputStreamReader(in);
                int data = isw.read();
                while (data != -1) {
                    result.append((char) data);
                    data = isw.read();
                }
                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    @Override
    protected void onPostExecute(String wordJsonString) {
        super.onPostExecute(wordJsonString);
        if (wordJsonString != null) {
            try {
                JSONArray jsonArray = new JSONArray(wordJsonString);

                if (jsonArray.length() > 0) {
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String mot = jsonObject.getString("name");

                    onPostExecutedCallback.onPostExecuted(mot);
                }
            } catch (JSONException e) {
                Log.e(TAG, "Error parsing JSON", e);
            }
        } else {
            Log.e(TAG, "No data received from API");
        }
    }

}