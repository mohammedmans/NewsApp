package com.example.mohammedmansour.newsapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

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
    private static String apiKey = "b6c9953f1488488b9f4441da294f9e0f";
    TabLayout tabLayout;
    LinearLayoutManager layoutManager;
    NewsAdapter newsAdapter;
    RecyclerView recyclerView;

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
        newsAdapter.setOnNewsClickListener(new NewsAdapter.OnNewsClickListener() {
            @Override
            public void onNewsClick(int position, ArticlesItem articlesItem) {
                Intent intent = new Intent(MainActivity.this, NewsWholeContentActivity.class);
                intent.putExtra("newsContentTitle",articlesItem.getTitle());
                intent.putExtra("newsContentDate",articlesItem.getPublishedAt());
                intent.putExtra("newsContentDesc",articlesItem.getDescription());
                intent.putExtra("newsContent",articlesItem.getContent());
                intent.putExtra("newsContentURL",articlesItem.getUrl());
                intent.putExtra("activityLabel",articlesItem.getSource().getName());
                //Log.e("newsContent",articlesItem.getContent());
                //Log.e("newsDesc",articlesItem.getDescription());
                intent.putExtra("newsContentImg",articlesItem.getUrlToImage());
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(newsAdapter);
        getNeswSources();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main);
    }

    public void getNeswSources() {
        ShowProgressBar();
        APIManager.getAPIs().getSourceName(apiKey, "en")
                .enqueue(new Callback<SourceResponse>() {
                    @Override
                    public void onResponse(Call<SourceResponse> call, Response<SourceResponse> response) {
                        HideProgressBar();
                        if (response.body().getStatus().equals("ok")) {
                            fillTabLayout(response.body().getSources());

                        } else {
                            ShowMessage(getString(R.string.error), response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<SourceResponse> call, Throwable t) {
                        ShowMessage(getString(R.string.error), t.getLocalizedMessage());
                        HideProgressBar();
                    }
                });
    }

    public void getNews(String sourceID) {
        ShowProgressBar();
        APIManager.getAPIs().getEverythingRespone(apiKey, sourceID)
                .enqueue(new Callback<EverythingResponse>() {
                    @Override
                    public void onResponse(Call<EverythingResponse> call, Response<EverythingResponse> response) {
                        HideProgressBar();
                        if (response.body().getStatus().equals("ok")) {
                            newsAdapter.setUpdateddData(response.body().getArticles());
                        } else {
                            ShowMessage(getString(R.string.error), response.body().getMessages());
                        }
                    }

                    @Override
                    public void onFailure(Call<EverythingResponse> call, Throwable t) {
                        HideProgressBar();
                        ShowMessage(getString(R.string.error), t.getLocalizedMessage());
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
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                getNews(sourcesItems.get(tab.getPosition()).getId());
            }
        });
        tabLayout.getTabAt(0).select();
    }

}
