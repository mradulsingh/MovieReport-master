package com.android.example.github.ui.movie;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.android.example.github.BuildConfig;
import com.android.example.github.R;
import com.android.example.github.api.MovieService;
import com.android.example.github.databinding.ActivityEarthquakeListFragmentBinding;
import com.android.example.github.di.Injectable;
import com.android.example.github.di.MovieModule;
import com.android.example.github.ui.search.SearchViewModel;
import com.android.example.github.util.PaginationMoviesList;
import com.android.example.github.vo.MovieInfoo;
import com.android.example.github.vo.MovieResponse;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

//import static android.support.constraint.Constraints.TAG;

public class MovieListFragment extends Fragment implements Injectable {


    public static final String LOG_TAG = "MovieListFragment";
    public static final String API_KEY = "1629d9f319180fab65a709e65ca9a077";

    @Inject
    ViewModelProvider.Factory viewModelProviderFactory;

    @Inject
    MovieService movieService;

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
    private MovieListViewModel movieListViewModel;

    ///////////////////////////////////////////////////////////////
    private static final String MOVIE_DIALOG_TAG = "movie_dialog_tag";

    private ActivityEarthquakeListFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.activity_earthquake_list_fragment, container, false);
        return binding.getRoot();
   }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        movieListViewModel = ViewModelProviders.of(this, viewModelProviderFactory)
                .get(MovieListViewModel.class);
        init();
    }

    /** method for opening FilterFragment */
    private void dialogForMovieQueries() {
        MoviesFilterFragment dialog = MoviesFilterFragment.newInstance(this);
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), MOVIE_DIALOG_TAG);

    }


    private void init() {
        setHasOptionsMenu(true);
        initRecyclerView();
        handleProgressBar();
    }

    private void initRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false);
        binding.recyclerViewList.setLayoutManager(linearLayoutManager);
        binding.recyclerViewList.setItemAnimator(new DefaultItemAnimator());
        adapter = new PaginationAdapter(getContext());
        binding.recyclerViewList.setAdapter(adapter);
        binding.recyclerViewList.addOnScrollListener(
                new PaginationMoviesList(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                movieListViewModel.loadMoreItems();
//                isLoading = true;
//                currentPage += 1;
//                Log.d("page number...", String.valueOf(currentPage));
//                new Handler().postDelayed(() -> loadNextPage(), 1000);
            }

            @Override
            public int getTotalPageCount() {
                return movieListViewModel.getTotalPageCount();
            }

            @Override
            public boolean isLoading() {
                return movieListViewModel.isLoading();
            }

            @Override
            public boolean isLastPage() {
                return movieListViewModel.isLastPage();
            }
        });

    }

    private void handleProgressBar() {
        //init service and load data

        movieListViewModel.loadFirstPage(binding.progressBar, adapter);
    }

//    public void loadFirstPage(ProgressBar progressBar, PaginationAdapter adapter) {
//        Log.d(LOG_TAG, "loadFirstPage");
//        callGetPopularMovies().enqueue(new Callback<MovieResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
//
//                Log.d(LOG_TAG, response.toString());
//
//                // Got data. Send it to adapter
//                List<MovieInfoo> results = fetchResults(response);
//                progressBar.setVisibility(View.GONE);
//                adapter.addAll(results);
//
//                if (currentPage >= TOTAL_PAGES)
//                    adapter.addLoadingFooter();
//                else
//                    isLastPage = true;
//            }
//
//            @Override
//            public void onFailure(Call<MovieResponse> call, Throwable t) {
//                Log.d("loadFirstPage...", "failure");
//                t.printStackTrace();
//            }
//        });
//    }
//    public void loadNextPage() {
//        Log.d(LOG_TAG, "loadNextPage: " + currentPage);
//
//        callGetPopularMovies().enqueue(new Callback<MovieResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
//                adapter.removeLoadingFooter();
//                isLoading = false;
//
//                List<MovieInfoo> results = fetchResults(response);
//                adapter.addAll(results);
//
//                if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
//                else isLastPage = true;
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
//                progressBar.setVisibility(GONE);
//            }
//        });
//    }
//    private List<MovieInfoo> fetchResults(Response<MovieResponse> response) {
//        MovieResponse topRatedMovies = response.body();
//        return Arrays.asList(topRatedMovies != null ? topRatedMovies.getResults() : new MovieInfoo[0]);
//    }
//    private Call<MovieResponse> callGetPopularMovies() {
//        return movieService.getPopularMovies(API_KEY, currentPage);
//    }

    /***********************************/
    /** functions for toolbar menus */
    /** menu methods */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.filter){
            dialogForMovieQueries();
        }
        return super.onOptionsItemSelected(item);
    }

    //////////////////////////////////////////////////////////////
//    /** PaginationMovieList override methods*/
//    public void loadMoreItems(){
//        isLoading = true;
//        currentPage += 1;
//        Log.d("page number...", String.valueOf(currentPage));
//        new Handler().postDelayed(() -> movieListViewModel.loadNextPage(), 1000);
//    }
//    public boolean isLoading(){
//        return isLoading;
//    }
//    public boolean isLastPage(){
//        return isLastPage;
//    }
//    public int getTotalPageCount(){
//        return TOTAL_PAGES;
//    }
    ///////////////////////////////////////////////////////////////
}

