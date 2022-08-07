package kynv1.fsoft.appmoviefinally.ui.reminder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import kynv1.fsoft.appmoviefinally.R;
import kynv1.fsoft.appmoviefinally.database.MovieDatabase;
import kynv1.fsoft.appmoviefinally.databinding.FragmentReminderBinding;
import kynv1.fsoft.appmoviefinally.model.reminder.Result;
import kynv1.fsoft.appmoviefinally.ui.favorite.IClickDeleteItem;


public class ReminderFragment extends Fragment implements ClickItemReminder {
    private FragmentReminderBinding binding;
    private ReminderAdapter reminderAdapter;
    private List<Result> results;

    public ReminderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentReminderBinding.inflate(inflater, container, false);
        setItemsInRecyclerView();
        return binding.getRoot();
    }

    private void setItemsInRecyclerView() {

        reminderAdapter = new ReminderAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.rcvReminderShowAll.setLayoutManager(linearLayoutManager);
        reminderAdapter.setData(results);
        binding.rcvReminderShowAll.setAdapter(reminderAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();

    }

    private void loadData() {
        results = MovieDatabase.getInstance(getContext()).reminderDao().getAll();
        reminderAdapter.setData(results);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void deleteReminder(Result result) {

        new AlertDialog.Builder(getContext())
                .setTitle("Are your sure to delete ")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MovieDatabase.getInstance(getContext()).reminderDao().delete(result);
                        loadData();
                    }
                })
                .setNegativeButton("No ", null)
                .show();

    }
}