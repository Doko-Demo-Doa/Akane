package com.acaziasoft.akane;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import com.acaziasoft.akane.view.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

  private FragmentManager fragmentManager;

  private TextView mTextMessage;

  private ImageButton localFileButton;

  private ImageButton cameraButton;

  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
      = new BottomNavigationView.OnNavigationItemSelectedListener() {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()) {
        case R.id.navigation_home:
          // mTextMessage.setText(R.string.title_home);
          return true;
        case R.id.navigation_dashboard:
          // mTextMessage.setText(R.string.title_dashboard);
          return true;
        case R.id.navigation_notifications:
          // mTextMessage.setText(R.string.title_notifications);
          return true;
      }
      return false;
    }

  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction()
        .add(R.id.content, HomeFragment.getInstance(), HomeFragment.TAG)
        .commit();

    mTextMessage = (TextView) findViewById(R.id.message);
    mTextMessage.setText("Abra");
    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
  }

}
