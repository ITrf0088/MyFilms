package org.rasulov.myfilms.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.rasulov.myfilms.R;
import org.rasulov.myfilms.model.film.Film;
import org.rasulov.myfilms.model.urlmaker.URLBase;
import org.rasulov.myfilms.model.urlmaker.URLDiscover;
import org.rasulov.myfilms.model.urlmaker.URLFactory;
import org.rasulov.myfilms.view.adapter.FilmAdapter;
import org.rasulov.myfilms.view_model.FilmState;
import org.rasulov.myfilms.view_model.ViewModelDB;
import org.rasulov.myfilms.view_model.ViewModelPref;
import org.rasulov.myfilms.view_model.ViewModelWeb;

import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ME = 2;
    private SwitchCompat switchCompat;
    private RecyclerView recyclerView;
    private FilmAdapter adapter;
    private ProgressBar progressBar;
    private Snackbar snackbar;
    private boolean canLoadWhenScrollIsInTheEnd = true;
    private ViewModelDB VM_DB;
    private ViewModelWeb VM_Web;
    private ViewModelPref VM_Pref;
    private FilmState state;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.main_toolbar_description));
        }

        VM_DB = new ViewModelProvider(this).get(ViewModelDB.class);
        VM_Web = new ViewModelProvider(this).get(ViewModelWeb.class);
        VM_Pref = new ViewModelProvider(this).get(ViewModelPref.class);
        switchCompat = findViewById(R.id.switch1);
        TextView textViewPopular = findViewById(R.id.textView_popular);
        TextView textViewRate = findViewById(R.id.textView_rate);
        recyclerView = findViewById(R.id.recycle_view);
        adapter = new FilmAdapter();
        progressBar = findViewById(R.id.progressBar);
        snackbar = Snackbar.make(this, recyclerView, getString(R.string.no_connection), Snackbar.LENGTH_LONG);
        state = VM_Pref.getFilmState();
        Log.d("TAG", "onCreate: " + state);

        if (state.getSortBy().equals(URLDiscover.VAL_SORT_BY_TOP_RATED))
            switchCompat.setChecked(true);


        recyclerView.setLayoutManager(new GridLayoutManager(this, getColumnCount()));
        recyclerView.setAdapter(adapter);
        recyclerView.setOnClickListener(v -> Toast.makeText(this, "sadsa", Toast.LENGTH_SHORT).show());
        adapter.setOnItemClickListener(this::startDetailActivity);
        switchCompat.setOnCheckedChangeListener(this::checkedChange);
        textViewPopular.setOnClickListener(v -> {
            if (switchCompat.isChecked()) {
                resetCache();
                switchCompat.setChecked(false);
                state.setSortBy(URLDiscover.VAL_SORT_BY_POPULARITY);
                loadFilms();
            }
        });

        textViewRate.setOnClickListener(v -> {
            if (!switchCompat.isChecked()) {
                resetCache();
                switchCompat.setChecked(true);
                state.setSortBy(URLDiscover.VAL_SORT_BY_TOP_RATED);
                loadFilms();
            }
        });


        setFilmsFromDB();

        observeLoadFilm();
        VM_Web.getLVDConnStatus().observe(this, hasNoConnection -> {
            if (hasNoConnection == null) return;
            if (!snackbar.isShown() && hasNoConnection) {
                snackbar.show();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                boolean canNotScrollVertically = !recyclerView.canScrollVertically(1);
                if (canNotScrollVertically && canLoadWhenScrollIsInTheEnd) {
                    loadFilms();
                }
            }
        });
    }

    private void resetCache() {
        state.resetPage();
        adapter.clear();
        VM_DB.deleteAllFilms();
    }

    private void loadFilms() {
        progressBar.setVisibility(View.VISIBLE);
        canLoadWhenScrollIsInTheEnd = false;

        URL urlRequest = URLFactory.create(state);
        if (urlRequest != null) {
            VM_Web.loadFilms(urlRequest);
        } else {
            Snackbar.make(recyclerView, "Что-то пошло не так!", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TAG", "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VM_Pref.saveFilmState();
    }

    private void observeLoadFilm() {
        MutableLiveData<List<Film>> lvdListFilmsWeb = VM_Web.getLVDListFilms();
        Observer<List<Film>> tag = films -> {
            if (films != null) {
                if (films.isEmpty()) {
                    Snackbar.make(recyclerView, getString(R.string.no_films), Snackbar.LENGTH_LONG).show();
                } else {
                    VM_DB.insertFilm(films);
                    adapter.addAll(films);
                    state.increasePage();
                    Log.d("TAG", "observeLoadFilm: " + state.getPage() + " " + films);
                }
            }
            progressBar.setVisibility(View.GONE);
            canLoadWhenScrollIsInTheEnd = true;
        };
        lvdListFilmsWeb.observe(this, tag);
    }


    private void checkedChange(CompoundButton compoundButton, boolean isChecked) {
        if (compoundButton.isPressed()) {
            resetCache();
            if (isChecked) {
                state.setSortBy(URLDiscover.VAL_SORT_BY_TOP_RATED);
            } else {
                state.setSortBy(URLDiscover.VAL_SORT_BY_POPULARITY);
            }
            loadFilms();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean isEN = state.getLanguage().equals(URLBase.VAL_LANG_EN);
        if (isEN) {
            menu.findItem(R.id.item_en).setChecked(true);
        } else {
            menu.findItem(R.id.item_ru).setChecked(true);
        }

        menu.findItem(R.id.item_action).setChecked(state.isNotInWithoutGenre(URLDiscover.VAL_GENRE_ACTION));
        menu.findItem(R.id.item_adventure).setChecked(state.isNotInWithoutGenre(URLDiscover.VAL_GENRE_ADVENTURE));
        menu.findItem(R.id.item_animation).setChecked(state.isNotInWithoutGenre(URLDiscover.VAL_GENRE_ANIMATION));
        menu.findItem(R.id.item_history).setChecked(state.isNotInWithoutGenre(URLDiscover.VAL_GENRE_HISTORY));
        menu.findItem(R.id.item_drama).setChecked(state.isNotInWithoutGenre(URLDiscover.VAL_GENRE_DRAMA));
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d("TAG", "onOptionsItemSelected: " + item.getTitle());
        int id = item.getItemId();
        if (id == R.id.menu_favourite) {
            startFavouriteActivity();
        } else {
            if (id == R.id.item_en) {
                if (!item.isChecked()) {
                    state.setLanguage(URLDiscover.VAL_LANG_EN);
                    item.setChecked(true);
                    resetCache();
                    loadFilms();
                }
            } else if (id == R.id.item_ru) {
                if (!item.isChecked()) {
                    state.setLanguage(URLDiscover.VAL_LANG_RU);
                    item.setChecked(true);
                    resetCache();
                    loadFilms();
                }
            } else if (id == R.id.item_action) {
                menuChanged(item, URLDiscover.VAL_GENRE_ACTION);
            } else if (id == R.id.item_adventure) {
                menuChanged(item, URLDiscover.VAL_GENRE_ADVENTURE);
            } else if (id == R.id.item_animation) {
                menuChanged(item, URLDiscover.VAL_GENRE_ANIMATION);
            } else if (id == R.id.item_drama) {
                menuChanged(item, URLDiscover.VAL_GENRE_DRAMA);
            } else if (id == R.id.item_history) {
                menuChanged(item, URLDiscover.VAL_GENRE_HISTORY);
            }

        }

        return super.onOptionsItemSelected(item);
    }

    private void menuChanged(MenuItem item, String genre) {
        if (item.isChecked()) {
            item.setChecked(false);
            state.addToWithoutGenres(genre);
        } else {
            item.setChecked(true);
            state.delFromWithoutGenres(genre);
        }

        resetCache();
        loadFilms();
    }

    private void startFavouriteActivity() {
        Intent intent = new Intent(this, FavouriteActivity.class);
        startActivity(intent);
        VM_Pref.saveFilmState();

    }

    private void startDetailActivity(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        Film film = adapter.getFilms().get(position);
        intent.putExtra("id", film.getId());
        intent.putExtra("fromActivity", ME);
        Log.d("detail", "startDetailActivity: " + state.getLanguage());
        VM_Pref.saveFilmState();
        startActivity(intent);
    }

    private void setFilmsFromDB() {
        try {
            List<Film> listFilms = VM_DB.getListFilms();
            Log.d("TAG", "setFilmsFromDB: " + listFilms.isEmpty());
            if (listFilms.isEmpty()) loadFilms();
            adapter.setFilms(listFilms);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getColumnCount() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int count = ((metrics.widthPixels * 160) / metrics.densityDpi) / 185;

        return Math.max(count, 2);
    }
}