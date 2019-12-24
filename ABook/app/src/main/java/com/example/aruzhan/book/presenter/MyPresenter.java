package com.example.aruzhan.book.presenter;

import com.example.aruzhan.book.model.Model;
import com.example.aruzhan.book.view.DataView;

public class MyPresenter implements BasePresenter<DataView> {

    DataView view;

    public MyPresenter() {
    }

    @Override
    public void attachView(DataView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public boolean isViewAttached() {
        return this.view!=null;
    }

    public void clickedButton() {
        view.showData(new Model("Aru", "Female", 21));
    }
}
