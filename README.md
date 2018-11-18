# MooV

App that lets users browse for most popular movies and favourite the ones they like.
This project consumes the api from http://www.themoviedb.org.

## What was used
- Kotlin for programming language
- Clean architecture for code organization
- Retrofit for remote calls
- Room for local calls
- Coroutines for asynchronous work
- Koin for dependency injection
- Android Architecture Component's ViewModel for configuration changes

## Features
- User can see top 50 most popular movies from TMDB
- User can click on a movie to see its details on another screen
- Movies are lazy loaded (20 each page)
- User can keep using the app even while offline for 1 day (cache duration)
- User can favorite any movie he wishes
- Smooth transition with animation when user clicks on a movie

## To be done
- Proper offline
-- Need to cache every call on a local database instead of using http cache
- Share feature
-- Not implemented at all
- Filtering & sorting feature
-- Not implemented at all
- Better testing
-- Didn't find how to test ViewModel's channels properly (also didn't find much help in the
community). Need to remove those delay calls on ViewModel's test.
-- Achieve 90%+ coverity
-- Test ui, for that need to figure out how to mock the ViewModels
- Better logic for selecting image quality from image configurations
- Better error handling