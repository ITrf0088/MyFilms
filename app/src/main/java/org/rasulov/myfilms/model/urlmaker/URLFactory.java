package org.rasulov.myfilms.model.urlmaker;

import org.rasulov.myfilms.view_model.FilmState;

import java.net.MalformedURLException;
import java.net.URL;

public class URLFactory {

    public static URL create(FilmState state) {
        try {
            URLDiscover discover = new URLDiscover();
            discover.addParamPage(state.getPage())
                    .addParam(state.getLanguage())
                    .addParam(state.getSortBy())
                    .addParam(URLDiscover.VAL_GENRE_FAMILY)
                    .addParam(URLDiscover.VAL_VOTE_GREATER_THAN_900)
                    .addParam(URLBase.Params.WITHOUT_GENRE, state.getWithoutGenresAsString() +
                            "," + URLDiscover.VAL_GENRE_MUSIC +
                            "," + URLDiscover.VAL_GENRE_HORROR +
                            "," + URLDiscover.VAL_GENRE_ROMANCE);

            return discover.buildURL();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
