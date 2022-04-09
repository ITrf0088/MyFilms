package org.rasulov.myfilms.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.rasulov.myfilms.R;
import org.rasulov.myfilms.model.film.FavouriteFilm;
import org.rasulov.myfilms.model.film.Film;
import org.rasulov.myfilms.view.adapter.FilmAdapter;
import org.rasulov.myfilms.view_model.ViewModelDB;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {

    public static final int ME = 1;
    private RecyclerView recyclerView;
    private FilmAdapter adapter;
    private ViewModelDB viewModelDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        getSupportActionBar().setTitle(getResources().getString(R.string.favourite_toolbar_description));
        recyclerView = findViewById(R.id.recycle_view_favourite);
        recyclerView.setLayoutManager(new GridLayoutManager(this, getColumnCount()));
        adapter = new FilmAdapter();
        adapter.setOnItemClickListener(this::startDetailActivity);
        recyclerView.setAdapter(adapter);

        viewModelDB = new ViewModelProvider(this).get(ViewModelDB.class);
        LiveData<List<FavouriteFilm>> favouriteFilms = viewModelDB.getLVDFavouriteFilms();
        favouriteFilms.observe(this, films -> {
            adapter.setFilms(new ArrayList<>(films));
        });

    }

    private void startDetailActivity(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        Film film = adapter.getFilms().get(position);
        intent.putExtra("id", film.getId());
        intent.putExtra("fromActivity", ME);
        startActivity(intent);
    }

    public int getColumnCount() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int count = ((metrics.widthPixels * 160) / metrics.densityDpi) / 185;

        return Math.max(count, 2);
    }
}