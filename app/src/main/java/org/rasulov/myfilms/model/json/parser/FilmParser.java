package org.rasulov.myfilms.model.json.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rasulov.myfilms.model.film.Film;

import java.util.ArrayList;
import java.util.List;

public class FilmParser implements Parser<Film> {

    private JSONObject jsonObject;

    public FilmParser(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public List<Film> parse() {
        if (jsonObject == null) return null;

        try {
            JSONArray results = jsonObject.getJSONArray("results");

            List<Film> films = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                JSONObject jsonObject = results.getJSONObject(i);
                int id = jsonObject.getInt("id");
                int voteCount = jsonObject.getInt("vote_count");
                double vote_average = jsonObject.getDouble("vote_average");
                String title = jsonObject.getString("title");
                String original_title = jsonObject.getString("original_title");
                String overview = jsonObject.getString("overview");
                String release_date = jsonObject.getString("release_date");
                String backdrop_path = jsonObject.getString("backdrop_path");
                String poster_path = jsonObject.getString("poster_path");

                Film film = new Film(
                        id,
                        voteCount,
                        vote_average,
                        title,
                        original_title,
                        overview,
                        release_date,
                        backdrop_path,
                        poster_path);
                films.add(film);
            }

            return films;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
