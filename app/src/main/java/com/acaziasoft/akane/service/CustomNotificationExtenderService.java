package com.acaziasoft.akane.service;

import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationDisplayedResult;
import com.onesignal.OSNotificationReceivedResult;

import java.math.BigInteger;

public class CustomNotificationExtenderService extends NotificationExtenderService {
  @Override
  protected boolean onNotificationProcessing(OSNotificationReceivedResult notification) {
    final int smallIconId = getResources().getIdentifier("right_icon", "id", android.R.class.getPackage().getName());

    OverrideSettings overrideSettings = new OverrideSettings();
    overrideSettings.extender = new NotificationCompat.Extender() {
      @Override
      public NotificationCompat.Builder extend(NotificationCompat.Builder builder) {
        if (smallIconId != 0) {
          //builder.getContentView().setViewVisibility(smallIconId, View.INVISIBLE);
          //builder.getBigContentView().setViewVisibility(smallIconId, View.INVISIBLE);
        }

        return builder
            .setColor(new BigInteger("FF00FF00", 16).intValue())
            .setSmallIcon(0);
      }
    };

    //OSNotificationDisplayedResult displayedResult = displayNotification(overrideSettings);
    //Log.d("OneSignalExample", "Notification displayed with id: " + displayedResult.androidNotificationId);

    return false;
  }
}
