package kynv1.fsoft.appmoviefinally.ui.setting;

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
import kynv1.fsoft.appmoviefinally.databinding.FragmentSortByBinding;
import kynv1.fsoft.appmoviefinally.utils.Constance;


public class SortByFragment extends DialogFragment {

    FragmentSortByBinding binding;
    private SortByChoose sortByChoose;

    private static final String TAG = "SortByFragment";

    public SortByFragment(SortByChoose sortByChoose) {
        // Required empty public constructor
        this.sortByChoose = sortByChoose;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSortByBinding.inflate(inflater,container,false);
        setupSortBy();
        return binding.getRoot();
    }

    private void setupSortBy() {
        binding.radioRating.setText(Constance.SORT_BY_RATE);
        binding.radioReleaseDate.setText(Constance.SORT_BY_DATE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.radioReleaseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+ binding.radioReleaseDate.getText().toString());
                sortByChoose.sendDataSortBy(binding.radioReleaseDate.getText().toString());
                dismiss();
            }
        });

        binding.radioRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+binding.radioRating.getText().toString());
                sortByChoose.sendDataSortBy(binding.radioRating.getText().toString());
                dismiss();
            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}