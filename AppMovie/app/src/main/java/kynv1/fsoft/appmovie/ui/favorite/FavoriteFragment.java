package kynv1.fsoft.appmovie.ui.favorite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kynv1.fsoft.appmovie.R;
import kynv1.fsoft.appmovie.database.MovieDatabase;
import kynv1.fsoft.appmovie.databinding.FragmentFavoriteBinding;
import kynv1.fsoft.appmovie.model.Result;
import kynv1.fsoft.appmovie.notification.ReminderAdapter;


public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;
    private ReminderAdapter reminderAdapter;
    private List<Result> results;

    public FavoriteFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater,container,false);
        setItemsInRecyclerView();
        return binding.getRoot();
    }

    private void setItemsInRecyclerView() {
        results = MovieDatabase.getInstance(getContext()).movieDao().orderThetable();
        reminderAdapter = new ReminderAdapter(results);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.rcvReminder.setLayoutManager(linearLayoutManager);
        binding.rcvReminder.setAdapter(reminderAdapter);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}