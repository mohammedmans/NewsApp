package com.example.mohammedmansour.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mohammedmansour.newsapp.API.Responses.Everything.ArticlesItem;

import java.io.Serializable;

public class NewsWholeContentActivity extends AppCompatActivity {

    TextView title,date,desc,content,see_more;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_whole_content);
        getSupportActionBar().setTitle(getIntent().getStringExtra("activityLabel"));
        title = findViewById(R.id.content_title);
        date = findViewById(R.id.content_date);
        desc = findViewById(R.id.content_desc);
        content = findViewById(R.id.content_content);
        imageView = findViewById(R.id.content_image);
        see_more = findViewById(R.id.see_more);

        title.setText(getIntent().getStringExtra("newsContentTitle"));
        date.setText(getIntent().getStringExtra("newsContentDate"));
        desc.setText(getIntent().getStringExtra("newsContentDesc"));
        content.setText(getIntent().getStringExtra("newsContent"));
        Glide.with(this).load(getIntent().getStringExtra("newsContentImg"))
                .into(imageView);
        see_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String url = getIntent().getStringExtra("newsContentURL");
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}
