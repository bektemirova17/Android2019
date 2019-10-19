package com.example.aruzhan.book;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ResultActivity extends AppCompatActivity{

    private static final String TAG = "Main";

    EditText nameEdt;
    Button searchBtn;
    ProgressBar mProgressBar;
    RecyclerView recyclerView;
    JSONArray books;
    List<JSONObject> books_list;
    ImageView noResult;
    BooksAdapter1 adapter;
    LinearLayoutManager mLinearLayoutManager;
    Spinner byBook, byNew;
    int startIndex = 0, newItems = 15;
    boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    String[] forSpinner1 = {"Все", "Книги", "Журналы"};
    String[] forSpinner2 = {"Актуальные сверху", "Новые сверху"};
    int type1 = 1, type2 = 1;
    boolean isFirst = true;
    String bookName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        nameEdt = findViewById(R.id.bookNameEditText);
        searchBtn = findViewById(R.id.searchButton);
        searchBtn.setOnClickListener(checkBookNameListener);
        recyclerView = findViewById(R.id.resultView);
        recyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        noResult = findViewById(R.id.noResult);
        mProgressBar = findViewById(R.id.progressBar);
        byBook = findViewById(R.id.by_book);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, forSpinner1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        byBook.setAdapter(adapter1);
        byBook.setSelection(0);
        byNew = findViewById(R.id.by_new);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, forSpinner2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        byNew.setAdapter(adapter2);
        byNew.setSelection(0);
        books_list = new ArrayList<>();
        bookName = getIntent().getExtras().get("name").toString();
        onSearchClick(bookName, startIndex, newItems);
        nameEdt.setText(bookName);
        spinnerListener(byBook, 1);
        spinnerListener(byNew, 2);
    }

    public void spinnerListener(Spinner spinner, final int which){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                books_list.clear();
                isFirst = true;
                startIndex = 0;
                if (which == 1){
                    type1 = position + 1;
                }else{
                    type2 = position + 1;
                }
                onSearchClick(bookName, startIndex, newItems);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    View.OnClickListener checkBookNameListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String bookname = nameEdt.getText().toString();
            startIndex = 0;
            if (bookname.equals("")){
                Toast.makeText(ResultActivity.this, "Введите название книги", Toast.LENGTH_SHORT).show();
            }else if (bookname.equals(bookName)){

            }else {
                bookName = bookname;
                books_list.clear();
                isFirst = true;
                onSearchClick(bookName, startIndex, newItems);
            }
        }
    };

    public void onSearchClick(String bookName, int startindex, int limit){
        String url = "https://www.googleapis.com/books/v1/volumes?q="+bookName.replace(" ", "%20");
        switch (type1){
            case 1:
                break;
            case 2:
                url += "&printType=books";
                break;
            case 3:
                url += "&printType=magazines";
                break;
            default:break;
        }
        switch (type2){
            case 1:
                break;
            case 2:
                url += "&orderBy=newest";
                break;
            default:break;
        }
        url += "&startIndex=" + startindex + "&maxResults=" + limit;
        RequestQueue queue = Volley.newRequestQueue(ResultActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    books = response.getJSONArray("items");
                    setAdapter(books);
                    recyclerView.setVisibility((View.VISIBLE));
                    noResult.setVisibility(View.INVISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    recyclerView.setVisibility(View.INVISIBLE);
                    noResult.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage());
            }
        });
        queue.add(request);
    }

    public void setAdapter(JSONArray books) throws JSONException {
        for (int i = 0; i < books.length(); i++){
            books_list.add(books.getJSONObject(i));
        }
        if (isFirst) {
            adapter = new BooksAdapter1(this, books_list, new BooksAdapter1.CustomItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    final Intent intent = new Intent(ResultActivity.this, InfoActivity.class);
                    try {
                        JSONObject book = books_list.get(position);
                        JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                        String title = volumeInfo.getString("title");
                        intent.putExtra("title", title);
                        String author = volumeInfo.getString("authors");
                        intent.putExtra("authors", author);
                        String desc = volumeInfo.getString("description");
                        intent.putExtra("description", desc);
                        String type = volumeInfo.getString("printType");
                        intent.putExtra("type", type);
                        String pageCnt = volumeInfo.getString("pageCount");
                        intent.putExtra("pageCount", pageCnt);

                        JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                        String thumbneil = imageLinks.getString("thumbnail");
                        intent.putExtra("image", thumbneil);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(mLinearLayoutManager);
            recyclerView.addOnScrollListener(recyclerViewOnScrollListener);
            isFirst = false;
        }else{
            adapter.notifyDataSetChanged();
            mProgressBar.setVisibility(View.GONE);
        }
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            currentItems = mLinearLayoutManager.getChildCount();
            totalItems = mLinearLayoutManager.getItemCount();
            scrollOutItems = mLinearLayoutManager.findFirstVisibleItemPosition();
            if (isScrolling &&(currentItems + scrollOutItems >= totalItems - 1)) {
                isScrolling = false;
                startIndex += newItems;
                onSearchClick(bookName, startIndex, newItems);
                mProgressBar.setVisibility(View.VISIBLE);
            }
        }
    };
}
