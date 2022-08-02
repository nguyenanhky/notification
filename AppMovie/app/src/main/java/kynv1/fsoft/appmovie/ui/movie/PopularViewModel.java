package kynv1.fsoft.appmovie.ui.movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import kynv1.fsoft.appmovie.data.remote.PopularShowRepository;
import kynv1.fsoft.appmovie.model.Popular;

public class PopularViewModel extends ViewModel {
    private PopularShowRepository popularShowRepository;

    public PopularViewModel() {
        popularShowRepository = new PopularShowRepository();
    }
    public LiveData<Popular>getPopularMovies(String api_key,Integer page){
        return popularShowRepository.getPopularMoivies(api_key,page);
    }
}
