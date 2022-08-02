package kynv1.fsoft.appmovie.data.remote;

import kynv1.fsoft.appmovie.model.Popular;
import kynv1.fsoft.appmovie.utls.Constance;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    ApiService apiService = new Retrofit.Builder()
            .baseUrl(Constance.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);


    @GET(Constance.KEY_MOVIE_POPULAR)
    Call<Popular> getPopularMovies(@Query("api_key") String api_key, @Query("page") int page);

}
