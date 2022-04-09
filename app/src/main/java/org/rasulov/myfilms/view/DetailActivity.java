package org.rasulov.myfilms.view;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.rasulov.myfilms.R;
import org.rasulov.myfilms.model.film.FavouriteFilm;
import org.rasulov.myfilms.model.film.Film;
import org.rasulov.myfilms.model.film.Review;
import org.rasulov.myfilms.model.film.Trailer;
import org.rasulov.myfilms.model.urlmaker.URLImage;
import org.rasulov.myfilms.view.adapter.ReviewAdapter;
import org.rasulov.myfilms.view.adapter.TrailerAdapter;
import org.rasulov.myfilms.view_model.ViewModelDB;
import org.rasulov.myfilms.view_model.ViewModelPref;
import org.rasulov.myfilms.view_model.ViewModelWeb;

import java.util.List;

public class DetailActivity extends AppCompatActivity {


    private ViewModelDB viewModelDB;
    private ViewModelWeb viewModelWeb;
    private FavouriteFilm favouriteFilm;
    private Film film;

    private Toolbar toolbar;
    private ImageView imagePoster;
    private TextView tvTitleContent;
    private TextView tvOriginalTitleContent;
    private TextView tvVoteAverageContent;
    private TextView tvReleaseDateContent;
    private TextView tvOverviewContent;
    private TextView tvOverviewMissing;
    private FloatingActionButton floatingButton;

    private RecyclerView recyclerReview, recyclerTrailer;
    private ReviewAdapter reviewAdapter;
    private TrailerAdapter trailerAdapter;
    private ViewModelPref VM_Pref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.d("TAG", "onCreate: ");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        VM_Pref = new ViewModelProvider(this).get(ViewModelPref.class);
        imagePoster = findViewById(R.id.image_inside_poster);
        tvTitleContent = findViewById(R.id.tv_title_content);
        tvOriginalTitleContent = findViewById(R.id.tv_original_title_content);
        tvVoteAverageContent = findViewById(R.id.tv_vote_average_content);
        tvReleaseDateContent = findViewById(R.id.tv_release_date_content);
        tvOverviewContent = findViewById(R.id.tv_overview_content);
        tvOverviewMissing = findViewById(R.id.tv_missing_overview_content);

        floatingButton = findViewById(R.id.floating_button);

        recyclerReview = findViewById(R.id.recycle_view_review);
        recyclerTrailer = findViewById(R.id.recycle_view_trailer);
        trailerAdapter = new TrailerAdapter();
        reviewAdapter = new ReviewAdapter();
        viewModelDB = new ViewModelProvider(this).get(ViewModelDB.class);
        viewModelWeb = new ViewModelProvider(this).get(ViewModelWeb.class);


        initializeFilm();
        initializeFavouriteFilmForFloatingButton();
        floatingButton.setOnClickListener(this::floatingButtonClicked);

        MutableLiveData<List<Trailer>> lvdListTrailers = viewModelWeb.getLVDListTrailers();
        lvdListTrailers.observe(this, trailers -> {
            Log.d("TAG1", "trailers: " + trailers);
            if (trailers != null) {
                recyclerTrailer.setLayoutManager(new LinearLayoutManager(this));
                recyclerTrailer.setAdapter(trailerAdapter);
                trailerAdapter.setTrailerClickListener(this::trailerClicked);
                trailerAdapter.setTrailers(trailers);

            }
        });


        MutableLiveData<List<Review>> lvdListReviews = viewModelWeb.getLVDListReviews();
        lvdListReviews.observe(this, reviews -> {
            Log.d("TAG1", "reviews: " + reviews);
            if (reviews != null) {
                recyclerReview.setLayoutManager(new LinearLayoutManager(this));
                recyclerReview.setAdapter(reviewAdapter);
                reviewAdapter.setReviews(reviews);
            }
        });


        if (savedInstanceState == null) {
            String language = VM_Pref.getFilmState().getLanguage();
            Log.d("detail", "onCreate: " + language);
            viewModelWeb.loadTrailers(film.getId(), language);
            viewModelWeb.loadReviews(film.getId(), language);
        }

    }

    private void initializeFilm() {
        setFilm();
        if (film != null) {
            Uri uri = new URLImage(URLImage.ORIGINAL, film.getPosterPath()).buildUri();
            Picasso.get().load(uri).placeholder(R.drawable.place_big).into(imagePoster);
            toolbar.setTitle(film.getTitle());
            tvTitleContent.setText(film.getTitle());
            tvOriginalTitleContent.setText(film.getOriginalTitle());
            tvVoteAverageContent.setText(String.valueOf(film.getVoteAverage()));
            tvReleaseDateContent.setText(film.getReleaseDate());
            String overview = film.getOverview();
            if (!overview.isEmpty()) {
                tvOverviewContent.setText(overview);
                tvOverviewMissing.setText("");
            }
        }
    }

    private void setFilm() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            int id = intent.getIntExtra("id", -1);
            int fromActivity = intent.getIntExtra("fromActivity", -1);
            if (id != -1 && fromActivity != -1) {
                if (setFilm(id, fromActivity)) return;
            }
        }

        finish();
    }

    private boolean setFilm(int id, int fromActivity) {
        try {
            if (fromActivity == FavouriteActivity.ME) {
                film = viewModelDB.getFavouriteById(id);
                return true;
            } else if (fromActivity == MainActivity.ME) {
                film = viewModelDB.getFilmById(id);
                return true;
            }
        } catch (Exception ignored) {
        }
        return false;
    }


    private void initializeFavouriteFilmForFloatingButton() {
        try {
            int id = film.getId();
            favouriteFilm = viewModelDB.getFavouriteById(id);
            if (favouriteFilm != null) {
                floatingButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal_700)));
            } else {
                floatingButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void floatingButtonClicked(View view) {
        try {
            if (favouriteFilm != null) {
                viewModelDB.deleteFavourite(favouriteFilm);
                Snackbar.make(floatingButton, getString(R.string.deleted_from_favourite), Snackbar.LENGTH_LONG).show();
            } else {
                viewModelDB.insertFavourite(new FavouriteFilm(film));
                Snackbar.make(floatingButton, getString(R.string.added_to_favourite), Snackbar.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        initializeFavouriteFilmForFloatingButton();
    }


    private void trailerClicked(String youtubeURL) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeURL));
        startActivity(intent);
    }

}
