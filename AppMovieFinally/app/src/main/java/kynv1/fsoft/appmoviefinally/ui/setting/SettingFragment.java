package kynv1.fsoft.appmoviefinally.ui.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kynv1.fsoft.appmoviefinally.R;
import kynv1.fsoft.appmoviefinally.databinding.FragmentSettingBinding;
import kynv1.fsoft.appmoviefinally.utils.Constance;


public class SettingFragment extends Fragment {

    private FragmentSettingBinding binding;
    private CategoryFragment categoryFragment;
    private SortByFragment sortByFragment;
    private FromReleaseYearFragment fromReleaseYearFragment;
    private MovieWithRateFomFragment movieWithRateFomFragment;
    private SharedPreferences sh, shTypeMovie;
    NavController navController;


    private static final String TAG = "SettingFragment";

    public SettingFragment() {
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(inflater,container,false);
        sh  = getActivity().getSharedPreferences(Constance.KEY_FILTER, Context.MODE_PRIVATE);
        shTypeMovie = getActivity().getSharedPreferences(Constance.KEY_TYPE_MOVIE,Context.MODE_PRIVATE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupBegin();
        setupCategory();
        setupSortBy();
        setUpFromReleaseYear();
        setupMovieWithRateFrom();

    }

    private void setupBegin() {
        String category =  sh.getString(Constance.KEY_FILTER_CATEGORY,"");
        int movieWithRateFrom = sh.getInt(Constance.KEY_FILTER_MOVIE_WITH_RATE_FROM,0);
        int fromRelease = sh.getInt(Constance.KEY_FILTER_FROM_RELEASE,0);
        String sortBy = sh.getString(Constance.KEY_FILTER_SORT_BY,"");


        binding.txtItemCategory.setText(category);
        binding.txtNumberOfStars.setText(movieWithRateFrom+"");
        binding.txtYear.setText(fromRelease+"");
        binding.txtItemSortBy.setText(sortBy);
    }


    private void setupMovieWithRateFrom() {
        binding.layoutMovieWithRateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieWithRateFomFragment = new MovieWithRateFomFragment(new MovieWithRateFromChoose() {
                    @Override
                    public void sendDataMovieWithRateFrom(String text) {
                        binding.txtNumberOfStars.setText(text);
                        Log.d(TAG, "sendDataMovieWithRateFrom: ");
                    }
                });
                movieWithRateFomFragment.show(getActivity().getSupportFragmentManager(),"moviWithRateFromFragment");
            }

        });
    }

    private void setUpFromReleaseYear() {
        binding.layoutFromReleaseYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromReleaseYearFragment = new FromReleaseYearFragment(new FromReleaseYearChoose() {
                    @Override
                    public void sendDataFromReleaseYear(String text) {
                        binding.txtYear.setText(text);
                    }
                });
                fromReleaseYearFragment.show(getActivity().getSupportFragmentManager(),"fromReleaseYEarFRagment");
            }
        });
    }

    private void setupSortBy() {
        binding.layoutSortBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                sortByFragment  =new SortByFragment(new SortByChoose() {
                    @Override
                    public void sendDataSortBy(String text) {
                        Log.d(TAG, "sendDataSortBy: "+text);
                        binding.txtItemSortBy.setText(text);

                        NavController navController = Navigation.findNavController(view);
                        navController.navigate(R.id.action_settingFragment_to_movieFragment);

                        int id = navController.getCurrentDestination().getId();
                        if(id == R.id.movieFragment){
                            navController.popBackStack(id,true);
                            navController.navigate(id);
                        }
                    }
                });
                sortByFragment.show(getActivity().getSupportFragmentManager(),"sortbydialog");
            }
        });
    }

    private void setupCategory() {
        binding.layoutCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                categoryFragment = new CategoryFragment(new CategoryChoose() {
                    @Override
                    public void sendDataCategory(String text) {
                        Log.d(TAG, "sendDataCategory: "+text);
                        binding.txtItemCategory.setText(text);
                    }
                });
                categoryFragment.show(getActivity().getSupportFragmentManager(),"categorydialog");
            }
        });
    }

    

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor =  sh.edit();
        editor.putString(Constance.KEY_FILTER_CATEGORY,binding.txtItemCategory.getText().toString());
        editor.putInt(Constance.KEY_FILTER_MOVIE_WITH_RATE_FROM,Integer.parseInt(binding.txtNumberOfStars.getText().toString()));
        editor.putInt(Constance.KEY_FILTER_FROM_RELEASE,Integer.parseInt(binding.txtYear.getText().toString()));
        editor.putString(Constance.KEY_FILTER_SORT_BY,binding.txtItemSortBy.getText().toString());

        Log.d(TAG, "onPause: "+sh.getInt(Constance.KEY_FILTER_MOVIE_WITH_RATE_FROM,0));
        editor.apply();

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
        SharedPreferences.Editor editor1 = shTypeMovie.edit();
        if(sh.getString(Constance.KEY_FILTER_CATEGORY,"").equals(Constance.TITLE_POPULAR_MOVIE)){
            editor1.putInt(Constance.KEY_TITLE_MOVIE,Constance.VALUE_TITLE_POPULAR_MOVIE);
        }else if(sh.getString(Constance.KEY_FILTER_CATEGORY,"").equals(Constance.TITLE_TOP_RATE_MOVIE)){
            editor1.putInt(Constance.KEY_TITLE_MOVIE,Constance.VALUE_TITLE_TOP_RATE_MOVIE);
        }else if(sh.getString(Constance.KEY_FILTER_CATEGORY,"").equals(Constance.TITLE_UPCOMING_MOVIE)){
            editor1.putInt(Constance.KEY_TITLE_MOVIE,Constance.VALUE_TITLE_UPCOMING_MOVIE);
        } else if(sh.getString(Constance.KEY_FILTER_CATEGORY,"").equals(Constance.TITLE_NOW_PLAYING_MOVIE)){
            editor1.putInt(Constance.KEY_TITLE_MOVIE,Constance.VALUE_TITLE_NOW_PLAYING_MOVIE);
        }
        editor1.apply();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}