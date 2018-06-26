package com.android.example.github.api;

import android.arch.lifecycle.LiveData;

import com.android.example.github.vo.MovieInfoo;
import com.android.example.github.vo.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * REST API access points
 */
public interface MovieService {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int pageIndex);
//    LiveData<ApiResponse<List<MovieResponse>>> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int pageIndex);

    @GET("movie/top_rated")
    Call<MovieResponse> getNowPlayingMovies(@Query("api_key") String apiKey, @Query("page") int pageIndex);

}
