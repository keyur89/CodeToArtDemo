package com.codetoart.codetoartmovies.MovieListView.contract;

import com.codetoart.codetoartmovies.ApiManager.Models.UpcomingMoviesDataModel;

import java.util.ArrayList;

/**
 * Created by Keyur Tailor on 09-Oct-17.
 */

public interface MovieListScreen {
    void showError(String message);

    void setupMovieListAdapter(ArrayList<UpcomingMoviesDataModel> upcomingMoviesModel);

    void setupNoDataError();

    void redirectToMovieDetail(UpcomingMoviesDataModel upcomingMoviesDataModel);
}
