package com.acaziasoft.akane.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.acaziasoft.akane.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements IFragment {

  public static final String TAG = HomeFragment.class.getSimpleName();

  static HomeFragment fragment;

  // @BindView(R.id.btn_local) ImageButton localFileButton;

  public static HomeFragment getInstance() {
    if (fragment != null) {
      return fragment;
    } else {
      return new HomeFragment();
    }
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home, container, false);
    //ButterKnife.bind(this, view);

    return view;
  }

  @Override
  public void onChooseAsset(String type) {
    Log.e(TAG, type);
  }
}
