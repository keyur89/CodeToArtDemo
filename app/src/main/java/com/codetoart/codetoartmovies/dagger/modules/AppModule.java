package com.codetoart.codetoartmovies.dagger.modules;


import com.codetoart.codetoartmovies.ApiManager.ApiManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


/**
 * Created by Keyur Tailor on 09-Oct-17.
 */


@Module
public class AppModule {

    @Provides
    @Singleton
    ApiManager provideApiManager() {
        return new ApiManager();
    }

}
