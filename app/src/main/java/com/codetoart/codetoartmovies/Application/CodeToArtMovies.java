package com.codetoart.codetoartmovies.Application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Keyur Tailor on 09-Oct-17.
 */

public class CodeToArtMovies extends Application {
    Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
    }
}
