package com.android.example.github.repository;

import com.android.example.github.AppExecutors;
import com.android.example.github.api.MovieService;
import com.android.example.github.db.MovieDao;
import com.android.example.github.db.MovieDb;
import com.android.example.github.ui.movie.MovieListViewModel;

import javax.inject.Inject;

public class MovieRepository {

    private MovieDao movieDao;
    private MovieService movieService;
    private MovieDb movieDb;
    private AppExecutors appExecutors;
    private MovieRepository movieRepository;

    NetworkBoundResource networkBoundResource;
    MovieListViewModel movieListViewModel;

    public static final String API_KEY = "1629d9f319180fab65a709e65ca9a077";

    @Inject
    public MovieRepository(MovieDao movieDao, MovieService movieService, MovieDb movieDb,
                           AppExecutors appExecutors) {
        this.movieDao = movieDao;
        this.movieService = movieService;
        this.movieDb = movieDb;
        this.appExecutors = appExecutors;
    }

}
