package com.example.aruzhan.book;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class BooksAdapter extends BaseAdapter{

    Context context;
    List<JSONObject> books;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    RequestQueue requestQueue;

    public BooksAdapter(final Context context, List<JSONObject> books){
        this.context = context;
        this.books = books;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        requestQueue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<>(20);
            @Override
            public Bitmap getBitmap(String url) {
                return  cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.row_item, null);
            viewHolder = new ViewHolder();
            viewHolder.titleTextView = convertView.findViewById(R.id.title);
            viewHolder.subTitleTextView = convertView.findViewById(R.id.subtitle);
            viewHolder.pageCntTextView = convertView.findViewById(R.id.pageCnt);
            convertView.setTag(viewHolder);
            viewHolder.coverImageView = convertView.findViewById(R.id.coverImageView);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            JSONObject book = books.get(position);
            JSONObject volumeInfo = book.getJSONObject("volumeInfo");

            String title = volumeInfo.getString("title");
            String subtitle = volumeInfo.getString("subtitle");
            String pageCnt = volumeInfo.getString("pageCount");

            viewHolder.titleTextView.setText(title);
            viewHolder.subTitleTextView.setText(subtitle);
            viewHolder.pageCntTextView.setText("Кол. стр.: "+pageCnt);

            JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
            String thumbnailLink = imageLinks.getString("thumbnail");
            viewHolder.coverImageView.setImageUrl(thumbnailLink, imageLoader);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return convertView;
    }

    private class ViewHolder {
        TextView titleTextView;
        TextView subTitleTextView;
        TextView pageCntTextView;
        public NetworkImageView coverImageView;
    }
}
