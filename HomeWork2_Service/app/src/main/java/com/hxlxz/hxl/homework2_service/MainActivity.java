package com.hxlxz.hxl.homework2_service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private ServiceConnection connection = new ServiceConnection() {
        private MyService.DownloadBinder downloadBinder;

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (MyService.DownloadBinder) service;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void StartService(View v) {
        Intent startIntent = new Intent(this, MyService.class);
        startService(startIntent);
    }

    public void StopService(View v) {
        Intent stopIntent = new Intent(this, MyService.class);
        stopService(stopIntent);
    }

    public void BindService(View v) {
        Intent bindIntent = new Intent(this, MyService.class);
        bindService(bindIntent, connection, BIND_AUTO_CREATE);
    }

    public void UnbindService(View v) {
        unbindService(connection);
    }

}
