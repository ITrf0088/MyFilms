package org.rasulov.myfilms.view_model;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.rasulov.myfilms.model.urlmaker.URLBase;
import org.rasulov.myfilms.model.urlmaker.URLDiscover;

import java.util.HashSet;
import java.util.Set;

public class ViewModelPref extends AndroidViewModel {

    private FilmState filmState;


    public ViewModelPref(@NonNull Application application) {
        super(application);
        setFilmState();
    }

    private void setFilmState() {
        SharedPreferences state = getApplication().getSharedPreferences("filmState", Context.MODE_PRIVATE);
        int page = state.getInt(URLBase.Params.PAGE, 1);
        String sortBy = state.getString(URLBase.Params.SORT_BY, URLDiscover.VAL_SORT_BY_POPULARITY);
        String language = state.getString(URLBase.Params.LANGUAGE, URLDiscover.VAL_LANG_RU);
        Set<String> withoutGenres = state.getStringSet(URLBase.Params.WITHOUT_GENRE, new HashSet<>());
        filmState = new FilmState(page, sortBy, language, withoutGenres);
    }

    public void saveFilmState() {
        SharedPreferences preferences = getApplication().getSharedPreferences("filmState", Context.MODE_PRIVATE);
        preferences.edit()
                .putInt(URLBase.Params.PAGE, filmState.getPage())
                .putString(URLBase.Params.SORT_BY, filmState.getSortBy())
                .putString(URLBase.Params.LANGUAGE, filmState.getLanguage())
                .putStringSet(URLBase.Params.WITHOUT_GENRE, filmState.getWithoutGenres())
                .apply();
    }

    public FilmState getFilmState() {
        return filmState;
    }

}
