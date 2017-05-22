package com.hxlxz.hxl.code7_camera;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final int TAKEPHOTO = 1;
    private Uri imageURI;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("请稍候");
        progressDialog.setMessage("正在上传...");
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
    }

    public void TakePhoto(View v) {
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists())
                outputImage.delete();
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageURI = Uri.fromFile(outputImage);

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
        startActivityForResult(intent, TAKEPHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKEPHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageURI));
                        ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    class UploadTask extends AsyncTask<String, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(String... p) {
            final String BOUNDARY = "----WebKitFormBoundaryT1HoybnYeFOGFlBR";
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("--" + BOUNDARY + "\r\n");
            stringBuffer.append("Content-Disposition: form-data; name=\"pic\"; filename=\"pic.jpg\"" + "\r\n");
            stringBuffer.append("Content-Type: image/jpeg" + "\r\n");
            stringBuffer.append("\r\n");

            try {
                byte[] HeaderInfo = stringBuffer.toString().getBytes("UTF-8");
                byte[] EndInfo = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");

                File Image = new File(getExternalCacheDir(), "output_image.jpg");

                HttpURLConnection conn = (HttpURLConnection) (new URL("http://test.hxlxz.com/upload.php")).openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type",
                        "multipart/form-data; boundary=" + BOUNDARY);
                conn.setRequestProperty("Content-Length", String.valueOf(HeaderInfo.length + Image.length() + EndInfo.length));
                conn.setDoOutput(true);

                OutputStream out = conn.getOutputStream();
                InputStream in = new FileInputStream(Image);
                out.write(HeaderInfo);

                Long ImgLen = Image.length();
                Long DoneLen = 0l;
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) != -1) {
                    out.write(buf, 0, len);
                    DoneLen += len;
                    Long t = (DoneLen * 100 / ImgLen);
                    publishProgress(t.intValue());
                }

                out.write(EndInfo);
                in.close();
                out.close();
                return conn.getResponseCode();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return -1;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, "UploadResult:" + result, Toast.LENGTH_SHORT).show();
        }
    }

    public void Upload(View v) {
        new UploadTask().execute();
    }
}
