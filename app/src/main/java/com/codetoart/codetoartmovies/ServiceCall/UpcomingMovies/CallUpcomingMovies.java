package com.codetoart.codetoartmovies.ServiceCall.UpcomingMovies;

import com.codetoart.codetoartmovies.ApiManager.ApiManager;
import com.codetoart.codetoartmovies.ApiManager.Models.UpcomingMoviesModel;
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

public class CallUpcomingMovies {
    EventBus bus;

    public void callUpcomingMovies(final ApiManager apiManager) {
        bus = Injector.provideEventBus();

        Call<UpcomingMoviesModel> upcomingMoviesCall = apiManager.getService().getUpcomingMovies(ApiManager.API_KEY);
        upcomingMoviesCall.enqueue(new Callback<UpcomingMoviesModel>() {
            @Override
            public void onResponse(Call<UpcomingMoviesModel> call, Response<UpcomingMoviesModel> response) {
                if (response.isSuccessful()) {
                    UpcomingMoviesModel upcomingMoviesModel = response.body();
                    bus.post(new UpcomingMoviesSuccessEvent(response.isSuccessful(), upcomingMoviesModel));
                } else {
                    Converter<ResponseBody, Error> errorConverter =
                            apiManager.retrofit.responseBodyConverter(Error.class, new Annotation[0]);
                    // Convert the error body into our Error type.
                    try {
                        Error error = errorConverter.convert(response.errorBody());
                        String errorMessageDisplay = error.status_message;
                        bus.post(new UpcomingMoviesFailureEvent(false, errorMessageDisplay));

                    } catch (Exception e) {
                        e.printStackTrace();
                        bus.post(new UpcomingMoviesFailureEvent(false, null));
                    }
                }
            }

            @Override
            public void onFailure(Call<UpcomingMoviesModel> call, Throwable t) {
                bus.post(new UpcomingMoviesFailureEvent(false, null));
            }
        });

    }

    static class Error {
        String status_message;
        int status_code;
        boolean success;
    }
}
