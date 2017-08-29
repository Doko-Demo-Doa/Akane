package com.acaziasoft.akane.presenter;

public interface Presenter<V> {
  void attachView(V view);

  void detachView();
}
