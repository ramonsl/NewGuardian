package com.example.ramonsl.newguardian;

public class News {


    private final String mTitle;
    private final String mSection;
    private final String mAuthor;
    private final String mThumbUrl;
    private final String mDate;
    private String mNewsUrl;

    public News(String title, String section, String author, String thumbUrl, String date, String newsUrl) {
        this.mTitle = title;
        this.mSection = section;
        this.mAuthor = author;
        this.mThumbUrl = thumbUrl;
        this.mDate = date;
        this.mNewsUrl = newsUrl;
    }


    public String getmTitle() {
        return mTitle;
    }

    public String getmSection() {
        return mSection;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmThumbUrl() {
        return mThumbUrl;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmNewsUrl() {
        return mNewsUrl;
    }
}
