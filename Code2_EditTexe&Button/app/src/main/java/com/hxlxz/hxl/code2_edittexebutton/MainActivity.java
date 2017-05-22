package com.hxlxz.hxl.code2_edittexebutton;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OnAnNiuPress(View v) {
        String str = "2014124080 何相龙  " + ((EditText) findViewById(R.id.ShuRuKuang)).getText().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = builder.create();
        alertDialog.setMessage(str);
        alertDialog.show();
    }
}
