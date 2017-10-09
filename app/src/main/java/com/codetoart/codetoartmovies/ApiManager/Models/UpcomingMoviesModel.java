package com.codetoart.codetoartmovies.ApiManager.Models;

import java.util.ArrayList;

/**
 * Created by Keyur Tailor on 09-Oct-17.
 */

public class UpcomingMoviesModel {

    ArrayList<UpcomingMoviesDataModel> results;

    public ArrayList<UpcomingMoviesDataModel> getResults() {
        return results;
    }

    public void setResults(ArrayList<UpcomingMoviesDataModel> results) {
        this.results = results;
    }
}
