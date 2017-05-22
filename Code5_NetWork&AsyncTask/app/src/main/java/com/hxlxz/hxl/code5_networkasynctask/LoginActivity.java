package com.hxlxz.hxl.code5_networkasynctask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    String urlstr;

    public void OnButtonLoginPress(View v) {
        String username = ((EditText) findViewById(R.id.editTextUsername)).getText().toString();
        String password = ((EditText) findViewById(R.id.editTextPassWord)).getText().toString();
        urlstr = "http://test.hxlxz.com/login.php?username=" + username + "&password=" + password;
        new Thread() {
            @Override
            public void run() {
                char temp[] = "false".toCharArray();
                try {
                    URL url = new URL(urlstr);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    InputStreamReader in = new InputStreamReader(conn.getInputStream());
                    in.read(temp);
                    in.close();
                    conn.disconnect();
                } catch (IOException e) {
                    temp = "error".toCharArray();
                }
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("result", new String(temp));
                msg.setData(data);
                handler.sendMessage(msg);
            }
        }.start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            String result = data.getString("result");
            if (result.substring(0, 4).equals("true")) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                String str = "密码错误";
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                AlertDialog alertDialog = builder.create();
                alertDialog.setMessage(str);
                alertDialog.show();
            }
        }
    };
}
