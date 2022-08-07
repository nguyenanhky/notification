package kynv1.fsoft.appmoviefinally.data.remote;

import kynv1.fsoft.appmoviefinally.model.Detail;
import kynv1.fsoft.appmoviefinally.model.MovieItem;
import kynv1.fsoft.appmoviefinally.utils.Constance;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    ApiService apiService = new Retrofit.Builder()
            .baseUrl(Constance.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);

    @GET(Constance.KEY_MOVIE_POPULAR)
    Call<MovieItem>getPopularMovies(@Query("api_key") String api_key, @Query("page") int page);

    @GET(Constance.KEY_RATE_MOVIE)
    Call<MovieItem>getRateMovies(@Query("api_key") String api_key, @Query("page") int page);

    @GET(Constance.KEY_UP_COMING)
    Call<MovieItem>getUpComingMoives(@Query("api_key") String api_key, @Query("page") int page);

    @GET(Constance.KEY_NOW_PLAY)
    Call<MovieItem>getNowPlayMovies(@Query("api_key") String api_key, @Query("page") int page);

    @GET(Constance.KEY_CAST_CREW_LIST)
    Call<Detail>getDetailMovies(@Path(Constance.KEY_PATH) Integer movieId, @Query("api_key") String api_key);

}
