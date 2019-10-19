package com.example.aruzhan.book;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class BooksAdapter1 extends RecyclerView.Adapter<BooksAdapter1.MyViewHolder> {
    Context context;
    List<JSONObject> books;
    ImageLoader imageLoader;
    RequestQueue requestQueue;
    CustomItemClickListener listener;

    public BooksAdapter1(final Context context, List<JSONObject> books, CustomItemClickListener listener){
        this.context = context;
        this.books = books;
        this.listener = listener;
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
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
        final MyViewHolder mViewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getPosition());
            }
        });
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            holder.titleTextView.setText(books.get(position).getJSONObject("volumeInfo").getString("title"));
            holder.subTitleTextView.setText(books.get(position).getJSONObject("volumeInfo").getString("subtitle"));
            holder.pageCntTextView.setText("Кол. стр.: " + books.get(position).getJSONObject("volumeInfo").getString("pageCount") + "\n"
                    + books.get(position).getJSONObject("volumeInfo").getString("printType"));
            holder.coverImageView.setImageUrl(books.get(position).getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("thumbnail"), imageLoader);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView subTitleTextView;
        public TextView pageCntTextView;
        public NetworkImageView coverImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title);
            subTitleTextView = (TextView) itemView.findViewById(R.id.subtitle);
            pageCntTextView = (TextView) itemView.findViewById(R.id.pageCnt);
            coverImageView = (NetworkImageView) itemView.findViewById(R.id.coverImageView);
        }
    }

    public interface CustomItemClickListener {
        public void onItemClick(View v, int position);
    }
}
