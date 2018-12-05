package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import java.util.List;

//Create a NewsItemViewModel class that extends from ViewModel (in the Android Architectures api)

public class NewsItemViewModel extends AndroidViewModel{

    //In it keep a Repository object. It should keep an instance of a respository and an instance of LiveData<List<NewsItem>>
    private Repository mRepository;
    private LiveData<List<NewsItem>> mAllNewsItems;

    public NewsItemViewModel (Application application) {
        super(application);
        mRepository = new Repository(application);
        mAllNewsItems = mRepository.getAllNewsItems();
    }

    // It should also have a method that returns the LiveData list.
    public LiveData<List<NewsItem>> getAllNewsItems() {
        return mAllNewsItems;
    }

    public void update() {
        mRepository.updateAll();
    }

    public void deleteAll(){
        mRepository.deleteAll();
    }
}
