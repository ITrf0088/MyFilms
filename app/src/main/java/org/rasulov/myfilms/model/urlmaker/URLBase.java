package org.rasulov.myfilms.model.urlmaker;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public abstract class URLBase {

    public static final String VAL_LANG_RU = "ru-RU";
    public static final String VAL_LANG_EN = "en-US";
    final String PARAM_API_KEY = "api_key";
    final String VAL_API_KEY = "2ccd0b37a7a271991d6b6fd28a47ae82";
    String BASE_URL = "https://api.themoviedb.org/3";
    String CHILD_URL;

    Uri.Builder uri;
    HashMap<String, String> valParam;

    public URLBase() {
        this.valParam = new HashMap<>();
        valParam.put(VAL_LANG_RU, Params.LANGUAGE);
        valParam.put(VAL_LANG_EN, Params.LANGUAGE);
    }

    public abstract URLBase addParam(String value);

    public URL buildURL() throws MalformedURLException {
        return new URL(uri.build().toString());
    }

    public void add(String key, String value) {
        if (isNotKeyExisted(key)) {
            uri.appendQueryParameter(key, value);
        }
    }

    void add(String value) {
        String key = valParam.get(value);
        if (isNotKeyExisted(key)) {
            uri.appendQueryParameter(key, value);
        }
    }

    void addPage(int value) {
        String key = Params.PAGE;
        if (isNotKeyExisted(key)) {
            uri.appendQueryParameter(key, String.valueOf(value));
        }
    }


    private boolean isNotKeyExisted(String key) {
        return !uri.toString().contains("&" + key);
    }

    void createUri() {
        String finalUrl = BASE_URL + CHILD_URL + Params.START_PARAM + PARAM_API_KEY + "=" + VAL_API_KEY;
        uri = Uri.parse(finalUrl).buildUpon();
    }


    public static class Params {
        public static final String START_PARAM = "?";
        public static final String LANGUAGE = "language";
        public static final String PAGE = "page";
        public static final String SORT_BY = "sort_by";
        public static final String CERTIFICAT_LTorE = "certification.lte";
        public static final String CERTIFICATION_COUNTRY = "certification_country";
        public static final String WITH_GENRE = "with_genres";
        public static final String WITHOUT_GENRE = "without_genres";
        public static final String VOTE_COUNT_GTorE = "vote_count.gte";
    }

}
