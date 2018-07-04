package com.example.ramonsl.newguardian;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<String> {

    private TextView mTextMessage;
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private NewsAdapter mAdapter;
    private static String mUrl="http://content.guardianapis.com/search?q=money&order-by=newest&order-date=published&show-fields=headline,thumbnail&show-references=author&show-tags=contributor&page=1&page-size=20&api-key=2d96f27b-6c25-40e7-ac6f-236b586abf0b"
    private static final int NEWS_LOADER_ID = 1;
    private ListView listView;
    private TextView mEmptyStateTextView;
    private View circleProgressBar;
    BottomNavigationView navigation;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_food:
                    mTextMessage.setText(R.string.title_food);
                    mUrl="http://content.guardianapis.com/search?q=food&order-by=newest&order-date=published&show-fields=headline,thumbnail&show-references=author&show-tags=contributor&page=1&page-size=20&api-key=2d96f27b-6c25-40e7-ac6f-236b586abf0b"

                    return true;
                case R.id.navigation_money:
                    mTextMessage.setText(R.string.title_money);
                    mUrl="http://content.guardianapis.com/search?q=money&order-by=newest&order-date=published&show-fields=headline,thumbnail&show-references=author&show-tags=contributor&page=1&page-size=20&api-key=2d96f27b-6c25-40e7-ac6f-236b586abf0b" getSupportLoaderManager().initLoader(0, null, (LoaderManager.LoaderCallbacks<String>)this).forceLoad();

                    return true;
                case R.id.navigation_technology:
                    mTextMessage.setText(R.string.title_technology);

                    mUrl="http://content.guardianapis.com/search?q=technology&order-by=newest&order-date=published&show-fields=headline,thumbnail&show-references=author&show-tags=contributor&page=1&page-size=20&api-key=2d96f27b-6c25-40e7-ac6f-236b586abf0b"

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextMessage =  findViewById(R.id.message);

        getSupportLoaderManager().initLoader(0, null, (LoaderManager.LoaderCallbacks<String>)this).forceLoad();

        navigation =  findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.navigation_food:

                        break;
                    case R.id.navigation_money:
                        fragment = new PlacesFragment();
                        break;
                    case R.id.navigation_technology:
                        fragment = new HistoriFragment();
                        break;

                    default:

                        break;
                }

    }

    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new FetchData(this);
    }

    @Override
    public void onLoadFinished( Loader<String>  loader, String data) {
        mTextMessage.setText(data);
        ArrayList<News> lista=Utils.extractNewsFromJson(data);
        load(lista);
        circleProgressBar.setVisibility(View.GONE);


    }

    @Override
    public void onLoaderReset( Loader<String>  loader) {
    }




    private static class FetchData extends AsyncTaskLoader<String> {

        public FetchData(Context context) {
            super(context);
        }

        @Override
        public String loadInBackground() {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String jsonStr = null;
            String line;
            try {
                URL url = new URL(mUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) return null;

                reader = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = reader.readLine()) != null) buffer.append(line);

                if (buffer.length() == 0) return null;
                jsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e("MainActivity", "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) urlConnection.disconnect();
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("MainActivity", "Error closing stream", e);
                    }
                }
            }

            return jsonStr;
        }

        @Override
        public void deliverResult(String data) {
            super.deliverResult(data);
        }
    }


    private void load(ArrayList<News> newsList){
         navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mTextMessage =  findViewById(R.id.message);
        listView = findViewById(R.id.list);
        mAdapter = new NewsAdapter(this, newsList);
        listView.setAdapter(mAdapter);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyStateTextView);
    }
}
