package org.rasulov.myfilms.view_model;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;
import org.rasulov.myfilms.model.AsyncLoader;
import org.rasulov.myfilms.model.film.Film;
import org.rasulov.myfilms.model.film.Review;
import org.rasulov.myfilms.model.film.Trailer;
import org.rasulov.myfilms.model.json.JSONLoad;
import org.rasulov.myfilms.model.json.parser.FilmParser;
import org.rasulov.myfilms.model.json.parser.ReviewParser;
import org.rasulov.myfilms.model.json.parser.TrailerParser;
import org.rasulov.myfilms.model.urlmaker.URLBase;
import org.rasulov.myfilms.model.urlmaker.URLReview;
import org.rasulov.myfilms.model.urlmaker.URLTrailer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ViewModelWeb extends AndroidViewModel {


    private MutableLiveData<List<Film>> LVDListFilms;
    private MutableLiveData<List<Trailer>> LVDListTrailers;
    private MutableLiveData<List<Review>> LVDListReviews;
    private MutableLiveData<Boolean> LVDConnStatus;

    public ViewModelWeb(@NonNull Application application) {
        super(application);
        LVDListTrailers = new MutableLiveData<>();
        LVDListReviews = new MutableLiveData<>();
        LVDConnStatus = new MutableLiveData<Boolean>() {
            @Override
            protected void onInactive() {
                super.onInactive();
                setValue(null);
            }
        };
        LVDListFilms = new MutableLiveData<List<Film>>() {
            @Override
            protected void onInactive() {
                super.onInactive();
                setValue(null);
            }
        };
    }



    public void loadFilms(URL urlRequest) {
        AsyncLoader<URL, List<Film>> loadParseFilms = new AsyncLoader<>();
        loadParseFilms.setTask(url -> {
            JSONLoad jsonLoad = new JSONLoad();
            JSONObject jsonObject = jsonLoad.load(url);
            FilmParser parser = new FilmParser(jsonObject);
            return parser.parse();
        });

        loadParseFilms.setPostExecuteTask(result -> {
            LVDListFilms.postValue(result);
            LVDConnStatus.postValue(result == null);
        });

        loadParseFilms.execute(urlRequest);
    }


    public void loadTrailers(int filmId,String language) {
        AsyncLoader<URL, List<Trailer>> loadParseTrailers = new AsyncLoader<>();
        loadParseTrailers.setTask(url -> {
            JSONLoad load = new JSONLoad();
            JSONObject object = load.load(url);
            TrailerParser parser = new TrailerParser(object);
            return parser.parse();
        });
        loadParseTrailers.setPostExecuteTask(result -> LVDListTrailers.postValue(result));

        URLTrailer urlTrailer = new URLTrailer(filmId);
        urlTrailer.addParam(language);
        URL url = null;
        try {
            url = urlTrailer.buildURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        loadParseTrailers.execute(url);
    }

    public void loadReviews(int filmId,String language) {
        AsyncLoader<URL, List<Review>> loadParseReviews = new AsyncLoader<>();
        loadParseReviews.setTask(url -> {
            JSONLoad load = new JSONLoad();
            JSONObject jsonObject = load.load(url);
            ReviewParser reviewParser = new ReviewParser(jsonObject);
            return reviewParser.parse();
        });
        loadParseReviews.setPostExecuteTask(result -> LVDListReviews.postValue(result));

        URLReview urlReview = new URLReview(filmId);
        urlReview.addParam(language);
        URL url = null;
        try {
            url = urlReview.buildURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        loadParseReviews.execute(url);
    }

    public MutableLiveData<List<Film>> getLVDListFilms() {
        return LVDListFilms;
    }

    public MutableLiveData<List<Trailer>> getLVDListTrailers() {
        return LVDListTrailers;
    }

    public MutableLiveData<List<Review>> getLVDListReviews() {
        return LVDListReviews;
    }

    public MutableLiveData<Boolean> getLVDConnStatus() {
        return LVDConnStatus;
    }
}
