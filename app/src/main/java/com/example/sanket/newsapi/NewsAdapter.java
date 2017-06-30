package com.example.sanket.newsapi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sanket on 6/28/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {
    private ArrayList<NewsItem> mNewsData;
    ItemClickListener listener;

    public NewsAdapter(ArrayList<NewsItem> mNewsData, ItemClickListener listener){

        this.mNewsData = mNewsData;
        this.listener = listener;
    }

    public interface ItemClickListener{
        void OnItemClick(int clickedItem);
    }

    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView author;
        public TextView title;
        public TextView description;
        public TextView url;
        public TextView urlToImage;
        public TextView publishedAt;

        public NewsAdapterViewHolder(View view){
            super(view);
            author = (TextView)view.findViewById(R.id.author);
            title = (TextView)view.findViewById(R.id.title);
            description = (TextView)view.findViewById(R.id.description);
            url = (TextView)view.findViewById(R.id.url);
            urlToImage = (TextView)view.findViewById(R.id.url_to_image);
            publishedAt = (TextView)view.findViewById(R.id.published_at);
            view.setOnClickListener(this);
        }

        public void bind(int position) {
            NewsItem item = mNewsData.get(position);
            author.setText(item.getAuthor());
            title.setText(item.getTitle());
            description.setText(item.getDescription());
            url.setText(item.getUrl());
            urlToImage.setText(item.getUrlToImage());
            publishedAt.setText(item.getPublishedAt());
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            listener.OnItemClick(position);
        }
    }

    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.news_layout_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new NewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapterViewHolder newsHolder, int position) {
        newsHolder.bind(position);
    }

    @Override
    public int getItemCount(){
        if (mNewsData==null)
            return 0;
        else
            return mNewsData.size();
    }

}
