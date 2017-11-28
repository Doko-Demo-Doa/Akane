package com.acaziasoft.akane.view.activity;

import android.content.ClipboardManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import com.acaziasoft.akane.Manager.InsertDBManager;
import com.acaziasoft.akane.R;
import com.acaziasoft.akane.presenter.UploadImagePresenter;
import com.acaziasoft.akane.view.fragment.DashboardFragment;
import com.acaziasoft.akane.view.fragment.HomeFragment;
import com.acaziasoft.akane.view.service.MyIntentService;

import java.io.File;
import java.util.Date;

public class MainActivity extends BaseActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    pushFragment(HomeFragment.newInstance());
                    return true;
                case R.id.navigation_dashboard:
                    pushFragment(DashboardFragment.newInstance());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
                Intent intentService = new Intent(this, MyIntentService.class);
                intentService.putExtra("uriImage", imageUri.toString());
                startService(intentService);
                finish();
            }
        } else {
            pushFragment(HomeFragment.newInstance());
        }
    }

    @Override
    public void init() {

    }

}
