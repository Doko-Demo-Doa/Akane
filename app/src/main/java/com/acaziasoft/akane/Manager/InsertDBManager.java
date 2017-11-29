package com.acaziasoft.akane.Manager;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.acaziasoft.akane.R;
import com.acaziasoft.akane.model.Item;
import com.acaziasoft.akane.presenter.UploadImagePresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import io.github.lizhangqu.coreprogress.ProgressHelper;
import io.github.lizhangqu.coreprogress.ProgressListener;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class InsertDBManager {
    private Context context;
    private NotificationManager mNotificationManager;
    private UploadImagePresenter.onUploadImage onUploadImage;
    private UploadImagePresenter.onInsertDB onInsertDB;
    private UploadImagePresenter.onCopyClipboard onCopyClipboard;
    private String url = "https://doko.moe/upload.php";

    public InsertDBManager(UploadImagePresenter.onUploadImage onUploadImage, UploadImagePresenter.onInsertDB onInsertDB,
                           UploadImagePresenter.onCopyClipboard onCopyClipboard, Context context) {
        this.onUploadImage = onUploadImage;
        this.onInsertDB = onInsertDB;
        this.onCopyClipboard = onCopyClipboard;
        this.context = context;
    }

    public void insertDB(String name, Date date, Double size, String url) {
        try {
            Item item = new Item(name, date, size, url);
            item.save();
            onInsertDB.insertSuccess();
        } catch (Exception e) {
            onInsertDB.insertFail(e.getLocalizedMessage());
        }
    }

    public void Upload(Uri uri) {
        onUploadImage.preUpload("Uploading ...");
        File file = new File(getRealPathFromURI(uri));
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("files[]", file.getName(), RequestBody.create(MediaType.parse("image/png"), file))
                .build();
        requestBody = ProgressHelper.withProgress(requestBody, new ProgressListener() {
            @Override
            public void onProgressChanged(long numBytes, long totalBytes, float percent, float speed) {
                onUploadImage.processUpload(percent);
            }
        });
        Request request = new Request.Builder().url(url)
                .post(requestBody).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                onUploadImage.uploadFail("Not call to api.");
                CloseResponse(response);
            } else {
                String resStr = response.body().string().toString();
                JSONObject json = new JSONObject(resStr);
                boolean status = (boolean) json.get("success");
                if (status) {
                    JSONArray Jarray = json.getJSONArray("files");
                    onUploadImage.uploadSuccess(ConvertToObject(Jarray.getJSONObject(0)));
                    CloseResponse(response);
                } else {
                    CloseResponse(response);
                    onUploadImage.uploadFail("Not call to api.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            onUploadImage.uploadFail(e.getLocalizedMessage());
        } catch (JSONException e) {
            e.printStackTrace();
            onUploadImage.uploadFail(e.getLocalizedMessage());
        }

    }

    @Nullable
    private Item ConvertToObject(JSONObject jsonObject) {
        Item item = new Item();
        try {
            item.setName(jsonObject.getString("name"));
            item.setSize(jsonObject.getDouble("size"));
            item.setUrl(jsonObject.getString("url"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return item;
    }

    private void CloseResponse(Response response) {
        response.close();
    }

    public void CopyClipboard(String s) {
        ((ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE)).setText(s);
        onCopyClipboard.onCopyClipboard();
    }

    private String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null,
                    null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void showNotification(String s, boolean onGoing, boolean show_process, int percent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder
                .setContentTitle("Message")
                .setContentText(s)
                .setSmallIcon(R.mipmap.icon)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setOngoing(onGoing);
        if (show_process)
            builder.setProgress(100, percent, false);
        if (mNotificationManager == null)
            mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, builder.build());
    }

    public boolean checkStatusActivity(){
        ActivityManager activityManager = (ActivityManager) context.getSystemService (Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(Integer.MAX_VALUE);
        if(tasksInfo.size() > 0)
            return true;
        return false;
    }
}
