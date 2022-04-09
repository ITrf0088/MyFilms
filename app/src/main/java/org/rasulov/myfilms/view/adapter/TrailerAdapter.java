package org.rasulov.myfilms.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rasulov.myfilms.R;
import org.rasulov.myfilms.model.film.Trailer;
import org.rasulov.myfilms.model.urlmaker.URLTrailer;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerHolder> {

    private List<Trailer> trailers;
    private OnTrailerClickListener trailerClickListener;

    public TrailerAdapter() {
        this.trailers = new ArrayList<>();
    }

    @NonNull
    @Override
    public TrailerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item,parent,false);
        return new TrailerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerHolder holder, int position) {
        holder.tvName.setText(trailers.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    public void setTrailerClickListener(OnTrailerClickListener trailerClickListener) {
        this.trailerClickListener = trailerClickListener;
    }

    class TrailerHolder extends RecyclerView.ViewHolder {
        ImageView imageTrailerPlay;
        TextView tvName;
        public TrailerHolder(@NonNull View itemView) {
            super(itemView);
            imageTrailerPlay = itemView.findViewById(R.id.image_trailer_play);
            tvName = itemView.findViewById(R.id.tv_trailer_name);
            tvName.setOnClickListener(v -> {
                if(trailerClickListener!=null){
                    String youTubeURL = URLTrailer.makeYouTubeURL(trailers.get(getAdapterPosition()).getKey());
                    trailerClickListener.onTrailerClick(youTubeURL);
                }
            });
            imageTrailerPlay.setOnClickListener(v -> {
                if(trailerClickListener!=null){
                    String youTubeURL = URLTrailer.makeYouTubeURL(trailers.get(getAdapterPosition()).getKey());
                    trailerClickListener.onTrailerClick(youTubeURL);
                }
            });
        }
    }

    public interface OnTrailerClickListener{
        void onTrailerClick(String url);
    }


}
