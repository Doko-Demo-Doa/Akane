package com.acaziasoft.akane.presenter;

public interface IPresenter<V> {
  void attachView(V view);

  void detachView();
}
