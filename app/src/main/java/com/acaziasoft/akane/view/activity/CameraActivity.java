package com.acaziasoft.akane.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.acaziasoft.akane.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CameraActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private int mCameraId = -1;
    ZXingScannerView scannerView;


    @Override
    protected void onCreate(@Nullable Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_camera);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void handleResult(Result result) {
        Log.e("Test", result.toString());
        Toast.makeText(this, result.getText(), Toast.LENGTH_SHORT).show();
    }
}
