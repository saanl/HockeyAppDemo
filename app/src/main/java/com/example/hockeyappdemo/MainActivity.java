package com.example.hockeyappdemo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import com.microsoft.appcenter.crashes.CrashesListener;
import com.microsoft.appcenter.crashes.ingestion.models.ErrorAttachmentLog;
import com.microsoft.appcenter.crashes.model.ErrorReport;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.CrashManagerListener;

import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       //initHockeyApp();
        initAppcenter();
    }


    private void initHockeyApp(){
        CrashManager.register(this, "e2c15e61b7f3431189155a1a7fb955f5", new CrashManagerListener() {
            @Override
            public boolean shouldAutoUploadCrashes() {
                return true;
            }
        });
    }

    private void initAppcenter(){
        AppCenter.setLogLevel(Log.VERBOSE);
        AppCenter.start(getApplication(),"68ab6b39-7bda-406d-ba86-93547a642b5e", Crashes.class, Analytics.class);
        Crashes.setListener(new CrashesListener() {
            @Override
            public boolean shouldProcess(ErrorReport report) {
                return false;
            }

            @Override
            public boolean shouldAwaitUserConfirmation() {
                return false;
            }

            @Override
            public Iterable<ErrorAttachmentLog> getErrorAttachments(ErrorReport report) {
                return null;
            }

            @Override
            public void onBeforeSending(ErrorReport report) {
                Log.e("MYAPPCENTER",report.getDevice().getOemName());
                Log.e("MYAPPCENTER",report.getDevice().getModel());
            }

            @Override
            public void onSendingFailed(ErrorReport report, Exception e) {

            }

            @Override
            public void onSendingSucceeded(ErrorReport report) {
                Log.e("MYAPPCENTER",report.getDevice().getOemName());
                Log.e("MYAPPCENTER",report.getDevice().getModel());
            }
        });
    }

    public void click(View view){

        String s = null;
        String[] arr = {"",""};
        int i = 0;

        Random r = new Random();

        if(r.nextInt(100) % 2 == 0){
            Log.e("AAA",s.length()+"");
        }else if(r.nextInt(100) % 3 == 0){
            Log.e("AAA",arr[2]);
        }else if(r.nextInt(100) % 5 == 0){
            Log.e("AAA",999/i+"");
        }else if(r.nextInt(100) % 7 == 0){
            throw new RuntimeException("RuntimeException For Test:"+ new Date());
        }

    }

    public void click2(View view){

        Intent intent = new Intent(this,Main2Activity.class);

        //startActivityForResult(intent,0);

        startActivity(intent);
    }

}
