package org.rasulov.myfilms.model.json;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class JSONLoad {

    public JSONObject load(URL url) {
        if (url != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url
                    .openConnection()
                    .getInputStream()))) {

                StringBuilder builder = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                return new JSONObject(builder.toString());
            } catch (IOException | JSONException e) {
                Log.e("it8800", e.getMessage(), e);
            }
        }
        return null;
    }
}
