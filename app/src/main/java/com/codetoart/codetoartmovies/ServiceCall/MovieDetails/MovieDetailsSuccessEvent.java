package com.codetoart.codetoartmovies.ServiceCall.MovieDetails;

import com.codetoart.codetoartmovies.ApiManager.Models.MovieDetailsModel;

/**
 * Created by Keyur Tailor on 09-Oct-17.
 */

public class MovieDetailsSuccessEvent {
    boolean successful;
    MovieDetailsModel movieDetailsModel;

    public MovieDetailsSuccessEvent(boolean successful, MovieDetailsModel upcomingMoviesModel) {
        this.successful = successful;
        this.movieDetailsModel = upcomingMoviesModel;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public MovieDetailsModel getMovieDetailsModel() {
        return movieDetailsModel;
    }
}
