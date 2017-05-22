package com.hxlxz.hxl.code3_layout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String str[] = {"8", "0", "何", "相", "龙"};
        int i = 0;
        while (i < 5) {
            i++;
            Button button = new Button(this);
            button.setId(i);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(200, 200);
            if (i != 1) {
                layoutParams.addRule(RelativeLayout.RIGHT_OF, i - 1);
                layoutParams.addRule(RelativeLayout.BELOW, i - 1);
            }
            button.setLayoutParams(layoutParams);
            button.setText(str[i - 1]);
            ((RelativeLayout) findViewById(R.id.RelativeLayout)).addView(button);
        }
    }
}
