package com.example.ramonsl.newguardian;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {

    private static final String LOG_TAG = NewsAdapter.class.getSimpleName();

    public NewsAdapter(Activity context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        final News currentNews = getItem(position);

        TextView txtTitle = listItemView.findViewById(R.id.txtTitle);
        TextView txtSection = listItemView.findViewById(R.id.txtSection);
        ImageView ImvThumb = listItemView.findViewById(R.id.imvThumb);
        TextView txtDate = listItemView.findViewById(R.id.txtDate);
        assert currentNews != null;
        txtTitle.setText(currentNews.getmTitle());
        txtSection.setText(currentNews.getmSection());

        Picasso.get()
                .load(currentNews.getmThumbUrl())
                .placeholder(R.drawable.baseline_photo_size_select_actual_24)
                .error(R.drawable.baseline_error_24)
                .into(ImvThumb);

        txtDate.setText(String.valueOf(currentNews.getmDate()));
        Log.i(LOG_TAG, "BOMBOU A LISTVIEW");
        return listItemView;

    }
}
