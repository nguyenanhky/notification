package kynv1.fsoft.appmoviefinally.ui.movie;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import kynv1.fsoft.appmoviefinally.R;
import kynv1.fsoft.appmoviefinally.database.MovieDatabase;
import kynv1.fsoft.appmoviefinally.databinding.FragmentMovieBinding;
import kynv1.fsoft.appmoviefinally.model.Result;
import kynv1.fsoft.appmoviefinally.ui.favorite.IClickDeleteItem;
import kynv1.fsoft.appmoviefinally.viewmodel.ViewModelNumberOfFavorite;
import kynv1.fsoft.appmoviefinally.viewmodel.MovieViewModel;
import kynv1.fsoft.appmoviefinally.utils.Constance;

public class MovieFragment extends Fragment implements IClickDeleteItem {
    private FragmentMovieBinding binding;
    private MoviesAdapter popularAdapter;
    private List<Result> results;
    private List<Result> resultsFilter;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private int mCurrentTypeDisplay = Result.TYPE_LIST;
    private Menu menu;
    //private PopularViewModel popularViewModel;
    private MovieViewModel movieViewModel;
    private static final String TAG = "MovieFragment";
    private ViewModelNumberOfFavorite viewModelNumberOfFavorite;
    private int page = 1;
    private boolean checkViewType = true;
    private boolean checkLoad = false;
    private SharedPreferences shFilter, shTypeMovie;
    public MovieFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        results = new ArrayList<>();
        resultsFilter = new ArrayList<>();
       // popularViewModel = new ViewModelProvider(this).get(PopularViewModel.class);
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        viewModelNumberOfFavorite = new ViewModelProvider(requireActivity()).get(ViewModelNumberOfFavorite.class);
        popularAdapter = new MoviesAdapter(this,requireContext());
        shFilter = getActivity().getSharedPreferences(Constance.KEY_FILTER, Context.MODE_PRIVATE);
        shTypeMovie = getActivity().getSharedPreferences(Constance.KEY_TYPE_MOVIE, Context.MODE_PRIVATE);
        Log.d(TAG, "onCreate: ");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMovieBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControls();
    }

    private void loadMoreNowPlayingMovies() {
        binding.idNestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    Log.d(TAG, "onScrollChange: da den vi tri cuoi cung ");
                    page++;
                    checkLoad = false;
                    binding.idPBLoading.setVisibility(View.VISIBLE);
                    resultsFilter.clear();
                    showNowPlayingMovies(page);
                }
            }
        });
    }

    private void loadMoreUpcomingMovies() {
        binding.idNestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    Log.d(TAG, "onScrollChange: da den vi tri cuoi cung ");
                    page++;
                    checkLoad = false;
                    binding.idPBLoading.setVisibility(View.VISIBLE);
                    resultsFilter.clear();
                    showUpcomingMovies(page);
                }
            }
        });
    }

    private void loadMoreTopRateMovies() {
        binding.idNestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    Log.d(TAG, "onScrollChange: da den vi tri cuoi cung ");
                    page++;
                    checkLoad = false;
                    binding.idPBLoading.setVisibility(View.VISIBLE);
                    resultsFilter.clear();
                    showTopRateMovies(page);
                }
            }
        });
    }

    private void showNowPlayingMovies(int page) {
        movieViewModel.getNowPlayingMovies(Constance.KEY_VALUE_MOVIE, page).observe(requireActivity(), popularItem -> {
            if (popularItem != null && checkLoad == false) {
                Log.i(TAG, "showTopRateMovies: ");
                results.addAll(popularItem.getResults());
            }
            if (checkViewType) {
                setTypeDisplayRCV(Result.TYPE_LIST);
            } else {
                setTypeDisplayRCV(Result.TYPE_GRID);
            }
            filterMovies(results);
            sortMovies(resultsFilter);
            popularAdapter.setData(resultsFilter);
        });
    }

    private void showTopRateMovies(int page) {
        movieViewModel.getTopRateMovies(Constance.KEY_VALUE_MOVIE, page).observe(requireActivity(), popularItem -> {
            if (popularItem != null && checkLoad == false) {
                Log.i(TAG, "showTopRateMovies: ");
                results.addAll(popularItem.getResults());
            }
            if (checkViewType) {
                setTypeDisplayRCV(Result.TYPE_LIST);
            } else {
                setTypeDisplayRCV(Result.TYPE_GRID);
            }
            filterMovies(results);
            sortMovies(resultsFilter);
            popularAdapter.setData(resultsFilter);
        });
    }

    private void showUpcomingMovies(int page) {
        movieViewModel.getUpcomingMovies(Constance.KEY_VALUE_MOVIE, page).observe(requireActivity(), popularItem -> {
            if (popularItem != null && checkLoad == false) {
                Log.i(TAG, "showUpcomingMovies:  thanh cong");
                results.addAll(popularItem.getResults());
            }
            if (checkViewType) {
                setTypeDisplayRCV(Result.TYPE_LIST);
            } else {
                setTypeDisplayRCV(Result.TYPE_GRID);
            }
            filterMovies(results);
            sortMovies(resultsFilter);
            popularAdapter.setData(resultsFilter);
        });
    }

    private void showPopularMovies(int page) {
        movieViewModel.getPopularMovies(Constance.KEY_VALUE_MOVIE, page).observe(requireActivity(), popularItem -> {
            if (popularItem != null && checkLoad == false) {
                results.addAll(popularItem.getResults());
            }
            if (checkViewType) {
                setTypeDisplayRCV(Result.TYPE_LIST);
            } else {
                setTypeDisplayRCV(Result.TYPE_GRID);
            }
            filterMovies(results);
            sortMovies(resultsFilter);
            popularAdapter.setData(resultsFilter);
        });
    }

    private void sortMovies(List<Result>resultsFilter) {
        if (shFilter.getString(Constance.KEY_FILTER_SORT_BY, "").equals(Constance.SORT_BY_DATE)) {
            Log.i(TAG, "sortMovies: date");
            Collections.sort(resultsFilter, new Comparator<Result>() {
                @Override
                public int compare(Result result, Result t1) {
                    return result.getRelease_date().compareTo(t1.getRelease_date());
                }
            });
            Log.d(TAG, "Sort ::::");
        } else if(shFilter.getString(Constance.KEY_FILTER_SORT_BY,"").equals(Constance.SORT_BY_RATE)) {
            Log.i(TAG, "sortMovies: time");
            Collections.sort(resultsFilter, new Comparator<Result>() {
                @Override
                public int compare(Result result, Result t1) {
                    return Double.compare(result.getVote_average(), t1.getVote_average());
                }
            });
        }
    }

    private void filterMovies(List<Result>results) {
        for (int i = 0; i < results.size(); i++) {
            if (checkRateAndRelease(results.get(i))) {
                resultsFilter.add(results.get(i));
            }
        }
    }


    public boolean checkRateAndRelease(Result result) {
        String str = result.getRelease_date();
        if (!str.equals("") && str.length() >= 4) {
            str = str.substring(0, 4);
            int year = Integer.parseInt(str);
            int start = shFilter.getInt(Constance.KEY_FILTER_MOVIE_WITH_RATE_FROM, 0);
            if ((result.getVote_average() >= start) && (shFilter.getInt(Constance.KEY_FILTER_FROM_RELEASE, 0) <= year)) {
                return true;
            }
        }
        return false;
    }


    private void loadMorePopularMovies() {
        binding.idNestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    Log.d(TAG, "onScrollChange: da den vi tri cuoi cung ");
                    page++;
                    Log.d(TAG, "page: " + page);
                    checkLoad = false;
                    binding.idPBLoading.setVisibility(View.VISIBLE);
                    resultsFilter.clear();
                    showPopularMovies(page);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        checkLoad = true;
        resultsFilter.clear();

    }

    private void addControls() {
        linearLayoutManager = new LinearLayoutManager(getContext());
        gridLayoutManager = new GridLayoutManager(getContext(), 2);

        if(checkViewType==true){
            binding.rcvMovie.setLayoutManager(linearLayoutManager);
        }
        else{
            binding.rcvMovie.setLayoutManager(gridLayoutManager);
        }
        popularAdapter.setData(results);
        binding.rcvMovie.setAdapter(popularAdapter);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu:
                onClickChangeTypeDisplay();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickChangeTypeDisplay() {
        Log.d(TAG, "onClickChangeTypeDisplay: ");
        if (mCurrentTypeDisplay == Result.TYPE_LIST) {
            setTypeDisplayRCV(Result.TYPE_GRID);
            binding.rcvMovie.setLayoutManager(gridLayoutManager);
        } else if (mCurrentTypeDisplay == Result.TYPE_GRID) {
            setTypeDisplayRCV(Result.TYPE_LIST);
            binding.rcvMovie.setLayoutManager(linearLayoutManager);
        }
        popularAdapter.notifyDataSetChanged();
        setIconMenu();
    }

    private void setIconMenu() {
        switch (mCurrentTypeDisplay) {
            case Result.TYPE_LIST:
                menu.getItem(0).setIcon(R.drawable.ic_list);
                checkViewType = true;
                break;
            case Result.TYPE_GRID:
                menu.getItem(0).setIcon(R.drawable.ic_grid);
                checkViewType = false;
                break;
        }
    }

    private void setTypeDisplayRCV(int typeDisplay) {
        if (results == null || results.isEmpty()) {
            return;
        }
        mCurrentTypeDisplay = typeDisplay;
        for (Result result : results) {
            result.setTypeDisplay(typeDisplay);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void clickDeleteFavorite(Result result) {
        MovieDatabase.getInstance(getContext()).movieDao().delete(result);
        viewModelNumberOfFavorite.setupNumberOfFavorite(MovieDatabase.getInstance(getContext()).movieDao().getCount());
        Log.d(TAG, "clickDeleteFavorite: " + MovieDatabase.getInstance(getContext()).movieDao().getCount());
    }

    @Override
    public void clickInsertFavorite(Result result) {
        MovieDatabase.getInstance(getContext()).movieDao().insert(result);
        viewModelNumberOfFavorite.setupNumberOfFavorite(MovieDatabase.getInstance(getContext()).movieDao().getCount());
        Log.d(TAG, "clickInsertFavorite: " + MovieDatabase.getInstance(getContext()).movieDao().getCount());
    }

    @Override
    public void onResume() {
        super.onResume();
        setTypeMovies();

    }

    private void setTypeMovies() {
        switch (shTypeMovie.getInt(Constance.KEY_TITLE_MOVIE, 0)) {
            case Constance.VALUE_TITLE_POPULAR_MOVIE:
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Constance.TITLE_POPULAR_MOVIE);
                Navigation.findNavController(binding.getRoot()).getCurrentDestination().setLabel(Constance.TITLE_POPULAR_MOVIE);
                showPopularMovies(page);
                loadMorePopularMovies();
                break;
            case Constance.VALUE_TITLE_TOP_RATE_MOVIE:
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Constance.TITLE_TOP_RATE_MOVIE);
                Navigation.findNavController(binding.getRoot()).getCurrentDestination().setLabel(Constance.TITLE_TOP_RATE_MOVIE);
                showTopRateMovies(page);
                loadMoreTopRateMovies();
                break;
            case Constance.VALUE_TITLE_UPCOMING_MOVIE:
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Constance.TITLE_UPCOMING_MOVIE);
                Navigation.findNavController(binding.getRoot()).getCurrentDestination().setLabel(Constance.TITLE_UPCOMING_MOVIE);
                showUpcomingMovies(page);
                loadMoreUpcomingMovies();
                break;
            case Constance.VALUE_TITLE_NOW_PLAYING_MOVIE:
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Constance.TITLE_NOW_PLAYING_MOVIE);
                Navigation.findNavController(binding.getRoot()).getCurrentDestination().setLabel(Constance.TITLE_NOW_PLAYING_MOVIE);
                showNowPlayingMovies(page);
                loadMoreNowPlayingMovies();
                break;
            default:
        }
    }
}