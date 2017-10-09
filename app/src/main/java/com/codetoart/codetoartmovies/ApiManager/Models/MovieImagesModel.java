package com.codetoart.codetoartmovies.ApiManager.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Keyur Tailor on 09-Oct-17.
 */

public class MovieImagesModel {
    ArrayList<MovieImagesDataModel> posters;

    public ArrayList<MovieImagesDataModel> getPosters() {
        return posters;
    }

    public void setPosters(ArrayList<MovieImagesDataModel> posters) {
        this.posters = posters;
    }

    public class MovieImagesDataModel {
        @SerializedName("file_path")
        String imagePath;

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }
    }
}


