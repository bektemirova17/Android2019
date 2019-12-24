package com.example.aruzhan.book.presenter;
import com.example.aruzhan.book.view.BaseView;
public interface BasePresenter<V extends BaseView> {
    void attachView(V view);
    void detachView();
    boolean isViewAttached();
}
