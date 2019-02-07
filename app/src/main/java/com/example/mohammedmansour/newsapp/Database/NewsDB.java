package com.example.mohammedmansour.newsapp.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.mohammedmansour.newsapp.API.Responses.Everything.ArticlesItem;
import com.example.mohammedmansour.newsapp.API.Responses.Source.SourcesItem;

@Database(entities = {ArticlesItem.class, SourcesItem.class}, version = 1, exportSchema = false)
public abstract class NewsDB extends RoomDatabase {
    private static NewsDB myNewsDB;



    public static NewsDB getInstance(Context context) {
        if (myNewsDB == null)
            // build database , else return myNewsDB
            myNewsDB = Room.databaseBuilder(context.getApplicationContext(),
                    NewsDB.class, "News Database")
                    .allowMainThreadQueries()
                    .build();
        return myNewsDB;
    }

    public abstract SourcesDao sourcesDao();

    public abstract ArticlesDao articlesDao();
}
