package com.acaziasoft.akane.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.acaziasoft.akane.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.Arrays;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CameraActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

  private int mCameraId = -1;
  ZXingScannerView scannerView;


  @Override
  protected void onCreate(@Nullable Bundle state) {
    super.onCreate(state);
    setContentView(R.layout.activity_camera);

    List<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.CODABAR,
        BarcodeFormat.QR_CODE);

    scannerView = new ZXingScannerView(this);
    scannerView.setAspectTolerance(0.5f);
    scannerView.setFormats(formats);
    scannerView.startCamera();

    ViewGroup contentFrame = findViewById(R.id.content_frame);
    contentFrame.addView(scannerView);
  }

  @Override
  protected void onResume() {
    super.onResume();
    //scannerView.startCamera();
  }

  @Override
  public void handleResult(Result result) {
    Log.e("Test", result.toString());
    Toast.makeText(this, result.getText(), Toast.LENGTH_SHORT).show();
  }
}
