package org.rasulov.myfilms.model.json.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rasulov.myfilms.model.film.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewParser implements Parser<Review> {

    private JSONObject jsonObject;

    public ReviewParser(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public List<Review> parse() {
        if (jsonObject == null) return null;
        List<Review> reviews = new ArrayList<>();
        try {
            JSONArray results = jsonObject.getJSONArray("results");

            if (results.length() == 0) return reviews;

            for (int i = 0; i < results.length(); i++) {
                JSONObject jsonObject = results.getJSONObject(i);
                String author = jsonObject.getString("author");
                String content = jsonObject.getString("content");
                reviews.add(new Review(author, content));
            }
            return reviews;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
