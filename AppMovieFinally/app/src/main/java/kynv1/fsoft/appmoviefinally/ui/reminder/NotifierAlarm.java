package kynv1.fsoft.appmoviefinally.ui.reminder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.Date;

import kynv1.fsoft.appmoviefinally.R;
import kynv1.fsoft.appmoviefinally.database.MovieDatabase;

import kynv1.fsoft.appmoviefinally.model.reminder.Result;
import kynv1.fsoft.appmoviefinally.ui.movieDetail.MovieDetailFragment;

public class NotifierAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Result result = new Result();
        result.setOriginal_title(intent.getStringExtra("Message"));
        result.setDate(new Date(intent.getStringExtra("RemindDate")));
        result.setId(intent.getIntExtra("id",0));

        MovieDatabase.getInstance(context).reminderDao().delete(result);
        Uri alarmsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        Intent intent1 = new Intent(context, MovieDetailFragment.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("my_channel_01","hello", NotificationManager.IMPORTANCE_HIGH);
        }

        Notification notification = builder.setContentTitle("Reminder")
                .setContentText(intent.getStringExtra("Message")).setAutoCancel(true)
                .setSound(alarmsound).setSmallIcon(R.mipmap.ic_launcher_round)
                .setChannelId("my_channel_01")
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(1, notification);
    }
}
