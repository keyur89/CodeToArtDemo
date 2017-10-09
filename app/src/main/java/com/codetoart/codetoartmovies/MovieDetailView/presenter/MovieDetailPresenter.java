package com.codetoart.codetoartmovies.MovieDetailView.presenter;

import com.codetoart.codetoartmovies.ApiManager.ApiManager;
import com.codetoart.codetoartmovies.ApiManager.Models.MovieDetailsModel;
import com.codetoart.codetoartmovies.ApiManager.Models.MovieImagesModel;
import com.codetoart.codetoartmovies.MovieDetailView.contract.MovieDetailScreen;
import com.codetoart.codetoartmovies.ServiceCall.MovieDetails.CallMovieDetails;
import com.codetoart.codetoartmovies.ServiceCall.MovieImages.CallMovieImages;

import javax.inject.Inject;

/**
 * Created by Keyur Tailor on 09-Oct-17.
 */

public class MovieDetailPresenter {
    @Inject
    public MovieDetailPresenter() {

    }

    public void callGetMovieDetails(int movieId, ApiManager apiManager) {
        CallMovieDetails callMovieDetails = new CallMovieDetails();
        callMovieDetails.callMovieDetails(apiManager, movieId);
        callGetMovieImages(movieId, apiManager);
    }

    public void callGetMovieImages(int movieId, ApiManager apiManager) {
        CallMovieImages callMovieImages = new CallMovieImages();
        callMovieImages.callMovieImages(apiManager, movieId);
    }

    public void setupMovieDetailData(MovieDetailScreen screen, MovieDetailsModel movieDetailsModel) {
        if (movieDetailsModel != null) {
            screen.setupMovieDetails(movieDetailsModel);
        } else {
            screen.setupNoDataError();
        }
    }

    public void setupMovieImagesData(MovieDetailScreen screen, MovieImagesModel movieImagesModel) {
        if (movieImagesModel.getPosters().size() > 0) {
            screen.setupMovieImages(movieImagesModel.getPosters());
        }
    }
}
