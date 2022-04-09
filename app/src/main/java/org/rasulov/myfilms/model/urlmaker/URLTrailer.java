package org.rasulov.myfilms.model.urlmaker;

public class URLTrailer extends URLBase {

    private static final String YOUTUBE = "https://www.youtube.com/watch?v=";
    private int idFilm;

    public URLTrailer(int idFilm) {
        this.idFilm = idFilm;
        CHILD_URL = String.format("/movie/%s/videos", idFilm);
        createUri();
    }

    @Override
    public URLTrailer addParam(String value) {
        add(value);
        return this;
    }

    public static String makeYouTubeURL(String key) {
        return YOUTUBE + key;
    }
}
