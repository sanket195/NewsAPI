package com.example.sanket.newsapi.data;

/**
 * Created by sanket on 7/27/2017.
 */

public class NewsItem {

    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;

    public NewsItem(String authorIn, String titleIn, String descIn, String urlIn, String urlToImageIn, String publishedAtIn) {
        this.author = authorIn;
        this.title = titleIn;
        this.description = descIn;
        this.url = urlIn;
        this.urlToImage = urlToImageIn;
        this.publishedAt = publishedAtIn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
