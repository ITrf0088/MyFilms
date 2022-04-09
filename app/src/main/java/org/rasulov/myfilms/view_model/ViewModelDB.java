package org.rasulov.myfilms.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rasulov.myfilms.model.AsyncLoader;
import org.rasulov.myfilms.model.film.FavouriteFilm;
import org.rasulov.myfilms.model.film.Film;
import org.rasulov.myfilms.model.film.database.FilmDataBase;

import java.util.List;

public class ViewModelDB extends AndroidViewModel {

    private static FilmDataBase dataBase;
    private LiveData<List<FavouriteFilm>> LVDFavouriteFilms;



    public ViewModelDB(@NonNull Application application) {
        super(application);
        dataBase = FilmDataBase.getInstance(application);
        LVDFavouriteFilms = dataBase.filmDao().getfavourites();

    }


    public LiveData<List<FavouriteFilm>> getLVDFavouriteFilms() {
        return LVDFavouriteFilms;
    }

    public List<Film> getListFilms() throws Exception {
        AsyncLoader<Void, List<Film>> loader = new AsyncLoader<>();
        loader.setTask(in -> dataBase.filmDao().getAllFilms());
        return loader.execute().get();
    }

    public Film getFilmById(int idFilm) throws Exception {
        AsyncLoader<Integer, Film> loader = new AsyncLoader<>();
        loader.setTask(id -> dataBase.filmDao().getFilmById(id));
        return loader.execute(idFilm).get();
    }


    public void insertFilm(List<Film> films) {
        AsyncLoader<List<Film>, Void> loader = new AsyncLoader<>();
        loader.setTask(in -> {
            dataBase.filmDao().insertFilm(in);
            return null;
        });

        loader.execute(films);
    }


    public void deleteFilm(Film film) {
        AsyncLoader<Film, Void> loader = new AsyncLoader<>();
        loader.setTask(in -> {
            dataBase.filmDao().deleteFilm(in);
            return null;
        });

        loader.execute(film);
    }


    public void deleteAllFilms() {
        AsyncLoader<Void, Void> loader = new AsyncLoader<>();
        loader.setTask(in -> {
            dataBase.filmDao().deleteAllFilms();
            return null;
        });

        loader.execute();
    }


    public FavouriteFilm getFavouriteById(int idFilm) throws Exception {
        AsyncLoader<Integer, FavouriteFilm> loader = new AsyncLoader<>();
        loader.setTask(id -> dataBase.filmDao().getFavouritemById(id));
        return loader.execute(idFilm).get();
    }


    public void insertFavourite(FavouriteFilm film) {
        AsyncLoader<FavouriteFilm, Void> loader = new AsyncLoader<>();
        loader.setTask(in -> {
            dataBase.filmDao().insertFavouriteFilm(in);
            return null;
        });

        loader.execute(film);
    }


    public void deleteFavourite(FavouriteFilm film) {
        AsyncLoader<FavouriteFilm, Void> loader = new AsyncLoader<>();
        loader.setTask(in -> {
            dataBase.filmDao().deleteFavouriteFilm(in);
            return null;
        });
        loader.execute(film);
    }


}

