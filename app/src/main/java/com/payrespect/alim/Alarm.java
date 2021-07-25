package com.payrespect.alim;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.payrespect.alim.alarmActivity;

import java.io.IOException;

public class Alarm extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("alarm","alarm");
        Intent intent1 = new Intent(context, alarmActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        /*PendingIntent pi = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_ONE_SHOT);
        try{
            pi.send();
        }catch(Exception e){
            e.printStackTrace();
        }
*/

        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, 0,
                intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        try {

            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            PlaySound(context,notification);

        } catch (Exception e) {
            e.printStackTrace();
        }

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, "main")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_ALARM)
                        .setContentTitle("자가진단 알림")
                        .setContentText("자가진단 알림임.")
                        .setContentIntent(fullScreenPendingIntent)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setSound(null)
                        .setFullScreenIntent(fullScreenPendingIntent, true);
        Notification mNotification = notificationBuilder.build();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1,mNotification);

    }
    private void PlaySound(Context context,Uri alert){
        MediaPlayer mPlayer;
        try{
            mPlayer=new MediaPlayer();
            mPlayer.setDataSource(context,alert);
            final AudioManager am=(AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if(am.getStreamVolume(AudioManager.STREAM_ALARM)!=0)
            {
                mPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mPlayer.prepare();
                mPlayer.setLooping(true);
                mPlayer.start();
            }

        }catch(IOException e)
        {
            Log.i("AlarmReceiver", "no audio file");
        }
    }
}
