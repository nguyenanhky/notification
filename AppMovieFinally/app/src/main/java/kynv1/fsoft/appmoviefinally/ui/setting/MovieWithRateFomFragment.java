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
import android.widget.SeekBar;

import kynv1.fsoft.appmoviefinally.R;
import kynv1.fsoft.appmoviefinally.databinding.FragmentMovieWithRateFomBinding;


public class MovieWithRateFomFragment extends DialogFragment {

    private FragmentMovieWithRateFomBinding binding;
    private MovieWithRateFromChoose movieWithRateFromChoose;
    private static final String TAG = "MovieWithRateFomFragment";
    private int index;


    public MovieWithRateFomFragment(MovieWithRateFromChoose movieWithRateFromChoose) {
        // Required empty public constructo
        this.movieWithRateFromChoose =movieWithRateFromChoose;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMovieWithRateFomBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.d(TAG, "onProgressChanged: ");
                index = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStopTrackingTouch: ");
                movieWithRateFromChoose.sendDataMovieWithRateFrom(index+ "");
                dismiss();
            }
        });
    }
}