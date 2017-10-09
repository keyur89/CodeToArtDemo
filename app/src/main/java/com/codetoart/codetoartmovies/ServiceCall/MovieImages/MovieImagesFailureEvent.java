package com.codetoart.codetoartmovies.ServiceCall.MovieImages;

/**
 * Created by Keyur Tailor on 09-Oct-17.
 */

public class MovieImagesFailureEvent {
    boolean successful;
    String errorMessageDisplay;

    public MovieImagesFailureEvent(boolean successful, String errorMessageDisplay) {
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
