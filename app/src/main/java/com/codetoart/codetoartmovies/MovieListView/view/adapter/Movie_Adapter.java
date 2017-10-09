package com.codetoart.codetoartmovies.MovieListView.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codetoart.codetoartmovies.ApiManager.Models.UpcomingMoviesDataModel;
import com.codetoart.codetoartmovies.BuildConfig;
import com.codetoart.codetoartmovies.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Keyur Tailor on 09-Oct-17.
 */


public class Movie_Adapter extends RecyclerView.Adapter<Movie_Adapter.MovieViewHolder> {
    public static SimpleDateFormat SIMPLE_DATE = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    public static SimpleDateFormat DB_DATE = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    ArrayList<UpcomingMoviesDataModel> movieDetailsModelArrayList;
    Context mContext;

    public Movie_Adapter(Context mContext, ArrayList<UpcomingMoviesDataModel> movieDetailsModelArrayList) {
        this.movieDetailsModelArrayList = movieDetailsModelArrayList;
        this.mContext = mContext;
    }

    @Override
    public int getItemCount() {
        return movieDetailsModelArrayList.size();
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder movieViewHolder, final int position) {
        final UpcomingMoviesDataModel movieDetailsModel = movieDetailsModelArrayList.get(position);

        try {
            movieViewHolder.txtMovieName.setText(movieDetailsModel.getMovieTitle());
            try {
                movieViewHolder.txtReleaseDate.setText(SIMPLE_DATE.format(DB_DATE.parse(movieDetailsModel.getReleaseDate())));
            } catch (ParseException e) {
                e.printStackTrace();
                movieViewHolder.txtReleaseDate.setText(movieDetailsModel.getReleaseDate());
            }

            Glide.with(mContext).load(BuildConfig.IMAGE_HOST + movieDetailsModel.getPosterPath())
                    .fitCenter()
                    .placeholder(R.drawable.ic_placeholder)
                    .into(movieViewHolder.imgMovie);

            movieViewHolder.txtMovieCertificate.setText(movieDetailsModel.isAdult() ? mContext.getResources().getString(R.string.adult) : mContext.getResources().getString(R.string.under_adult));
        } catch (Exception e) {
            e.printStackTrace();
        }

        movieViewHolder.setIsRecyclable(false);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.movie_details_row_layout, viewGroup, false);
        return new MovieViewHolder(itemView);
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));  //recyclerView.getChildPosition(child)
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.rlMovieCard)
        RelativeLayout rlMovieCard;
        @Bind(R.id.imgMovie)
        ImageView imgMovie;
        @Bind(R.id.txtMovieName)
        TextView txtMovieName;
        @Bind(R.id.txtReleaseDate)
        TextView txtReleaseDate;
        @Bind(R.id.txtMovieCertificate)
        TextView txtMovieCertificate;

        public MovieViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

}


