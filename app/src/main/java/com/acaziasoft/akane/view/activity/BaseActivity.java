package com.acaziasoft.akane.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.acaziasoft.akane.R;

import java.util.Stack;

public abstract class BaseActivity extends AppCompatActivity {
    public static final String TAG = BaseActivity.class.getSimpleName();
    public Stack<Fragment> fragments = new Stack<>();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_MENU && "LGE".equalsIgnoreCase(Build.BRAND) || super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && "LGE".equalsIgnoreCase(Build.BRAND)) {
            openOptionsMenu();
            return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public abstract void init();

    public void StackFragmentManager(Fragment fragment) {
        pushFragment(fragment);
    }

    private void pushFragment(Fragment fragment) {
        fragments.push(fragment);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content, fragment, fragment.getClass().getName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }

    private void ReplaceFragment(Fragment fragment) {
        fragments.pop();
        fragments.add(fragment);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, fragment, fragment.getClass().getName())
                .commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }

    @Override
    public void onBackPressed() {
        if(fragments.size() == 1)
            finish();
        super.onBackPressed();
    }
}
