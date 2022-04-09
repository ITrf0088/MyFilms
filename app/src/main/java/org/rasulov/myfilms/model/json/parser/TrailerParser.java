package org.rasulov.myfilms.model.json.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rasulov.myfilms.model.film.Trailer;

import java.util.ArrayList;
import java.util.List;

public class TrailerParser implements Parser<Trailer> {

    private JSONObject jsonObject;

    public TrailerParser(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public List<Trailer> parse() {
        if (jsonObject == null) return null;
        List<Trailer> trailers = new ArrayList<>();
        try {
            JSONArray results = jsonObject.getJSONArray("results");

            if (results.length() == 0) return trailers;

            for (int i = 0; i < results.length(); i++) {
                JSONObject jsonObject = results.getJSONObject(i);
                String key = jsonObject.getString("key");
                String name = jsonObject.getString("name");
                trailers.add(new Trailer(key, name));
            }
            return trailers;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
