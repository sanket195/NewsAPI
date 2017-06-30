package com.example.sanket.newsapi;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sanket on 6/29/2017.
 */

public class JsonParser {

    public static ArrayList<NewsItem> parseJSON(String json) throws JSONException {
        ArrayList<NewsItem> mnewsData = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray articles = main.getJSONArray("articles");

        for (int i = 0; i < articles.length(); i++) {
            JSONObject item = articles.getJSONObject(i);
            String author = item.getString("author");
            String title = item.getString("title");
            String description = item.getString("description");
            String url = item.getString("url");
            String urlToImage = item.getString("urlToImage");
            String publishedAt = item.getString("publishedAt");
            NewsItem ni = new NewsItem(author, title, description, url, urlToImage, publishedAt);
            mnewsData.add(ni);
        }
        Log.d("JSONParser", "Size of ArrayList: " + mnewsData.size());
        return mnewsData;
    }
}
