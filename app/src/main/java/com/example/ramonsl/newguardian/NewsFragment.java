package com.example.ramonsl.newguardian;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<String> {
    private static final String LOG_TAG = Utils.class.getSimpleName();

    ListView listView;
    private NewsAdapter mAdapter;
    private View circleProgressBar;



    public NewsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_news, container, false);
        listView = root.findViewById(R.id.listaDeNoticias);
        circleProgressBar = root.findViewById(R.id.loading_spinner);
        circleProgressBar.setVisibility(View.VISIBLE);
        getActivity().getSupportLoaderManager().initLoader(0, null, (LoaderManager.LoaderCallbacks<String>) this).forceLoad();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News currentNews = mAdapter.getItem(i);
                Uri newsUri = Uri.parse(currentNews != null ? currentNews.getmNewsUrl() : null);
                Intent intent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(intent);

            }
        });

        return root;
    }

    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new FetchData(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        ArrayList<News> lista = Utils.extractNewsFromJson(data);
        load(lista);
        circleProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
    }

    private void load(ArrayList<News> newsList) {
        mAdapter = new NewsAdapter(getActivity(), newsList);
        listView.setAdapter(mAdapter);
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
                URL url = new URL(MainActivity.mUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) return null;

                reader = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = reader.readLine()) != null) buffer.append(line);

                if (buffer.length() == 0) return null;
                jsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "Erro na no Stream");

                return null;
            } finally {
                if (urlConnection != null) urlConnection.disconnect();
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {

                        Log.e(LOG_TAG, "Erro na no Stream");

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
}
