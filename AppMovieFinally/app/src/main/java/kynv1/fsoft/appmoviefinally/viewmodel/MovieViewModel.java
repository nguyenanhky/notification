package kynv1.fsoft.appmoviefinally.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import kynv1.fsoft.appmoviefinally.data.repository.MovieRepository;
import kynv1.fsoft.appmoviefinally.model.Detail;
import kynv1.fsoft.appmoviefinally.model.MovieItem;

public class MovieViewModel extends ViewModel {
    private MovieRepository movieRepository;

    public MovieViewModel() {
        movieRepository = new MovieRepository();
    }

    public LiveData<MovieItem> getPopularMovies(String api_key, Integer page){
        return  movieRepository.getPopularMovies(api_key,page);
    }

    public LiveData<MovieItem>getTopRateMovies(String api_key, Integer page){
        return movieRepository.getTopRateMovies(api_key,page);
    }

    public LiveData<MovieItem>getUpcomingMovies(String api_key, Integer page){
        return movieRepository.getUpcomingMovies(api_key,page);
    }

    public LiveData<MovieItem>getNowPlayingMovies(String api_key, Integer page){
        return movieRepository.getNowPlayingMovies(api_key,page);
    }
    public LiveData<Detail>getDetailMovies(Integer movieId, String api_key){
        return movieRepository.getDetailMovies(movieId,api_key);
    }


}
