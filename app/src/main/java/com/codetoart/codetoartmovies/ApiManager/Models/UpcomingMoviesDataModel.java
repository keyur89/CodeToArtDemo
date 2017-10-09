package com.codetoart.codetoartmovies.ApiManager.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Keyur Tailor on 09-Oct-17.
 */

public class UpcomingMoviesDataModel {

    @SerializedName("vote_count")
    int voteCount;
    @SerializedName("id")
    int movieId;
    @SerializedName("video")
    boolean isVideo;
    @SerializedName("title")
    String movieTitle;
    @SerializedName("original_title")
    String originalTitle;
    @SerializedName("vote_average")
    float voteAverage;
    float popularity;
    @SerializedName("poster_path")
    String posterPath;
    @SerializedName("backdrop_path")
    String backdropPath;
    @SerializedName("original_language")
    String originalLanguage;
    @SerializedName("adult")
    boolean isAdult;
    String overview;
    @SerializedName("release_date")
    String releaseDate;
    @SerializedName("genre_ids")
    ArrayList<Integer> genreIds;

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public ArrayList<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(ArrayList<Integer> genreIds) {
        this.genreIds = genreIds;
    }
}
