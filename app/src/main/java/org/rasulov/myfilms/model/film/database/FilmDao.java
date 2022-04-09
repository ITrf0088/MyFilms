package org.rasulov.myfilms.model.film.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import org.rasulov.myfilms.model.film.FavouriteFilm;
import org.rasulov.myfilms.model.film.Film;

import java.util.List;

@Dao
public interface FilmDao {

    @Query("SELECT * FROM films")
    List<Film> getAllFilms();

    @Query("SELECT * FROM films WHERE id ==:id")
    Film getFilmById(int id);

    @Query("DELETE FROM films")
    void deleteAllFilms();

    @Insert
    void insertFilm(List<Film> films);

    @Delete
    void deleteFilm(Film film);


    @Query("SELECT * FROM favourite_films")
    LiveData<List<FavouriteFilm>> getfavourites();

    @Query("SELECT * FROM favourite_films WHERE id ==:id")
    FavouriteFilm getFavouritemById(int id);

    @Insert
    void insertFavouriteFilm(FavouriteFilm film);

    @Delete
    void deleteFavouriteFilm(FavouriteFilm film);
}
