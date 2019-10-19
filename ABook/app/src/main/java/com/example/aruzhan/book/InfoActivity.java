package com.example.aruzhan.book;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LruCache;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

public class InfoActivity extends AppCompatActivity {

    TextView title, author, desc;
    NetworkImageView coverImage;
    ImageLoader mImageLoader;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        title = findViewById(R.id.titleInfo);
        author = findViewById(R.id.authorInfo);
        desc = findViewById(R.id.fullInfo);
        coverImage = findViewById(R.id.coverImageView);

        Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));
        author.setText(intent.getStringExtra("authors") + "\n" + intent.getStringExtra("type"));
        desc.setText(intent.getStringExtra("description"));
        String img = intent.getStringExtra("image");

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        mImageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<>(20);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
        coverImage.setImageUrl(img, mImageLoader);
    }
}
