package org.rasulov.myfilms.model.film.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.rasulov.myfilms.model.film.FavouriteFilm;
import org.rasulov.myfilms.model.film.Film;

@Database(entities = {Film.class, FavouriteFilm.class}, version = 3, exportSchema = false)
public abstract class FilmDataBase extends RoomDatabase {
    private static FilmDataBase dataBase;
    private static Object lock = new Object();

    public static FilmDataBase getInstance(Context context) {
        synchronized (lock) {
            if (dataBase == null) {
                dataBase = Room.databaseBuilder(context, FilmDataBase.class, "films.db")
                        .fallbackToDestructiveMigration().build();
            }
            return dataBase;
        }
    }

    public abstract FilmDao filmDao();
}
