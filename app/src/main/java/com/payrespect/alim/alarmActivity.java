package com.payrespect.alim;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class alarmActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("its me", "alarm Activity has started!!");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        setContentView(R.layout.activity_alarm);
        //this is for test
    }
    public void goToNext(View v){

        try {
            Intent intent = getPackageManager().getLaunchIntentForPackage("kr.go.eduro.hcs");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } catch(ActivityNotFoundException e){
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://hcs.eduro.go.kr"));
                startActivity(intent);
                finish();
            }catch(Exception e2){
                e2.printStackTrace();
                Toast.makeText(getApplicationContext(),"오류 : 앱이 설치되지 않았거나 실행할 수 없음.",Toast.LENGTH_SHORT).show();
            }
        }
    }
}