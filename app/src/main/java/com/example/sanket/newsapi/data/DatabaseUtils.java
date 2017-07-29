package com.example.sanket.newsapi.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sanket.newsapi.NewsItem;

import java.util.ArrayList;

import static com.example.sanket.newsapi.data.Contract.TABLE_NEWS.COLUMN_NAME_AUTHOR;
import static com.example.sanket.newsapi.data.Contract.TABLE_NEWS.COLUMN_NAME_DESCRIPTION;
import static com.example.sanket.newsapi.data.Contract.TABLE_NEWS.COLUMN_NAME_PUBLISHED_AT;
import static com.example.sanket.newsapi.data.Contract.TABLE_NEWS.COLUMN_NAME_TITLE;
import static com.example.sanket.newsapi.data.Contract.TABLE_NEWS.COLUMN_NAME_URL;
import static com.example.sanket.newsapi.data.Contract.TABLE_NEWS.COLUMN_NAME_URL_TO_IMAGE;
import static com.example.sanket.newsapi.data.Contract.TABLE_NEWS.TABLE_NAME;

/**
 * Created by sanket on 7/27/2017.
 */

public class DatabaseUtils {

    public static Cursor getAll(SQLiteDatabase db) {
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_NAME_PUBLISHED_AT + " DESC"
        );
        return cursor;
    }

    public static void bulkInsert (SQLiteDatabase db, ArrayList<NewsItem> items) {
        db.beginTransaction();
        try {
            for (NewsItem i : items) {
                ContentValues cv = new ContentValues();
                Log.d("DatabaseUtils: ", "Author's name: " + i.getAuthor());
                cv.put(COLUMN_NAME_AUTHOR, i.getAuthor());
                cv.put(COLUMN_NAME_TITLE, i.getTitle());
                cv.put(COLUMN_NAME_DESCRIPTION, i.getDescription());
                cv.put(COLUMN_NAME_URL, i.getUrl());
                cv.put(COLUMN_NAME_URL_TO_IMAGE, i.getUrlToImage());
                cv.put(COLUMN_NAME_PUBLISHED_AT, i.getPublishedAt());
                db.insert(TABLE_NAME, null, cv);
            }
            Log.d("DatabaseUtils: ", "List size: " + items.size());
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void deleteAll(SQLiteDatabase db) { db.delete(TABLE_NAME, null, null); }
}
