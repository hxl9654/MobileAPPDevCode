package com.hxlxz.hxl.code6_sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ListView listView = (ListView) findViewById(R.id.ListView);
        ArrayList<String> List = new ArrayList<>();

        dbHelper = new MyDatabaseHelper(this, "Student.db", null, 1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("Student", null, null, null, null, null, null);
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                String Name = cursor.getString(cursor.getColumnIndex("Name"));
                String StudentClass = cursor.getString(cursor.getColumnIndex("Class"));
                int StudentID = cursor.getInt(cursor.getColumnIndex("StudentID"));
                List.add(Name + " " + StudentClass + " " + StudentID);
                i++;
            } while (cursor.moveToNext());
        }
        cursor.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, List);
        listView.setAdapter(adapter);
    }
}
