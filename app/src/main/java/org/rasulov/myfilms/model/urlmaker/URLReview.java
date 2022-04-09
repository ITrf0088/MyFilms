package org.rasulov.myfilms.model.urlmaker;

public class URLReview extends URLBase {

    private int idFilm;

    public URLReview(int idFilm) {
        this.idFilm = idFilm;
        CHILD_URL = String.format("/movie/%s/reviews", idFilm);
        createUri();
    }

    @Override
    public URLReview addParam(String value) {
        add(value);
        return this;
    }

    public URLReview addParamPage(int value) {
        addPage(value);
        return this;
    }
}
