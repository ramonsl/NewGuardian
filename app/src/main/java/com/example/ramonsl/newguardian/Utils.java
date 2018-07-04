package com.example.ramonsl.newguardian;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Utils {
    private static final String LOG_TAG = Utils.class.getSimpleName();

    private Utils() {

    }

    public static ArrayList<News> extractNewsFromJson(String newsJSON) {
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }
        ArrayList<News> newses = new ArrayList<>();
        try {

            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            JSONObject response = baseJsonResponse.getJSONObject("response");
            JSONArray newsesArray = response.getJSONArray("results");
            for (int i = 0; i < newsesArray.length(); i++) {
                JSONObject currentNews = newsesArray.getJSONObject(i);
                JSONObject fields = currentNews.getJSONObject("fields");
                Log.println(Log.INFO, LOG_TAG, String.valueOf("Fields: " + fields));
                String authorArticle;
                if (currentNews.has("tags")) {
                    JSONArray tagsArray = currentNews.getJSONArray("tags");
                    if (!currentNews.isNull("tags") && tagsArray.length() > 0) {
                        JSONObject tagObject = (JSONObject) tagsArray.get(0);
                        authorArticle = tagObject.getString("webTitle");
                        Log.println(Log.INFO, LOG_TAG, String.valueOf("Author's name: " + authorArticle));
                    } else {
                        authorArticle = "Autor desconhecido";
                    }
                } else {
                    authorArticle = "Autor desconhecido";
                }

                String titleArticle = currentNews.getString("webTitle");
                String sectionArticle = "#" + currentNews.getString("sectionName");
                String imageArticle;
                if (fields.has("thumbnail")) {
                    imageArticle = fields.getString("thumbnail");
                    Log.println(Log.INFO, LOG_TAG, String.valueOf("IMAGE URL: " + imageArticle));
                } else {
                    continue;
                }
                String dateArticle = currentNews.getString("webPublicationDate");
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateArticle);
                String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
                dateArticle = formattedDate;
                String urlArticle = currentNews.getString("webUrl");
                News newsItem = new News(titleArticle, sectionArticle, authorArticle, imageArticle, dateArticle, urlArticle);
                newses.add(newsItem);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "JSON DEU PI", e);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newses;
    }
}