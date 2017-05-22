package com.hxlxz.hxl.code5_networkasynctask;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }

    private ImageView imageView;
    private ProgressDialog progressDialog;

    public void OnButtonPress(View v) {
        String url = ((EditText) findViewById(R.id.editText)).getText().toString();
        imageView = (ImageView) findViewById(R.id.imageView);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("提示信息");
        progressDialog.setMessage("正在下载，请稍后...");
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        new mytask().execute(url);
    }

    public class mytask extends AsyncTask<String, Integer, Bitmap> {
        @Override
        public void onPreExecute() {
            progressDialog.show();
        }

        @Override
        public Bitmap doInBackground(String... url) {
            Bitmap bitmap = null;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            InputStream inputStream = null;
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url[0]);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    inputStream = httpResponse.getEntity().getContent();
                    long file_length = httpResponse.getEntity().getContentLength();
                    int len = 0;
                    byte[] data = new byte[128];
                    int total_length = 0;
                    while ((len = inputStream.read(data)) != -1) {
                        total_length += len;
                        int value = (int) ((total_length / (float) file_length) * 100);
                        publishProgress(value);
                        outputStream.write(data, 0, data.length);
                    }
                    byte[] result = outputStream.toByteArray();
                    bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
                    inputStream.close();
                }
            } catch (Exception e) {

            }
            return bitmap;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            progressDialog.dismiss();
            imageView.setImageBitmap(result);
        }
    }
}