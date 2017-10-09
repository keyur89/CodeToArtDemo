package com.codetoart.codetoartmovies.ServiceCall.UpcomingMovies;

/**
 * Created by Keyur Tailor on 09-Oct-17.
 */

public class UpcomingMoviesFailureEvent {
    boolean successful;
    String errorMessageDisplay;

    public UpcomingMoviesFailureEvent(boolean successful, String errorMessageDisplay) {
        this.successful = successful;
        this.errorMessageDisplay = errorMessageDisplay;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getErrorMessageDisplay() {
        return errorMessageDisplay;
    }
}
