package com.elbissopsoft.schooldiary;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Thyagu on 11/22/2016.
 */


class Events_Custom  extends ArrayAdapter<Eventdec> {

    ArrayList<Eventdec> eventsList;
    Eventdec eventdec;
    Context context;

   //int list_style;

    public Events_Custom(Context applicationContext,  ArrayList<Eventdec> eventsList) {
//        super(context,eventsList);


        super(applicationContext,R.layout.eventlist, eventsList);

        this.context=applicationContext;
       // this.list_style=list_style;
        this.eventsList=eventsList;


    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater inflator = LayoutInflater.from(getContext());
            convertView = inflator.inflate(R.layout.eventlist, null);
        }

        eventdec = getItem(position);

        TextView evetns_tv = (TextView) convertView.findViewById(R.id.events);
        TextView  event_desc_tv = (TextView) convertView.findViewById(R.id.event_desc);
        TextView dateinfo_tv = (TextView) convertView.findViewById(R.id.date);
        evetns_tv.setText(eventsList.get(position).getEvents());
        event_desc_tv.setText(eventsList.get(position).getEvent_desc());
        dateinfo_tv.setText(eventsList.get(position).getDateinfo());
       Button button=(Button) convertView.findViewById(R.id.more);
        button.setTag(position);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer index = (Integer) v.getTag();
                eventdec=getItem(index);
                Intent i=new Intent(context, EventlongdescActivity.class);
                i.putExtra("date",eventsList.get(index).getDateinfo());
                i.putExtra("longdesc",eventsList.get(index).getLongdesc());
                i.putExtra("title",eventsList.get(index).getEvents());
context.startActivity(i);
//                Toast.makeText(context,eventdec.getEvents(),Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }



}
