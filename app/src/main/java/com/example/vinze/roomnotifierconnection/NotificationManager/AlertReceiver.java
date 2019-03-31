package com.example.vinze.roomnotifierconnection.NotificationManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;


public class AlertReceiver extends BroadcastReceiver {

    //private Activity activity = new ReminderActivity();
    //private AlarmManager alarmManager = (AlarmManager)activity.getSystemService(Context.ALARM_SERVICE);

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationHelper notificationHelper = new NotificationHelper(context);
        try{


            NotificationCompat.Builder nb = notificationHelper.getChannel1Notification(intent.getStringExtra("medicationName"),
                    "Bitte denken Sie daran, ihr Medikament einzunehmen!");
            notificationHelper.getManager().notify(1, nb.build());
/*
            PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, intent.getIntExtra("SETCODE", -1), intent,0);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, intent.getIntExtra("TIME", -1)+ TimeUnit.DAYS.toMillis(1), pendingIntent);

            } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,intent.getIntExtra("TIME", -1)+ TimeUnit.DAYS.toMillis(1), pendingIntent);
            }
            else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, intent.getIntExtra("TIME", -1)+ TimeUnit.DAYS.toMillis(1), pendingIntent);
            }*/

        }
        catch (Exception e)
        {

        }

    }
}


