package com.codetoart.codetoartmovies.ServiceCall.MovieImages;

import com.codetoart.codetoartmovies.ApiManager.Models.MovieImagesModel;

/**
 * Created by Keyur Tailor on 09-Oct-17.
 */

public class MovieImagesSuccessEvent {
    boolean successful;
    MovieImagesModel movieImagesModel;

    public MovieImagesSuccessEvent(boolean successful, MovieImagesModel movieImagesModel) {
        this.successful = successful;
        this.movieImagesModel = movieImagesModel;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public MovieImagesModel getMovieImagesModel() {
        return movieImagesModel;
    }
}
