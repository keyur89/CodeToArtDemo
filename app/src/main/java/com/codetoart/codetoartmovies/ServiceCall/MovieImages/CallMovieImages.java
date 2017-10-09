package com.codetoart.codetoartmovies.ServiceCall.MovieImages;

import com.codetoart.codetoartmovies.ApiManager.ApiManager;
import com.codetoart.codetoartmovies.ApiManager.Models.MovieImagesModel;
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

public class CallMovieImages {
    EventBus bus;

    public void callMovieImages(final ApiManager apiManager, int movieId) {
        bus = Injector.provideEventBus();

        Call<MovieImagesModel> imagesModelCall = apiManager.getService().getMovieImages(movieId,ApiManager.API_KEY);
        imagesModelCall.enqueue(new Callback<MovieImagesModel>() {
            @Override
            public void onResponse(Call<MovieImagesModel> call, Response<MovieImagesModel> response) {
                if (response.isSuccessful()) {
                    MovieImagesModel movieImagesModel = response.body();
                    bus.post(new MovieImagesSuccessEvent(response.isSuccessful(), movieImagesModel));
                } else {
                    Converter<ResponseBody, Error> errorConverter =
                            apiManager.retrofit.responseBodyConverter(Error.class, new Annotation[0]);
                    // Convert the error body into our Error type.
                    try {
                        Error error = errorConverter.convert(response.errorBody());
                        String errorMessageDisplay = error.status_message;
                        bus.post(new MovieImagesFailureEvent(false, errorMessageDisplay));

                    } catch (Exception e) {
                        e.printStackTrace();
                        bus.post(new MovieImagesFailureEvent(false, null));
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieImagesModel> call, Throwable t) {
                bus.post(new MovieImagesFailureEvent(false, null));
            }
        });

    }

    static class Error {
        String status_message;
        int status_code;
        boolean success;
    }
}
