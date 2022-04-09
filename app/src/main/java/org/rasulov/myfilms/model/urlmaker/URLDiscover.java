package org.rasulov.myfilms.model.urlmaker;

public class URLDiscover extends URLBase {

    public static final String VAL_SORT_BY_POPULARITY = "popularity.desc";
    public static final String VAL_SORT_BY_TOP_RATED = "vote_average.desc";
    public static final String VAL_GENRE_ACTION = "28";
    public static final String VAL_GENRE_ADVENTURE = "12";
    public static final String VAL_GENRE_ANIMATION = "16";
    public static final String VAL_GENRE_FAMILY = "10751";
    public static final String VAL_GENRE_HISTORY = "36";
    public static final String VAL_GENRE_HORROR = "27";
    public static final String VAL_GENRE_MUSIC = "10402";
    public static final String VAL_GENRE_ROMANCE = "10749";
    public static final String VAL_GENRE_DRAMA = "18";
    public static final String VAL_RATING_G = "G";
    public static final String VAL_RATING_PG = "PG";
    public static final String VAL_RATING_PG_13 = "PG-13";
    public static final String VAL_RATING_R = "R";
    public static final String VAL_VOTE_GREATER_THAN_500 = "500";
    public static final String VAL_CERTIFICATE_US = "US";
    public static final String VAL_CERTIFICATE_RU = "RU";
    public static final String VAL_VOTE_GREATER_THAN_900 = "900";

    public URLDiscover() {
        valParam.put(VAL_SORT_BY_POPULARITY, Params.SORT_BY);
        valParam.put(VAL_SORT_BY_TOP_RATED, Params.SORT_BY);

        valParam.put(VAL_GENRE_ACTION, Params.WITH_GENRE);
        valParam.put(VAL_GENRE_ADVENTURE, Params.WITH_GENRE);
        valParam.put(VAL_GENRE_ANIMATION, Params.WITH_GENRE);
        valParam.put(VAL_GENRE_FAMILY, Params.WITH_GENRE);
        valParam.put(VAL_GENRE_HISTORY, Params.WITH_GENRE);

        valParam.put(VAL_CERTIFICATE_US, Params.CERTIFICATION_COUNTRY);
        valParam.put(VAL_CERTIFICATE_RU, Params.CERTIFICATION_COUNTRY);
        valParam.put(VAL_RATING_G, Params.CERTIFICAT_LTorE);
        valParam.put(VAL_RATING_PG, Params.CERTIFICAT_LTorE);
        valParam.put(VAL_RATING_PG_13, Params.CERTIFICAT_LTorE);
        valParam.put(VAL_RATING_R, Params.CERTIFICAT_LTorE);
        valParam.put(VAL_VOTE_GREATER_THAN_500, Params.VOTE_COUNT_GTorE);
        valParam.put(VAL_VOTE_GREATER_THAN_900, Params.VOTE_COUNT_GTorE);
        CHILD_URL = "/discover/movie";
        createUri();
    }


    @Override
    public URLDiscover addParam(String value) {
        add(value);
        return this;
    }

    public URLDiscover addParamPage(int value) {
        addPage(value);
        return this;
    }

    public URLDiscover addParam(String key, String value) {
        super.add(key, value);
        return this;
    }
}
