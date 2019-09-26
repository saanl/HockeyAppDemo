package com.example.hockeyappdemo;

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
import net.hockeyapp.android.Tracking;
import net.hockeyapp.android.metrics.MetricsManager;

import java.text.SimpleDateFormat;
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
        AppCenter.start(getApplication(),"839abbd6-ee35-4d0a-8635-62ff81fb4fd6", Crashes.class, Analytics.class);
        Analytics.trackEvent("SDK INIT");
        AppCenterTracking.startUsage(this);
    }

    public void click(View view){


        myerror();

    }

    public void click2(View view){

        Intent intent = new Intent(this,Main2Activity.class);

        //startActivityForResult(intent,0);

        startActivity(intent);
    }

    private void myerror(){
        MyError.generateError();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppCenterTracking.stopUsage(this);
        Log.e("Test Tacking: ", AppCenterTracking.getUsageTime(getApplicationContext())+"" );
    }


}
