package com.codetoart.codetoartmovies.MovieListView.presenter;

import com.codetoart.codetoartmovies.ApiManager.ApiManager;
import com.codetoart.codetoartmovies.ApiManager.Models.UpcomingMoviesDataModel;
import com.codetoart.codetoartmovies.ApiManager.Models.UpcomingMoviesModel;
import com.codetoart.codetoartmovies.MovieListView.contract.MovieListScreen;
import com.codetoart.codetoartmovies.ServiceCall.UpcomingMovies.CallUpcomingMovies;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by Keyur Tailor on 09-Oct-17.
 */

public class MovieListPresenter {
    ArrayList<UpcomingMoviesDataModel> upcomingMoviesDataList;

    @Inject
    public MovieListPresenter() {

    }

    public void callGetUpcomingMovies(ApiManager apiManager) {
        CallUpcomingMovies callUpcomingMovies = new CallUpcomingMovies();
        callUpcomingMovies.callUpcomingMovies(apiManager);
    }

    public void setupMovieListData(MovieListScreen screen, UpcomingMoviesModel upcomingMoviesModel) {
        upcomingMoviesDataList = upcomingMoviesModel.getResults();
        if (upcomingMoviesModel.getResults().size() > 0) {
            screen.setupMovieListAdapter(upcomingMoviesModel.getResults());
        } else {
            screen.setupNoDataError();
        }
    }

    public void redirectToMovieDetail(MovieListScreen screen, int position) {
        if (upcomingMoviesDataList.size() > 0) {
            screen.redirectToMovieDetail(upcomingMoviesDataList.get(position));
        }
    }

}
