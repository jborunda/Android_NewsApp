package com.example.rkjc.news_app_2;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import android.arch.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {
    //In your Activity, make a NewsItemViewModel instance variable
    private NewsItemViewModel mNewsItemViewModel;
    private RecyclerView rv;
    private NewsRecyclerViewAdapter adapter;
    private ArrayList<NewsItem> news = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // In onCreate, set the instance variable to a new NewsItemViewModel using ViewModelProviders
        mNewsItemViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);

        rv = (RecyclerView)findViewById(R.id.news_recyclerview);
        adapter = new NewsRecyclerViewAdapter(this, news);

        //Call your method to get the LiveData object, and set an observer to it with the observe method
        mNewsItemViewModel.getAllNewsItems().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable final List<NewsItem> newsItems) {
                // Update the cached copy of the words in the adapter.
                adapter.setNewsList(newsItems);
            }
        });

        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    //In your menu item listener, call the Repository's sync method
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();

        if (itemThatWasClickedId == R.id.action_search) {
            mNewsItemViewModel.update();
            adapter.mNews.addAll((ArrayList) mNewsItemViewModel.getAllNewsItems().getValue());
            adapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }






//--------------------------------------------------------------------------------------------------
//    public class NewsQueryTask extends AsyncTask<URL, Void, String> {
//
//        @Override
//        protected String doInBackground(URL... urls) {
//            URL newsUrl = urls[0];
//
//            String newsSearchResults = null;
//            try {
//                newsSearchResults = NetworkUtils.getResponseFromHttpUrl(newsUrl);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return newsSearchResults;
//        }
//
//        @Override
//        protected void onPostExecute(String newsSearchResults) {
//            if(newsSearchResults != null && !newsSearchResults.equals("")) {
//                //we want to populate the RecyclerView here
//                news = JsonUtils.parseNews(newsSearchResults);
//                adapter.mNews.addAll(news);
//                adapter.notifyDataSetChanged();
//
//            }
//        }
//    }

}
