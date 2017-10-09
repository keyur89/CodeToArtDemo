package com.codetoart.codetoartmovies.dagger.components;


import com.codetoart.codetoartmovies.MovieDetailView.view.activity.MovieDetailActivity;
import com.codetoart.codetoartmovies.MovieListView.view.activity.MovieListActivity;
import com.codetoart.codetoartmovies.dagger.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Keyur Tailor on 09-Oct-17.
 */


@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {


    void inject(MovieListActivity movieListActivity);

    void inject(MovieDetailActivity movieDetailActivity);
}
