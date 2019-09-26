package com.example.hockeyappdemo;

import android.util.Log;

import com.microsoft.appcenter.analytics.Analytics;

import java.util.Date;
import java.util.Random;

public class MyError {

    public static void generateError(){
        String s = null;
        String[] arr = {"",""};
        int i = 0;

        Random r = new Random();

        Analytics.trackEvent("CRASH HAPPEN");


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

}
