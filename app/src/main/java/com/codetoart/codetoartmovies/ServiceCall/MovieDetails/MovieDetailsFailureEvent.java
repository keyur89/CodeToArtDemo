package com.codetoart.codetoartmovies.ServiceCall.MovieDetails;

/**
 * Created by Keyur Tailor on 09-Oct-17.
 */

public class MovieDetailsFailureEvent {
    boolean successful;
    String errorMessageDisplay;

    public MovieDetailsFailureEvent(boolean successful, String errorMessageDisplay) {
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
