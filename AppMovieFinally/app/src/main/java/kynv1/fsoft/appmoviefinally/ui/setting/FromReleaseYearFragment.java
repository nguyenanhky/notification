package kynv1.fsoft.appmoviefinally.ui.setting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kynv1.fsoft.appmoviefinally.R;
import kynv1.fsoft.appmoviefinally.databinding.FragmentFromReleaseYearBinding;


public class FromReleaseYearFragment extends DialogFragment {

    private FragmentFromReleaseYearBinding binding;
    private FromReleaseYearChoose releaseYearChoose;

    public FromReleaseYearFragment( FromReleaseYearChoose releaseYearChoose) {
        // Required empty public constructor
        this.releaseYearChoose = releaseYearChoose;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFromReleaseYearBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        binding.txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releaseYearChoose.sendDataFromReleaseYear(binding.edtYear.getText().toString());
                dismiss();
            }
        });
    }
}