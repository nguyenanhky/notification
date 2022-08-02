package kynv1.fsoft.appmovie.data.remote;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import kynv1.fsoft.appmovie.model.Popular;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularShowRepository {
    private static final String TAG = "PopularShowRepository";
    public LiveData<Popular>getPopularMoivies(String api_key, Integer page){
        MutableLiveData<Popular>data = new MutableLiveData<>();
        ApiService.apiService.getPopularMovies(api_key,page).enqueue(new Callback<Popular>() {
            @Override
            public void onResponse(Call<Popular> call, Response<Popular> response) {
                Log.d(TAG, "onResponse: ");
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Popular> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                data.setValue(null);
            }
        });
        return data;
    }
}
