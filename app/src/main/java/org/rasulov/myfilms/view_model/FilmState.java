package org.rasulov.myfilms.view_model;

import androidx.annotation.NonNull;

import java.util.Set;

public class FilmState {

    private int page;
    private String sortBy;
    private String language;
    private Set<String> withoutGenres;

    public FilmState(int page, String sortBy, String language, Set<String> withoutGenres) {
        this.page = page;
        this.sortBy = sortBy;
        this.language = language;
        this.withoutGenres = withoutGenres;
    }

    public void increasePage() {
        ++page;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void delFromWithoutGenres(String genre) {
        withoutGenres.remove(genre);
    }

    public void addToWithoutGenres(String genre) {
        withoutGenres.add(genre);
    }

    public String getWithoutGenresAsString() {
        StringBuilder builder = new StringBuilder();
        for (String genre : withoutGenres) {
            builder.append(genre).append(",");
        }

        return builder.substring(0, builder.toString().length());
    }

    public Set<String> getWithoutGenres() {
        return withoutGenres;
    }

    public int getPage() {
        return page;
    }

    public String getSortBy() {
        return sortBy;
    }

    public String getLanguage() {
        return language;
    }

    public void resetPage() {
        this.page = 1;
    }

    @NonNull
    @Override
    public String toString() {
        return page + "\n"
                + sortBy + "\n"
                + language + "\n"
                + withoutGenres + "\n";
    }

    public boolean isNotInWithoutGenre(String genre) {
        return !withoutGenres.contains(genre);
    }
}
