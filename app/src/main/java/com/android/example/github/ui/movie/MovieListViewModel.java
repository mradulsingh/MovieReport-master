package com.android.example.github.ui.movie;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.example.github.api.MovieService;
import com.android.example.github.repository.MovieRepository;
import com.android.example.github.util.AbsentLiveData;
import com.android.example.github.vo.MovieInfoo;
import com.android.example.github.vo.MovieResponse;
import com.android.example.github.vo.Repo;
import com.android.example.github.vo.Resource;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static com.android.example.github.ui.movie.MovieListFragment.API_KEY;

public class MovieListViewModel extends ViewModel {

    private LiveData<Resource<List<MovieInfoo>>> movies;

    //pagination/////////////////////////
    public static final String LOG_TAG = "MovieListFragment";
    PaginationAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private final static int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    public int TOTAL_PAGES = 0;
    public int currentPage = PAGE_START;
    private MovieService movieService;
    ////////////////////////////////////
    public MovieRepository movieRepository;

    private LiveData<Resource<List<MovieInfoo>>> results;
    private final MutableLiveData<String> query = new MutableLiveData<>();


    /**
     * getting reference of the Movie repository
     */
    @Inject
    public MovieListViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
//        results = Transformations.switchMap(query, search -> {
//            if (search == null || search.trim().length() == 0) {
//                return AbsentLiveData.create();
//            } else {
//                return movieRepository.search(search);
//            }
//        });
    }

    ////////////////////////////////////////////////////////////////////

    public void loadFirstPage(ProgressBar progressBar, PaginationAdapter adapter) {
        Log.d(LOG_TAG, "loadFirstPage");
        callGetPopularMovies().enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {

                Log.d(LOG_TAG, response.toString());

                // Got data. Send it to adapter
                List<MovieInfoo> results = fetchResults(response);
                progressBar.setVisibility(View.GONE);
                adapter.addAll(results);

                if (currentPage >= TOTAL_PAGES)
                    adapter.addLoadingFooter();
                else
                    isLastPage = true;
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("loadFirstPage...", "failure");
                t.printStackTrace();
            }
        });
    }

    public void loadNextPage() {
        Log.d(LOG_TAG, "loadNextPage: " + currentPage);

        callGetPopularMovies().enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                adapter.removeLoadingFooter();
                isLoading = false;

                List<MovieInfoo> results = fetchResults(response);
                adapter.addAll(results);

                if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                progressBar.setVisibility(GONE);
            }
        });
    }

    /**
     * fetching movies with the given query
     */
    private List<MovieInfoo> fetchResults(Response<MovieResponse> response) {
        MovieResponse topRatedMovies = response.body();
        return Arrays.asList(topRatedMovies != null ? topRatedMovies.getResults() : new MovieInfoo[0]);
    }

    private Call<MovieResponse> callGetPopularMovies() {
        return movieService.getPopularMovies(API_KEY, currentPage);
    }

    //////////////////////////////////////////////////////////////
    /** PaginationMovieList override methods*/
    public void loadMoreItems(){
        isLoading = true;
        currentPage += 1;
        Log.d("page number...", String.valueOf(currentPage));
        new Handler().postDelayed(() -> loadNextPage(), 1000);
    }
    public boolean isLoading(){
        return isLoading;
    }
    public boolean isLastPage(){
        return isLastPage;
    }
    public int getTotalPageCount(){
        return TOTAL_PAGES;
    }
    ///////////////////////////////////////////////////////////////


}
