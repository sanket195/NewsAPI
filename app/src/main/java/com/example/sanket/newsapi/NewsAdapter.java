package com.example.sanket.newsapi;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sanket.newsapi.data.Contract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sanket on 6/28/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {
    private ItemClickListener listener;
    private Cursor cursor;
    private Context context;
    public static final String TAG = "newsadapter";

    public NewsAdapter(Cursor cursor, ItemClickListener listener){

        this.cursor = cursor;
        this.listener = listener;
        Log.d(TAG, "NewsAdapter is been created");
    }

    public interface ItemClickListener{
        void OnItemClick(Cursor cursor, int clickedItem);
    }

    class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView author;
        public TextView title;
        public TextView description;
        public ImageView image;

        NewsAdapterViewHolder(View view){
            super(view);
            author = view.findViewById(R.id.author);
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            image = view.findViewById(R.id.img);
            view.setOnClickListener(this);
            Log.d(TAG, "NewsAdapterViewHolder has been created");
        }

        public void bind(int position) {
            cursor.moveToPosition(position);
            author.setText(cursor.getString(cursor.getColumnIndex((Contract.TABLE_NEWS.COLUMN_NAME_AUTHOR))));
            title.setText(cursor.getString(cursor.getColumnIndex((Contract.TABLE_NEWS.COLUMN_NAME_TITLE))));
            description.setText(cursor.getString(cursor.getColumnIndex((Contract.TABLE_NEWS.COLUMN_NAME_DESCRIPTION))));
            String imageUrl = cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWS.COLUMN_NAME_URL_TO_IMAGE));

            if(imageUrl != null){
                Picasso.with(context).load(imageUrl).into(image);
            }

            Log.d(TAG + " @imageUrl: ", cursor.getString(cursor.getColumnIndex((Contract.TABLE_NEWS.COLUMN_NAME_URL_TO_IMAGE))));
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            listener.OnItemClick(cursor, position);
        }
    }

    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
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
        return cursor.getCount();
    }

}
