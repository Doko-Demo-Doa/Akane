package com.acaziasoft.akane.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.acaziasoft.akane.R;
import com.acaziasoft.akane.view.fragment.HomeFragment;
import com.acaziasoft.akane.view.service.MyIntentService;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            StackFragmentManager(HomeFragment.newInstance());
        }
    }

    @Override
    public void init() {

    }

}
