package com.acaziasoft.akane.view.service;

import android.app.IntentService;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;

import com.acaziasoft.akane.Manager.InsertDBManager;
import com.acaziasoft.akane.model.Item;
import com.acaziasoft.akane.presenter.UploadImagePresenter;

import java.util.Date;

public class MyIntentService extends IntentService implements UploadImagePresenter.onUploadImage, UploadImagePresenter.onInsertDB, UploadImagePresenter.onCopyClipboard {
    private InsertDBManager insertDB;
    private String clipBoard = "";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Uri uriImage = Uri.parse(intent.getStringExtra("uriImage"));
        insertDB = new InsertDBManager(this, this, this, this);
        insertDB.Upload(uriImage);
    }

    @Override
    public void preUpload(String s) {
        insertDB.showNotification(s, true, false, 0);
    }

    @Override
    public void processUpload(float percent) {
        insertDB.showNotification("Upload ..." + Math.round(percent * 100) + "%", true, true, Math.round(percent * 100));
    }

    @Override
    public void uploadSuccess(Item item) {
        clipBoard = item.getUrl();
        insertDB.insertDB(item.getName(), new Date(), item.getSize(), item.getUrl());
    }

    @Override
    public void uploadFail(String s) {
        insertDB.showNotification(s, false, false,0);
    }

    @Override
    public void insertSuccess() {
        insertDB.CopyClipboard(clipBoard);
    }

    @Override
    public void insertFail(String s) {
        insertDB.showNotification(s, false, false, 0);
    }

    @Override
    public void onCopyClipboard() {
        insertDB.showNotification("Upload done! Copy to clipboard success!", false, false, 0);
    }
}
