package com.payrespect.alim;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String CHANNEL_ID = "main";
    private TimePicker timePicker;
    private AlarmManager alarmManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button setbtn = findViewById(R.id.setbtn);
        Button cancelbtn = findViewById(R.id.cancelbtn);
        timePicker = findViewById(R.id.time);
        setbtn.setOnClickListener(this);
        cancelbtn.setOnClickListener(this);
        alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Normal";
            String description = "Normal Notification";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            /*AudioAttributes.Builder audiobuilder = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC);
            channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM), audiobuilder.build());*/
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, Alarm.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this,0,intent,0);
        switch(v.getId()){
            case R.id.setbtn:
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.getHour());
                calendar.set(Calendar.MINUTE,timePicker.getMinute());
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pIntent);
                Toast.makeText(getApplicationContext(),getString(R.string.whenSet),Toast.LENGTH_SHORT).show();
                break;
            case R.id.cancelbtn:
                alarmManager.cancel(pIntent);
                Toast.makeText(getApplicationContext(),getString(R.string.whenUnset),Toast.LENGTH_SHORT).show();
                break;
        }
    }
}