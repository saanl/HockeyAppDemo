package com.example.hockeyappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import com.microsoft.appcenter.distribute.Distribute;
import com.microsoft.appcenter.distribute.DistributeListener;
import com.microsoft.appcenter.distribute.ReleaseDetails;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.CrashManagerListener;
import net.hockeyapp.android.ExceptionHandler;
import net.hockeyapp.android.Tracking;
import net.hockeyapp.android.UpdateManager;
import net.hockeyapp.android.UpdateManagerListener;
import net.hockeyapp.android.metrics.MetricsManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initHockeyApp();
        //initAppcenter();
    }


    private void initHockeyApp(){

        UpdateManager.register(this, "6f258871556141b2bc60cec4ef5efaac", new UpdateManagerListener() {
            @Override
            public void onUpdateAvailable(JSONArray data, String url) {
                super.onUpdateAvailable(data, url);
                for (int i = 0; i < data.length(); i++) {
                    try {
                        Log.e("item: ",data.getJSONObject(i).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        CrashManager.register(this, "6f258871556141b2bc60cec4ef5efaac", new CrashManagerListener() {
            @Override
            public boolean shouldAutoUploadCrashes() {
                return true;
            }
        });



    }

    public void click3(View view){
        ExceptionHandler.saveException(new RuntimeException("ExceptionHandler For Test"), Thread.currentThread(), new CrashManagerListener() {
            @Override
            public boolean shouldAutoUploadCrashes() {
                return true;
            }

            @Override
            public void onCrashesSent() {
                super.onCrashesSent();
                Log.e("ExceptionHandler","自定义crash 发送");
            }
        });
    }

    private void initAppcenter(){
        AppCenter.setLogLevel(Log.VERBOSE);
        Distribute.setEnabled(true);
        AppCenter.start(getApplication(),"6f258871-5561-41b2-bc60-cec4ef5efaac", Crashes.class, Analytics.class);//Distribute.class
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
