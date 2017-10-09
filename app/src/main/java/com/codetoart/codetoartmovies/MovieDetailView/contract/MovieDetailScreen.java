package com.codetoart.codetoartmovies.MovieDetailView.contract;

import com.codetoart.codetoartmovies.ApiManager.Models.MovieDetailsModel;
import com.codetoart.codetoartmovies.ApiManager.Models.MovieImagesModel;

import java.util.ArrayList;

/**
 * Created by Keyur Tailor on 09-Oct-17.
 */

public interface MovieDetailScreen {
    void showError(String message);

    void setupMovieDetails(MovieDetailsModel upcomingMoviesModel);

    void setupNoDataError();

    void setupMovieImages(ArrayList<MovieImagesModel.MovieImagesDataModel> posters);
}
