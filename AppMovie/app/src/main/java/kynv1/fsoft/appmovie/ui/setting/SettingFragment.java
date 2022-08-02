package kynv1.fsoft.appmovie.ui.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kynv1.fsoft.appmovie.R;
import kynv1.fsoft.appmovie.databinding.FragmentSettingBinding;
import kynv1.fsoft.appmovie.utls.Constance;


public class SettingFragment extends Fragment {
    private FragmentSettingBinding binding;
    private SharedPreferences sh;
    public SettingFragment() {
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
        binding  = FragmentSettingBinding.inflate(inflater,container,false);
        sh = getActivity().getSharedPreferences(Constance.KEY_FILTER, Context.MODE_PRIVATE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupBegin();
    }

    private void setupBegin() {
        String category = sh.getString(Constance.KEY_FILTER_CATEGORY,"");
        int movieWithRateFrom = sh.getInt(Constance.KEY_FILTER_MOVIE_WITH_RATE_FROM,0);
        int fromRelease = sh.getInt(Constance.KEY_FILTER_FROM_RELEASE,0);
        String sortBy = sh.getString(Constance.KEY_FILTER_SORT_BY,"");

        binding.edtCategory.setText(category);
        binding.edtMoveWithRateFrom.setText(movieWithRateFrom+"");
        binding.edtFromRelease.setText(fromRelease+"");
        binding.edtSortBy.setText(sortBy);
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sh.edit();
        editor.putString(Constance.KEY_FILTER_FROM_RELEASE,binding.edtCategory.getText().toString());
        editor.putInt(Constance.KEY_FILTER_MOVIE_WITH_RATE_FROM,Integer.parseInt(binding.edtMoveWithRateFrom.getText().toString()));
        editor.putInt(Constance.KEY_FILTER_FROM_RELEASE,Integer.parseInt(binding.edtFromRelease.getText().toString()));
        editor.putString(Constance.KEY_FILTER_SORT_BY,binding.edtSortBy.getText().toString());
        editor.apply();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}