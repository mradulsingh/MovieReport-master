package com.android.example.github.ui.movie;

import android.databinding.DataBindingUtil;
import android.graphics.Movie;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.example.github.BuildConfig;
import com.android.example.github.R;
import com.android.example.github.api.MovieService;
//import com.android.example.github.databinding.ActivityEarthquakeListFragmentBinding;
import com.android.example.github.di.MovieModule;
import com.android.example.github.util.PaginationMoviesList;
import com.android.example.github.vo.MovieInfoo;
import com.android.example.github.vo.MovieResponse;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class MovieListFragment extends Fragment {

    public static final String LOG_TAG = "MovieListFragment";
    public static final String API_KEY = "1629d9f319180fab65a709e65ca9a077";

    /**
     * For pagination
     */
    PaginationAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView rv;
    ProgressBar progressBar;
    private final static int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 0;
    private int currentPage = PAGE_START;
    private MovieService movieService;

    ///////////////////////////////////////////////////////////////
    private static final String MOVIE_DIALOG_TAG = "movie_dialog_tag";

//    ActivityEarthquakeListFragmentBinding binding;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
//                R.layout.activity_earthquake_list_fragment, container, false);
        rootView = inflater.inflate(R.layout.activity_earthquake_list_fragment, container,false);
        adapter = new PaginationAdapter(getContext());
        dialogForMovieQueries();
        init();
        code();

        return rootView;
    }

    private void dialogForMovieQueries() {
        MoviesFilterFragment dialog = MoviesFilterFragment.newInstance(this);
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), MOVIE_DIALOG_TAG);

    }


    private void init() {
        rv = rootView.findViewById(R.id.recyclerViewList);
        progressBar = rootView.findViewById(R.id.progressBar);
//        rv = binding.recyclerViewList;
//        progressBar = binding.progressBar;


        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    }

    private void code() {
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);

        rv.addOnScrollListener(new PaginationMoviesList(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                Log.d("page number...", String.valueOf(currentPage));
                new Handler().postDelayed(() -> loadNextPage(), 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }
        });

        //init service and load data
        movieService = MovieModule.provideMovieService();

        loadFirstPage();
    }

    private void loadFirstPage() {
        Log.d(LOG_TAG, "loadFirstPage");
        callTopRatedMoviesApi().enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {

                Log.d(LOG_TAG, response.toString());
                // Got data. Send it to adapter

                List<MovieInfoo> results = fetchResults(response);
                Log.d("list..." , results.toString());
                progressBar.setVisibility(View.GONE);
                adapter.addAll(results);

                if (currentPage >= TOTAL_PAGES)
                    adapter.addLoadingFooter();
                else
                    isLastPage = true;
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }

    private void loadNextPage() {
        Log.d(LOG_TAG, "loadNextPage: " + currentPage);

        callTopRatedMoviesApi().enqueue(new Callback<MovieResponse>() {
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

            }
        });
    }

    /** fetching movies with the given query */
    private List<MovieInfoo> fetchResults(Response<MovieResponse> response){
        MovieResponse topRatedMovies = response.body();
        return Arrays.asList(topRatedMovies != null ? topRatedMovies.getResults() : new MovieInfoo[0]);
    }

    private Call<MovieResponse> callTopRatedMoviesApi() {
        return movieService.getNowPlayingMovies(API_KEY, currentPage);
    }

    /** menu methods */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }
}

