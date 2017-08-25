package com.acaziasoft.akane;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {

  public static final String TAG = HomeFragment.class.getSimpleName();

  static HomeFragment fragment;

  public static HomeFragment newInstance() {
    if (fragment != null) {
      return fragment;
    } else {
      return new HomeFragment();
    }
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_home, container, false);
  }
}
