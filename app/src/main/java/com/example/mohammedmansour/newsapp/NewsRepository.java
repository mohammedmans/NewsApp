package com.example.mohammedmansour.newsapp;

import android.content.Context;

import com.example.mohammedmansour.newsapp.API.APIManager;
import com.example.mohammedmansour.newsapp.API.Responses.Everything.ArticlesItem;
import com.example.mohammedmansour.newsapp.API.Responses.Everything.EverythingResponse;
import com.example.mohammedmansour.newsapp.API.Responses.Everything.Source;
import com.example.mohammedmansour.newsapp.API.Responses.Source.SourceResponse;
import com.example.mohammedmansour.newsapp.API.Responses.Source.SourcesItem;
import com.example.mohammedmansour.newsapp.Base.BaseActivity;
import com.example.mohammedmansour.newsapp.Database.NewsDB;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository extends BaseActivity {
    private static String apiKey = "b6c9953f1488488b9f4441da294f9e0f";
    Context context;
    NewsDB newsDB;

    public NewsRepository(Context context) {
        this.context = context;
        newsDB = NewsDB.getInstance(context);
    }

    // used to pass data to activity
    public interface OnSourcesPreparedListener {
        void sourcesPreparedListener(List<SourcesItem> sourcesItems);
    }

    // like in adapter we created object from interface and receive in on bind
    // so we create object in method and receive when got response from web service
    public void getSources(final OnSourcesPreparedListener listener) { // create & work on thread then return data then called Onprelistener
        // this method take listener when data is ready return to  listener
        // if web service return data so get data from web server else get data from DB on Fail
        APIManager.getAPIs()
                .getSourceName(apiKey, "en")
                .enqueue(new Callback<SourceResponse>() {
                    @Override
                    public void onResponse(Call<SourceResponse> call, Response<SourceResponse> response) {
                        // response from getSources first add to DB then back to activity -> using threading
                        // this getSources Work on Background thread and return data to this method
                        // return response  to activity through Callback | interface like OnItemClickListener Interface on Adapter,
                        // Thread call another thread
                        // create interface to connect with Activity like interface on Adapter

                        // like we check if object != null in adapter to check if clicked or not
                        if (response.body().getStatus().equals("ok")) {
                            listener.sourcesPreparedListener(response.body().getSources());
                            // todo: insert data in Database
                            insertSourcesToDB((ArrayList<SourcesItem>) response.body().getSources());

                        } else {
                            listener.sourcesPreparedListener(null);

                        }

                    }

                    @Override
                    public void onFailure(Call<SourceResponse> call, Throwable t) {
                        // get Sources from Database
                        getSourcesFromDB(listener);

                    }
                });
    }

    public interface OnArticlesPreparesListener {
        void articlesPreparedListener(List<ArticlesItem> articlesItems);
    }

    public void getArticles(final String source_id, final OnArticlesPreparesListener onArticlesPreparesListener) {

        APIManager.getAPIs()
                .getEverythingRespone(apiKey, source_id)
                .enqueue(new Callback<EverythingResponse>() {
                    @Override
                    public void onResponse(Call<EverythingResponse> call, Response<EverythingResponse> response) {

                        if (response.body().getStatus().equals("ok")) {
                            onArticlesPreparesListener.articlesPreparedListener(response.body().getArticles());
                            // todo: insert data in Database
                            insertArticlesToDB((ArrayList<ArticlesItem>) response.body().getArticles());

                        } else {
                            onArticlesPreparesListener.articlesPreparedListener(null);

                        }

                    }

                    @Override
                    public void onFailure(Call<EverythingResponse> call, Throwable t) {
                        // get Articles from Database
                        getArticlesFromDB(source_id, onArticlesPreparesListener);
                    }
                });
    }

    // create thread when add data to DB to not block main thread
    private void insertSourcesToDB(final ArrayList<SourcesItem> sourcesItems) {
        // create thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                newsDB.sourcesDao().addAllSources(sourcesItems);
            }
        }).start();
    }

    private void insertArticlesToDB(final ArrayList<ArticlesItem> articlesItems) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (ArticlesItem articlesItem : articlesItems) {
                    articlesItem.setSource_id(articlesItem.getSource().getId());
                    articlesItem.setSource_name(articlesItem.getSource().getName());
                }
                newsDB.articlesDao().addAllArticles(articlesItems);
            }
        }).start();
    }

    private void getSourcesFromDB(final OnSourcesPreparedListener sourcesPreparedListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                sourcesPreparedListener.sourcesPreparedListener(newsDB.sourcesDao().getAllSources());
            }
        }).start();

    }

    private void getArticlesFromDB(final String source_id, final OnArticlesPreparesListener articlesPreparesListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                articlesPreparesListener.articlesPreparedListener(newsDB.articlesDao().getArticlesbySourceId(source_id));
            }
        }).start();

    }
}
