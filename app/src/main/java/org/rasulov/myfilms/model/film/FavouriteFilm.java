package org.rasulov.myfilms.model.film;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "favourite_films")
public class FavouriteFilm extends Film {

    public FavouriteFilm(int uniqueId, int id, int voteCount, double voteAverage, String title, String originalTitle, String overview, String releaseDate, String backdropPath, String posterPath) {
        super(uniqueId, id, voteCount, voteAverage, title, originalTitle, overview, releaseDate, backdropPath, posterPath);
    }

    @Ignore
    public FavouriteFilm(Film film) {
        super(film.getUniqueId(), film.getId(), film.getVoteCount(), film.getVoteAverage(), film.getTitle(), film.getOriginalTitle(), film.getOverview(), film.getReleaseDate(), film.getBackdropPath(), film.getPosterPath());
    }
}
