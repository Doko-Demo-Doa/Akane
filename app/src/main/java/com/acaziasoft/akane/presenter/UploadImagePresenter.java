package com.acaziasoft.akane.presenter;

import com.acaziasoft.akane.model.Item;

/**
 * Created by duyth on 11/23/2017.
 */

public class UploadImagePresenter {
    public interface onUploadImage{
        void preUpload(String s);
        void processUpload(float percent);
        void uploadSuccess(Item item);
        void uploadFail(String s);
    }

    public interface onInsertDB{
        void insertSuccess();
        void insertFail(String s);
    }

    public interface onCopyClipboard{
        void onCopyClipboard();
    }
}
