package kynv1.fsoft.appmoviefinally.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewModelNumberOfFavorite extends ViewModel {
    public final MutableLiveData<Integer> numberOfFavorite = new MutableLiveData<>();

    public void setupNumberOfFavorite(Integer count){
        numberOfFavorite.setValue(count);
    }

    public LiveData<Integer>getCount(){
        return numberOfFavorite;
    }
}
