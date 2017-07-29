package com.example.sanket.newsapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.sanket.newsapi.data.Contract;
import com.example.sanket.newsapi.data.DBHelper;
import com.example.sanket.newsapi.data.DatabaseUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>, NewsAdapter.ItemClickListener {

    static final String TAG = "mainactivity";

    private ProgressBar progress;
    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private JsonParser jParser;
    private SQLiteDatabase db;
    private Cursor cursor;
    private static final int NEWS_LOADER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_news);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        SharedPreferences prefer = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst = prefer.getBoolean("isFirst", true);

        if(isFirst){
            load();
            SharedPreferences.Editor editor = prefer.edit();
            editor.putBoolean("isFirst", false);
            editor.commit();
        }
        ScheduleUtilities.scheduleRefresh(this);



    }


    @Override
    protected void onStart() {
        super.onStart();
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);
        mNewsAdapter = new NewsAdapter(cursor, this);
        mRecyclerView.setAdapter(mNewsAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search) {
            load();
        }

        return true;
    }

    private void openPage(String url) {
        Uri webPage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public Loader<Void> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "on CreateLoader has started");
        return new AsyncTaskLoader<Void>(this) {

            // Show the progress bar when loading
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                progress.setVisibility(View.VISIBLE);
            }

            // In the background thread, refresh the news items
            @Override
            public Void loadInBackground() {
                RefreshTasks.refreshArticles(MainActivity.this);
                return null;
            }
        };

    }

    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
        Log.d(TAG, "on Load Finished has started");
        progress.setVisibility(View.GONE);
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);

        mNewsAdapter = new NewsAdapter(cursor, this);
        mRecyclerView.setAdapter(mNewsAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {

    }

    @Override
    public void OnItemClick(Cursor cursor, int clickedItem) {
        cursor.moveToPosition(clickedItem);
        String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWS.COLUMN_NAME_URL));
        Log.d(TAG, String.format("Url %s", url));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    public void load() {
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(NEWS_LOADER, null, this).forceLoad();
    }


}
