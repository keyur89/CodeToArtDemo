package com.codetoart.codetoartmovies.ServiceCall.MovieDetails;

import com.codetoart.codetoartmovies.ApiManager.ApiManager;
import com.codetoart.codetoartmovies.ApiManager.Models.MovieDetailsModel;
import com.codetoart.codetoartmovies.Utils.Injector;

import org.greenrobot.eventbus.EventBus;

import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by Keyur Tailor on 09-Oct-17.
 */

public class CallMovieDetails {
    EventBus bus;

    public void callMovieDetails(final ApiManager apiManager, int movieId) {
        bus = Injector.provideEventBus();

        Call<MovieDetailsModel> movieDetailsCall = apiManager.getService().getMovieDetails(movieId,ApiManager.API_KEY);
        movieDetailsCall.enqueue(new Callback<MovieDetailsModel>() {
            @Override
            public void onResponse(Call<MovieDetailsModel> call, Response<MovieDetailsModel> response) {
                if (response.isSuccessful()) {
                    MovieDetailsModel movieDetailsModel = response.body();
                    bus.post(new MovieDetailsSuccessEvent(response.isSuccessful(), movieDetailsModel));
                } else {
                    Converter<ResponseBody, Error> errorConverter =
                            apiManager.retrofit.responseBodyConverter(Error.class, new Annotation[0]);
                    // Convert the error body into our Error type.
                    try {
                        Error error = errorConverter.convert(response.errorBody());
                        String errorMessageDisplay = error.status_message;
                        bus.post(new MovieDetailsFailureEvent(false, errorMessageDisplay));

                    } catch (Exception e) {
                        e.printStackTrace();
                        bus.post(new MovieDetailsFailureEvent(false, null));
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieDetailsModel> call, Throwable t) {
                bus.post(new MovieDetailsFailureEvent(false, null));
            }
        });

    }

    static class Error {
        String status_message;
        int status_code;
        boolean success;
    }
}
