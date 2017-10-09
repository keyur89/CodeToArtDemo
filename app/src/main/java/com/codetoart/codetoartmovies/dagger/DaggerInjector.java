package com.codetoart.codetoartmovies.dagger;


import com.codetoart.codetoartmovies.dagger.components.AppComponent;
import com.codetoart.codetoartmovies.dagger.components.DaggerAppComponent;
import com.codetoart.codetoartmovies.dagger.modules.AppModule;

/**
 * Created by Keyur Tailor on 09-Oct-17.
 */


public class DaggerInjector {
    private static AppComponent appComponent =
            DaggerAppComponent.builder().appModule(new AppModule()).build();


    public static AppComponent get() {
        return appComponent;
    }
}