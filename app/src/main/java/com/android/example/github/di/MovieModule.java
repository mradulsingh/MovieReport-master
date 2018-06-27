package com.android.example.github.di;

//import com.android.example.github.viewmodel.MovieViewModelFactory;

import dagger.Module;


/** Modules are responsible for creating/satisfying Dependencies*/
@Module(includes = ViewModelModule.class)
public class MovieModule {


//    @Provides @Singleton
//    MovieRepository provideMovieRepository(MovieDao movieDao, MovieService movieService, MovieDb movieDb, AppExecutors appExecutors){
//        return new MovieRepository(movieDao, movieService, movieDb, appExecutors);
//    }




}
