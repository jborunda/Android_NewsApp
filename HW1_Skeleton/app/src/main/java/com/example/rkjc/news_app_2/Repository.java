package com.example.rkjc.news_app_2;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Repository {
    private NewsItemDao mNewsItemDao;
    private LiveData<List<NewsItem>> mAllNewsItems;

    public Repository(Application application){
        DB db = DB.getDatabase(application.getApplicationContext());
        mNewsItemDao = db.wordDao();
        mAllNewsItems = mNewsItemDao.loadAllNewsItems();
    }

    LiveData<List<NewsItem>> getAllNewsItems() {
        return mAllNewsItems;
    }

    public void updateAll () {
        new updateAsyncTask(mNewsItemDao).execute();
    }

    public void deleteAll(){
        new deleteAsyncTask(mNewsItemDao).execute();
    }

    private static class updateAsyncTask extends AsyncTask<URL, Void, Void> {
        private NewsItemDao mAsyncTaskDao;
        private NewsRecyclerViewAdapter mAdapter;

        updateAsyncTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(URL... params) {
            mAsyncTaskDao.clearAll();

            Log.d("mycode", "RUNNING BGKDSS");
            try {
                String dataString = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildURL());
                Log.d("network string", dataString);
                mAsyncTaskDao.insert(JsonUtils.parseNews(dataString));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

    }

    private static class deleteAsyncTask extends AsyncTask<NewsItem, Void, Void> {
        private NewsItemDao mAsyncTaskDao;
        deleteAsyncTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final NewsItem... params) {
            Log.d("mycode", "deleteting news items: ");
            mAsyncTaskDao.clearAll();
            return null;
        }
    }
}

