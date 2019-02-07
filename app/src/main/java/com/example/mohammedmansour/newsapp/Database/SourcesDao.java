package com.example.mohammedmansour.newsapp.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.media.MediaPlayer;

import com.example.mohammedmansour.newsapp.API.Responses.Everything.ArticlesItem;
import com.example.mohammedmansour.newsapp.API.Responses.Source.SourcesItem;

import java.util.List;

@Dao
public interface SourcesDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void addAllSources(List<SourcesItem> sourcesItems);

    @Query("select * from SourcesItem")
    List<SourcesItem> getAllSources();
}
