package com.android.example.github.di;

import com.android.example.github.api.MovieService;
//import com.android.example.github.viewmodel.MovieViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/** Modules are responsible for creating/satisfying Dependencies*/
@Module
public class MovieModule {
    public static final String BASE_URl = "https://api.themoviedb.org/3/";

    @Singleton
    public static MovieService provideMovieService() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieService.class);
    }

//    @Provides @Singleton
//    MovieRepository provideMovieRepository(MovieDao movieDao, MovieService movieService, MovieDb movieDb, AppExecutors appExecutors){
//        return new MovieRepository(movieDao, movieService, movieDb);
//    }

//    @Provides @Singleton
//    MovieDb provideMovieDb(Application application){
//        return Room.databaseBuilder(application, MovieDb.class, "MovieDb.db").build();
//    }

//    @Provides @Singleton
//    MovieDao provideMovieDao(MovieDb movieDb){
//        return movieDb.movieDao();
//    }

//    @Provides @Singleton
//    ViewModelProvider.Factory provideViewModelFactory(MovieRepository repository){
//        return new MovieViewModelFactory(repository);
//    }


}
