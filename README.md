# CodeToArtDemo
Movie list Databse Demo
In this demo Assigment

Assignment: Upcoming Movies App
It is app which list all upcoming movies using The Movie Db API (TMdb). Action bar have info action item, which on clicking navigates to Information screen. It comprises of total 3 screens:
1) Movie List Screen
List down all upcoming movies with following details on the list:
Poster Image
Name
Release Date
Adult
REST API details
Movie List API
https://api.themoviedb.org/3/movie/upcoming
Params:
api_key – b7cd3340a794e5a2f35e3abb820b497f

movie_main

2) Movie Detail Screen
When user selects any movie from previous screen, it navigates to Movie detail screen:
Movie images with dot-indicator (at least 1 image and maximum 5 image)
Title
Overview – display only 5 lines with ellipsize end
Popularity in Stars rating
REST API details
Movie Details API
https://api.themoviedb.org/3/movie/<movie-id>
Params:
api_key – b7cd3340a794e5a2f35e3abb820b497f
Get Images API
https://api.themoviedb.org/3/movie/<movie-id>/images

movie_details

3) Information Screen
It simply displays your name :).

