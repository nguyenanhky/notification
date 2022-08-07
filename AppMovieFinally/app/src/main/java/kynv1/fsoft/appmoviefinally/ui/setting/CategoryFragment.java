package kynv1.fsoft.appmoviefinally.ui.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kynv1.fsoft.appmoviefinally.R;
import kynv1.fsoft.appmoviefinally.databinding.FragmentCategoryBinding;
import kynv1.fsoft.appmoviefinally.utils.Constance;

public class CategoryFragment extends DialogFragment {

    private FragmentCategoryBinding binding;
    private static final String TAG = "CategoryFragment";
    private CategoryChoose categoryChoose;
    public CategoryFragment(CategoryChoose categoryChoose) {
        this.categoryChoose = categoryChoose;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupCategory();

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        binding.radioPopularMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG," : " +binding.radioPopularMovies.getText().toString());
                categoryChoose.sendDataCategory(binding.radioPopularMovies.getText().toString());
                dismiss();
            }
        });
        binding.radioUpcomingMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG," : " +binding.radioUpcomingMovies.getText().toString());
                categoryChoose.sendDataCategory(binding.radioUpcomingMovies.getText().toString());
                dismiss();
            }
        });
        binding.radioTopRatedMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG," : " +binding.radioTopRatedMovies.getText().toString());
                categoryChoose.sendDataCategory(binding.radioTopRatedMovies.getText().toString());
                dismiss();
            }
        });
        binding.radioNowPlayingMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG," : " +binding.radioNowPlayingMovies.getText().toString());
                categoryChoose.sendDataCategory(binding.radioNowPlayingMovies.getText().toString());
                dismiss();
            }
        });


    }

    private void setupCategory() {
        binding.radioPopularMovies.setText(Constance.TITLE_POPULAR_MOVIE);
        binding.radioTopRatedMovies.setText(Constance.TITLE_TOP_RATE_MOVIE);
        binding.radioUpcomingMovies.setText(Constance.TITLE_UPCOMING_MOVIE);
        binding.radioNowPlayingMovies.setText(Constance.TITLE_NOW_PLAYING_MOVIE);
    }
}