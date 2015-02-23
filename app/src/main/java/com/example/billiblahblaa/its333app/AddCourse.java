package com.example.billiblahblaa.its333app;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class AddCourse extends ActionBarActivity {

    CourseDBHelper helper;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        helper = new CourseDBHelper(this);
    }

    public void addClicked(View v) {
        EditText etCode = (EditText)findViewById(R.id.etCode);
        EditText etCredit = (EditText)findViewById(R.id.etCredit);
        RadioGroup rgDay = (RadioGroup)findViewById(R.id.rgDay);
        RadioGroup rgTime = (RadioGroup)findViewById(R.id.rgTime);

        String sCode = etCode.getText().toString();
        String sCredit = etCredit.getText().toString();

        if (sCode.trim().length() == 0 || sCredit.trim().length() == 0) {
            Toast t = Toast.makeText(this.getApplicationContext(),
                    "Both course code and credit are necessary.",
                    Toast.LENGTH_SHORT);
            t.show();
        }
        else {
            int rDayID = rgDay.getCheckedRadioButtonId();
            int rTimeID = rgTime.getCheckedRadioButtonId();
            String day = ((RadioButton)findViewById(rDayID)).getText().toString();
            String time = ((RadioButton)findViewById(rTimeID)).getText().toString();


            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues r = new ContentValues();
            r.put("code", sCode);
            r.put("credit", Integer.valueOf(sCredit));
            r.put("day", day);
            r.put("time", time);

            Toast t = Toast.makeText(this.getApplicationContext(),
                    "Add successfully",
                    Toast.LENGTH_SHORT);
            t.show();
            listCourse();
        }
    }

    public void listCourse() {
        SQLiteDatabase db2 = helper.getReadableDatabase();
        Cursor cursor = db2.rawQuery("SELECT _id, code, (day || ' ' || time) AS x FROM course;", null);

        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor,
                new String[] {"code", "x"}, new int[] {android.R.id.text1,android.R.id.text2},0);

        ListView lv = (ListView)findViewById(R.id.listView);
        lv.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
