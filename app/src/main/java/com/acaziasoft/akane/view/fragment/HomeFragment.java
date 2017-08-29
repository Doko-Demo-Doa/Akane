package com.acaziasoft.akane.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acaziasoft.akane.R;

public class HomeFragment extends Fragment implements IFragment {

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

  @Override
  public void onChooseAsset(String type) {
    Log.e(TAG, type);
  }
}
