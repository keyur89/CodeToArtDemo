package com.codetoart.codetoartmovies.ServiceCall.UpcomingMovies;

import com.codetoart.codetoartmovies.ApiManager.Models.UpcomingMoviesModel;

/**
 * Created by Keyur Tailor on 09-Oct-17.
 */

public class UpcomingMoviesSuccessEvent {
    boolean successful;
    UpcomingMoviesModel upcomingMoviesModel;

    public UpcomingMoviesSuccessEvent(boolean successful, UpcomingMoviesModel upcomingMoviesModel) {
        this.successful = successful;
        this.upcomingMoviesModel = upcomingMoviesModel;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public UpcomingMoviesModel getUpcomingMoviesModel() {
        return upcomingMoviesModel;
    }
}
