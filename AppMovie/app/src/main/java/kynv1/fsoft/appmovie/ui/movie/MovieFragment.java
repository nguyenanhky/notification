package kynv1.fsoft.appmovie.ui.movie;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import kynv1.fsoft.appmovie.R;
import kynv1.fsoft.appmovie.databinding.FragmentMovieBinding;
import kynv1.fsoft.appmovie.model.Result;
import kynv1.fsoft.appmovie.ui.MainActivity;
import kynv1.fsoft.appmovie.ui.movie.adapter.PopularAdapter;
import kynv1.fsoft.appmovie.utls.Constance;


public class MovieFragment extends Fragment {
    private FragmentMovieBinding binding;
    private List<Result> results;
    private List<Result> resultsFilter;
    private PopularAdapter popularAdapter;
    private PopularViewModel popularViewModel;
    private SharedPreferences sharedPreferences;
    private int page = 1;
    private boolean check = false;
    private static final String TAG = "MovieFragment";

    public MovieFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        popularViewModel = new ViewModelProvider(requireActivity()).get(PopularViewModel.class);
        results = new ArrayList<>();
        popularAdapter = new PopularAdapter(requireContext());
        resultsFilter = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMovieBinding.inflate(inflater, container, false);
        sharedPreferences = getActivity().getSharedPreferences(Constance.KEY_FILTER, Context.MODE_PRIVATE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControls();
        showPopularMovies(page);
        addLoadMore();
    }


    private void addLoadMore() {
        binding.idNestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    Log.d(TAG, "onScrollChange: da den cuoi cung");
                    check = false;
                    binding.idPBLoading.setVisibility(View.VISIBLE);
                    page++;
                    resultsFilter.clear();
                    showPopularMovies(page);
                }
            }
        });
    }

    private void addControls() {
        binding.rcvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rcvMovie.setAdapter(popularAdapter);
        popularAdapter.setData(results);
    }


    private void showPopularMovies(int page) {
        popularViewModel.getPopularMovies(Constance.KEY_VAlUE_MOVIE, page).observe(requireActivity(), popular -> {
            if (popular != null && check == false) {
                Log.d(TAG, "showPopularMovies: nguyen anh ky  ");
                results.addAll(popular.getResults());
            }
            for (int i = 0; i < results.size(); i++) {
                if (checkYear(results.get(i)) ) {
                    resultsFilter.add(results.get(i));
                }
            }
            // sort by Movie with rate from
            Collections.sort(resultsFilter);
            //sort  by From Release
            Log.d(TAG, "resultsFilter.size(): "+resultsFilter.size());
            popularAdapter.setData(resultsFilter);
        });

    }
    public boolean checkYear(Result result){
        String str = result.getRelease_date();
        if(!str.equals("") && str.length()>=4){
            str = str.substring(0,4);
            int year = Integer.parseInt(str);
            int start = sharedPreferences.getInt(Constance.KEY_FILTER_MOVIE_WITH_RATE_FROM, 0);
            if( (result.getVote_average()>=start) && (sharedPreferences.getInt(Constance.KEY_FILTER_FROM_RELEASE,0)<=year)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
        check = true;
        resultsFilter.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}