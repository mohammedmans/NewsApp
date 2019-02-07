package com.example.mohammedmansour.newsapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.mohammedmansour.newsapp.API.APIManager;
import com.example.mohammedmansour.newsapp.API.APIs;
import com.example.mohammedmansour.newsapp.API.Responses.Everything.ArticlesItem;
import com.example.mohammedmansour.newsapp.API.Responses.Everything.EverythingResponse;
import com.example.mohammedmansour.newsapp.API.Responses.Source.SourceResponse;
import com.example.mohammedmansour.newsapp.API.Responses.Source.SourcesItem;
import com.example.mohammedmansour.newsapp.Adapters.NewsAdapter;
import com.example.mohammedmansour.newsapp.Base.BaseActivity;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    TabLayout tabLayout;
    LinearLayoutManager layoutManager;
    NewsAdapter newsAdapter;
    RecyclerView recyclerView;
    NewsRepository newsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tab_layout);
        recyclerView = findViewById(R.id.recycler_view);
        setSupportActionBar(toolbar);
        layoutManager = new LinearLayoutManager(this);
        newsAdapter = new NewsAdapter(null, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(newsAdapter);
        newsRepository = new NewsRepository(activity);
        getNewsSources();
        newsAdapter.setOnNewsClickListener(new NewsAdapter.OnNewsClickListener() {
            @Override
            public void onNewsClick(int position, ArticlesItem articlesItem) {
                Intent intent = new Intent(MainActivity.this, NewsWholeContentActivity.class);
                intent.putExtra("newsItem", articlesItem);
                intent.putExtra("newsItemSource", articlesItem.getSource());
                //intent.putExtra("newsLabel",articlesItem.getSource().getName());
                //Log.e("imageurl",articlesItem.getUrlToImage());
                startActivity(intent);
            }
        });

    }


//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        setContentView(R.layout.activity_main);
//    }

    public void getNewsSources() {
        ShowProgressBar();
        // when data is ready there in repositroy class  back to activity through callback, so that returned sourcesItems below
        // even if data get from web or DB
        newsRepository.getSources(new NewsRepository.OnSourcesPreparedListener() {
            @Override
            public void sourcesPreparedListener(final List<SourcesItem> sourcesItems) {
                HideProgressBar();
                if (sourcesItems != null)
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fillTabLayout(sourcesItems);
                        }
                    });

            }
        });
    }

    public void getNews(String sourceID) {
        ShowProgressBar();
        newsRepository.getArticles(sourceID, new NewsRepository.OnArticlesPreparesListener() {
            @Override
            public void articlesPreparedListener(final List<ArticlesItem> articlesItems) {
                HideProgressBar();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        newsAdapter.setUpdateddData(articlesItems);
                    }
                });

            }
        });
    }

    public void fillTabLayout(final List<SourcesItem> sourcesItems) {
        for (SourcesItem sourcesItem : sourcesItems) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(sourcesItem.getName());
            tabLayout.addTab(tab);
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getNews(sourcesItems.get(tab.getPosition()).getId());
                recyclerView.smoothScrollToPosition(0);
                //getSupportActionBar().show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                getNews(sourcesItems.get(tab.getPosition()).getId());
                recyclerView.smoothScrollToPosition(0);
                //getSupportActionBar().show();

            }
        });
        tabLayout.getTabAt(0).select();
    }

}
