package org.rasulov.myfilms.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.rasulov.myfilms.R;
import org.rasulov.myfilms.model.film.Film;
import org.rasulov.myfilms.model.urlmaker.URLImage;

import java.util.ArrayList;
import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmHolder> {


    private List<Film> films;
    private OnItemClickListener onItemClickListener;

    public FilmAdapter() {
        films = new ArrayList<>();
    }

    @NonNull
    @Override
    public FilmHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_item, parent, false);
        return new FilmHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmHolder holder, int position) {
        Log.d("it0088", "onBindViewHolder: " + position);
        Film film = films.get(position);
        ImageView imageView = holder.imageViewSmallPoster;
        URLImage uri = new URLImage(URLImage.ORIGINAL, film.getPosterPath());
        Picasso.get().load(uri.buildUri()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return films.size();
    }


    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
        notifyDataSetChanged();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void addAll(List<Film> filmss) {
        films.addAll(filmss);
        notifyDataSetChanged();
    }

    public void clear() {
        films.clear();
        notifyDataSetChanged();
    }


    class FilmHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewSmallPoster;

        public FilmHolder(@NonNull View itemView) {
            super(itemView);
            imageViewSmallPoster = itemView.findViewById(R.id.image_outside_poster);
            imageViewSmallPoster.setOnClickListener(v -> {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(getAdapterPosition());
            });
        }
    }
}
