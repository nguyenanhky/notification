package kynv1.fsoft.appmoviefinally.ui.favorite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kynv1.fsoft.appmoviefinally.R;
import kynv1.fsoft.appmoviefinally.database.MovieDatabase;
import kynv1.fsoft.appmoviefinally.databinding.FragmentFavoriteBinding;
import kynv1.fsoft.appmoviefinally.model.Result;
import kynv1.fsoft.appmoviefinally.ui.movie.MoviesAdapter;
import kynv1.fsoft.appmoviefinally.viewmodel.ViewModelNumberOfFavorite;


public class FavoriteFragment extends Fragment implements IClickDeleteItem {
    private MoviesAdapter moviesAdapter;
    private List<Result> results;
    private List<Result> resultsSearch;
    private FragmentFavoriteBinding binding;
    private ViewModelNumberOfFavorite viewModelNumberOfFavorite;
    private static final String TAG = "FavoriteFragment";

    public FavoriteFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        viewModelNumberOfFavorite = new ViewModelProvider(requireActivity()).get(ViewModelNumberOfFavorite.class);
        resultsSearch = new ArrayList<>();
        Log.d(TAG, "onCreateView: ");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControls();
        loadData();
    }

    private void loadData() {
        results = MovieDatabase.getInstance(getContext()).movieDao().getAll();
        moviesAdapter.setData(results);
    }

    private void addControls() {
        moviesAdapter = new MoviesAdapter(this, requireContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        moviesAdapter.setData(results);
        binding.rcvMovie.setLayoutManager(linearLayoutManager);
        binding.rcvMovie.setAdapter(moviesAdapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_favorite, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i(TAG, "onQueryTextChange: " + newText);
                searchMovies(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void searchMovies(String newText) {
        resultsSearch = MovieDatabase.getInstance(getContext()).movieDao().SearchResult(newText);
        moviesAdapter.setData(resultsSearch);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(getContext(), "nguyen anh ky", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void clickDeleteFavorite(Result result) {
        MovieDatabase.getInstance(getContext()).movieDao().delete(result);
        loadData();
        viewModelNumberOfFavorite.setupNumberOfFavorite(MovieDatabase.getInstance(getContext()).movieDao().getCount());
    }

    @Override
    public void clickInsertFavorite(Result result) {
    }
}
