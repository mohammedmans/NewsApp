package com.example.mohammedmansour.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mohammedmansour.newsapp.API.Responses.Everything.ArticlesItem;
import com.example.mohammedmansour.newsapp.API.Responses.Everything.Source;

public class NewsWholeContentActivity extends AppCompatActivity {

    TextView title, date, desc, content, see_more;
    ImageView imageView;
    ArticlesItem articlesItem = null;
    Source source = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("activityLabel"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                onBackPressed();
            }
        });



        title = findViewById(R.id.content_title);
        date = findViewById(R.id.content_date);
        desc = findViewById(R.id.content_desc);
        content = findViewById(R.id.content_content);
        imageView = findViewById(R.id.content_image);
        see_more = findViewById(R.id.see_more);
        articlesItem = getIntent().getParcelableExtra("newsItem");
        source = getIntent().getParcelableExtra("newsItemSource");

        title.setText(articlesItem.getTitle());
        date.setText(articlesItem.getPublishedAt());
        desc.setText(articlesItem.getDescription());
        content.setText(articlesItem.getContent());

        getSupportActionBar().setTitle(articlesItem.getSource_name());

        Glide.with(this).load(articlesItem.getUrlToImage())
                .into(imageView);
        see_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(articlesItem.getUrl()));
                startActivity(i);
            }
        });
    }


    //    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        setContentView(R.layout.activity_news_details_content);
//    }
}
