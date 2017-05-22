package com.hxlxz.hxl.code6_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDatabaseHelper(this, "Student.db", null, 1);
    }

    public class MyDatabaseHelper extends SQLiteOpenHelper {
        public static final String SQL = "CREATE table Student ("
                + "StudentID integer, "
                + "Name text, "
                + "Class text)";
        private Context context;

        //, String name, String StudentID, String StudentClass
        public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL);
            Toast.makeText(context, "Created", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public void AddStudent(View v) {
        ContentValues values = new ContentValues();
        values.put("StudentID", ((EditText) findViewById(R.id.StudentID)).getText().toString());
        values.put("Name", ((EditText) findViewById(R.id.Name)).getText().toString());
        values.put("Class", ((EditText) findViewById(R.id.StudentClass)).getText().toString());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert("Student", null, values);
        ((EditText) findViewById(R.id.StudentID)).setText("");
        ((EditText) findViewById(R.id.Name)).setText("");
        ((EditText) findViewById(R.id.StudentClass)).setText("");
        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
    }

    public void ShowAll(View v) {
        Intent intent = new Intent(this, ShowActivity.class);
        startActivity(intent);
    }
}
