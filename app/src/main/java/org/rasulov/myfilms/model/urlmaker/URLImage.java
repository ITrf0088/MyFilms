package org.rasulov.myfilms.model.urlmaker;

import android.net.Uri;

public class URLImage extends URLBase {

    public static final String POSTER_SIZE = "w500";
    public static final String BACKDROP_SIZE = "w780";
    public static final String ORIGINAL = "original";

    private String size;
    private String path;

    public URLImage(String size, String path) {
        this.size = size;
        this.path = path;
        createUri();
    }

    @Override
    void createUri() {
        String finalUrl = "https://image.tmdb.org/t/p/" + size + "/" + path;
        uri = Uri.parse(finalUrl).buildUpon();
    }

    @Override
    public URLImage addParam(String value) {
        return null;
    }


    public Uri buildUri() {
        return uri.build();
    }
}
