package com.example.mohammedmansour.newsapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mohammedmansour.newsapp.API.Responses.Everything.ArticlesItem;
import com.example.mohammedmansour.newsapp.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.CustomViewHolder> {
    List<ArticlesItem> articlesItems;
    Context context;
    OnNewsClickListener onNewsClickListener;




    public void setOnNewsClickListener(OnNewsClickListener onNewsClickListener) {
        this.onNewsClickListener = onNewsClickListener;
    }

    public NewsAdapter(List<ArticlesItem> articlesItems, Context context) {
        this.articlesItems = articlesItems;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item_view, parent, false);
        return new CustomViewHolder(view);
    }

    public List<ArticlesItem> getArticlesItems() {

        return articlesItems;
    }

    public void setArticlesItems(List<ArticlesItem> articlesItems) {
        this.articlesItems = articlesItems;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, final int i) {
        final ArticlesItem articlesItem = articlesItems.get(i);
        customViewHolder.title.setText(articlesItem.getTitle());
        customViewHolder.desc.setText(articlesItem.getDescription());
        customViewHolder.date.setText(articlesItem.getPublishedAt());

        customViewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    onNewsClickListener.onNewsClick(i,articlesItem);

            }
        });

        Glide.with(context).load(articlesItem.getUrlToImage())
                .into(customViewHolder.image);
    }

    public void setUpdateddData(List<ArticlesItem> articlesItems) {
        this.articlesItems = articlesItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (articlesItems == null)
            return 0;
        return articlesItems.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView date, title, desc;
        ImageView image;
        View parent;

        public CustomViewHolder(@NonNull View view) {
            super(view);
            date = view.findViewById(R.id.date);
            title = view.findViewById(R.id.title);
            desc = view.findViewById(R.id.desc);
            image = view.findViewById(R.id.image);
            parent = view;
        }
    }

    public interface OnNewsClickListener {
        void onNewsClick(int position, ArticlesItem articlesItem);
    }

}
