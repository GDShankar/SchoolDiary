package com.elbissopsoft.schooldiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class EventlongdescActivity extends AppCompatActivity {

    TextView longdes, title;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventlongdesc);

        Intent intent=getIntent();
        date = intent.getStringExtra("date");

    longdes=(TextView)findViewById(R.id.longdesc);
        longdes.setText(intent.getStringExtra("longdesc"));
title=(TextView)findViewById(R.id.eventstitle);
        title.setText(intent.getStringExtra("title"));

    }
}
