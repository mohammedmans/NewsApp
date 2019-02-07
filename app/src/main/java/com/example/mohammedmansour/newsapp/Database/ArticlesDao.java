package com.example.mohammedmansour.newsapp.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.mohammedmansour.newsapp.API.Responses.Everything.ArticlesItem;

import java.util.List;

@Dao
public interface ArticlesDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void addAllArticles(List<ArticlesItem> articlesItems);

    @Query("delete from articlesitem")
    void removeAllOldArticles();

    @Query("select * from ArticlesItem where  source_id=:source_id")
    List<ArticlesItem> getArticlesbySourceId(String source_id);
}
