package com.acaziasoft.akane;

import android.app.Application;

import com.orm.SugarContext;

public class AkaneApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }
}
