package com.hxlxz.hxl.homework2_service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }
    private DownloadBinder binder = new DownloadBinder();

    class DownloadBinder extends Binder {
        public void startDownload() {
            Log.d("MyService", "startDownload");
        }

        public int getProgress() {
            Log.d("MyService", "getProgress");
            return 0;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("MyService", "onBind");
        return binder;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d("MyService", "OnCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startId){
        Log.d("MyService", "onStartCommand");
        return super.onStartCommand(intent,flag,startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("MyService", "onDestroy");
    }
}
