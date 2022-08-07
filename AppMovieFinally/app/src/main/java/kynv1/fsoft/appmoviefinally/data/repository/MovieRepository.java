package kynv1.fsoft.appmoviefinally.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import kynv1.fsoft.appmoviefinally.data.remote.ApiService;
import kynv1.fsoft.appmoviefinally.model.Detail;
import kynv1.fsoft.appmoviefinally.model.MovieItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private static final String TAG = "MovieRepository";

    public LiveData<MovieItem> getPopularMovies(String api_key, Integer page) {
        MutableLiveData<MovieItem> data = new MutableLiveData<>();
        ApiService.apiService.getPopularMovies(api_key, page).enqueue(new Callback<MovieItem>() {
            @Override
            public void onResponse(Call<MovieItem> call, Response<MovieItem> response) {
                Log.d(TAG, "onResponse: ");
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MovieItem> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<MovieItem> getTopRateMovies(String api_key, Integer page) {
        MutableLiveData<MovieItem> data = new MutableLiveData<>();
        ApiService.apiService.getRateMovies(api_key, page).enqueue(new Callback<MovieItem>() {
            @Override
            public void onResponse(Call<MovieItem> call, Response<MovieItem> response) {
                Log.d(TAG, "onResponse: ");
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MovieItem> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                data.setValue(null);
            }
        });
        return data;
    }


    public LiveData<MovieItem> getUpcomingMovies(String api_key, Integer page) {
        MutableLiveData<MovieItem> data = new MutableLiveData<>();
        ApiService.apiService.getUpComingMoives(api_key, page).enqueue(new Callback<MovieItem>() {
            @Override
            public void onResponse(Call<MovieItem> call, Response<MovieItem> response) {
                Log.d(TAG, "onResponse: ");
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MovieItem> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<MovieItem> getNowPlayingMovies(String api_key, Integer page) {
        MutableLiveData<MovieItem> data = new MutableLiveData<>();
        ApiService.apiService.getNowPlayMovies(api_key, page).enqueue(new Callback<MovieItem>() {
            @Override
            public void onResponse(Call<MovieItem> call, Response<MovieItem> response) {
                Log.d(TAG, "onResponse: ");
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MovieItem> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<Detail> getDetailMovies(Integer movieId, String api_key) {
        MutableLiveData<Detail> data = new MutableLiveData<>();
        ApiService.apiService.getDetailMovies(movieId, api_key).enqueue(new Callback<Detail>() {
            @Override
            public void onResponse(Call<Detail> call, Response<Detail> response) {
                Log.d(TAG, "onResponse: ");
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Detail> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                data.setValue(null);
            }
        });
        return data;
    }


}
