package com.example.mohammedmansour.newsapp.API.Responses.Everything;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class ArticlesItem implements Parcelable {



    @SerializedName("publishedAt")
    private String publishedAt;

    @SerializedName("author")
    private String author;

    @SerializedName("urlToImage")
    private String urlToImage;

    @SerializedName("description")
    private String description;

    // we don't need source to be exist in article table
    // because room doesn't allow for object references between entities
    // and what we need from this table like id ... create here like next ...
    // and set this var here form ignorenace object
    @ColumnInfo
    String source_id;

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    @ColumnInfo
    String source_name;

    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }

    @Ignore
    @SerializedName("source")
    private Source source;

    @SerializedName("title")
    private String title;

    @PrimaryKey
    @NonNull
    @SerializedName("url")
    private String url;

    @SerializedName("content")
    private String content;

    protected ArticlesItem(Parcel in) {
        publishedAt = in.readString();
        author = in.readString();
        urlToImage = in.readString();
        description = in.readString();
        title = in.readString();
        url = in.readString();
        content = in.readString();
        source_name = in.readString();
    }

    public ArticlesItem() {
    }

    public static final Creator<ArticlesItem> CREATOR = new Creator<ArticlesItem>() {
        @Override
        public ArticlesItem createFromParcel(Parcel in) {
            return new ArticlesItem(in);
        }

        @Override
        public ArticlesItem[] newArray(int size) {
            return new ArticlesItem[size];
        }
    };


    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Source getSource() {
        return source;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return
                "ArticlesItem{" +
                        "publishedAt = '" + publishedAt + '\'' +
                        ",author = '" + author + '\'' +
                        ",urlToImage = '" + urlToImage + '\'' +
                        ",description = '" + description + '\'' +
                        ",sourceName = '" + source_name + '\'' +
                        ",source = '" + source + '\'' +
                        ",title = '" + title + '\'' +
                        ",url = '" + url + '\'' +
                        ",content = '" + content + '\'' +
                        "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(publishedAt);
        parcel.writeString(author);
        parcel.writeString(urlToImage);
        parcel.writeString(description);
        parcel.writeString(title);
        parcel.writeString(url);
        parcel.writeString(content);
        parcel.writeString(source_name);
    }

}