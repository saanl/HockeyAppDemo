package com.example.hockeyappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.system.Os;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    TextView tx;
    TextView my_app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx = findViewById(R.id.mytx);
        my_app = findViewById(R.id.my_app);
        my_app.setText("build.config"+ BuildConfig.VERSION_CODE+" "+BuildConfig.VERSION_NAME);
        PackageInfo packageInfo = getName();
        tx.setText("package info"+packageInfo.getLongVersionCode()+" "+packageInfo.versionName);
        initHockeyApp();
      // initAppcenter();
    }

    public PackageInfo getName(){
        try {
            PackageInfo packageInfo = this.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0);
            return packageInfo;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }


    public void click4(View view) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = getApplicationContext().getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);

        Toast.makeText(this, "3333333", Toast.LENGTH_SHORT).show();
        tx.setText("version code: "+packageInfo.versionCode+" version name: "+packageInfo.versionName);
    }

    private void initHockeyApp(){

        UpdateManager.register(this, "32192b0788e140bcbfb91f31a898ad78", new UpdateManagerListener() {
            @Override
            public void onUpdateAvailable(JSONArray data, String url) {
                super.onUpdateAvailable(data, url);
                Log.e("JSON DATA: ",data.toString());
            }
        });

        CrashManager.register(this, "32192b0788e140bcbfb91f31a898ad78", new CrashManagerListener() {
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
//        Distribute.setListener(new DistributeListener() {
//            @Override
//            public boolean onReleaseAvailable(Activity activity, ReleaseDetails releaseDetails) {
//
//                Log.e("JSON DATA: ","version: "+ releaseDetails.getVersion());
//
//                return false;
//            }
//        });
//        Crashes.setListener(new CrashesListener() {
//            @Override
//            public boolean shouldProcess(ErrorReport report) {
//                return false;
//            }
//
//            @Override
//            public boolean shouldAwaitUserConfirmation() {
//                return false;
//            }
//
//            @Override
//            public Iterable<ErrorAttachmentLog> getErrorAttachments(ErrorReport report) {
//
//                ErrorAttachmentLog log = new ErrorAttachmentLog();
//
//                log.setContentType("Test crash attachment");
//                log.setErrorId(UUID.randomUUID());
//                log.setData("Hello World".getBytes());
//                log.setFileName("myattachment");
//                log.setId(UUID.randomUUID());
//                List<ErrorAttachmentLog> logs = new ArrayList<ErrorAttachmentLog>();
//                logs.add(log);
//
//                return logs;
//            }
//
//            @Override
//            public void onBeforeSending(ErrorReport report) {
//
//            }
//
//            @Override
//            public void onSendingFailed(ErrorReport report, Exception e) {
//                    Log.e("onSendingFailed"," ");
//            }
//
//            @Override
//            public void onSendingSucceeded(ErrorReport report) {
//                Log.e("onSendingSucceeded"," ");
//            }
//        });
        //77644f0a-6455-4454-bc43-ea9cce4de1e5 68ab6b39-7bda-406d-ba86-93547a642b5e
        AppCenter.start(getApplication(),"32192b07-88e1-40bc-bfb9-1f31a898ad78", Crashes.class, Analytics.class,Distribute.class);
        //Distribute.setEnabled(true);
        //AppCenter.setUserId();
        Analytics.trackEvent("SDK INIT");
        AppCenterTracking.startUsage(this);
        tx.setText("distribution: "+Distribute.isEnabled().get()+"");

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
