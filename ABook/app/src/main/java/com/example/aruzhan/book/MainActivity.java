package com.example.aruzhan.book;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aruzhan.book.model.Model;
import com.example.aruzhan.book.presenter.MyPresenter;
import com.example.aruzhan.book.view.DataView;

public class MainActivity extends AppCompatActivity implements DataView<Model>{

    EditText bookNameEdt;
    Button searchBtn;
    private static final String TAG = MainActivity.class.getSimpleName();

    private MyPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MyPresenter();
        bookNameEdt = findViewById(R.id.bookNameEditText);
        searchBtn = findViewById(R.id.searchButton);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("name", bookNameEdt.getText().toString());
                startActivity(intent);
            }
        });

    }
    public void ChangeFragment(View view) {
        Fragment fragment;

        if (view == findViewById(R.id.button)) {
            fragment = new FirstFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_place, fragment);
            ft.commit();
        }
        if (view == findViewById(R.id.button2)) {
            fragment = new SecondFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_place, fragment);
            ft.commit();
        }
    }
    void clicked(){
        loadPresenter();
    }

    private void loadPresenter() {
        presenter.attachView(this);

        if(presenter.isViewAttached())
            presenter.clickedButton();
    }

    @Override
    public void showData(Model data) {
        Toast.makeText(this, "Hello "+data.getNama(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void isError(String message) {
        Log.e(TAG+".error", message);

    }
}
