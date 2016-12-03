package com.elbissopsoft.schooldiary;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Events extends AppCompatActivity {
TextView datedisplay;
    ImageButton next_btn,previous_btn;
    final ArrayList<Eventdec> eventsList = new ArrayList<>();
    ListView listView;
    String date,year_month;
    int  mon,year;
    ProgressDialog loading;
    String []months={"January","Febraury","March","April","May","June","July","August","September","October","November","December"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events);
        datedisplay=(TextView)findViewById(R.id.date);
        next_btn=(ImageButton)findViewById(R.id.nextimg);
        previous_btn=(ImageButton)findViewById(R.id.previousimg);
        listView=(ListView) findViewById(R.id.eventslist);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        Date dateobj = new Date();
        date= df.format(dateobj);
        String []dateparts=date.split("-");
        String mont;
        mont = dateparts[1];
        year= Integer.parseInt(dateparts[0]);
        mon=Integer.parseInt(mont);
year_month=String.valueOf(year)+String.valueOf(mon);
//        Toast.makeText(getApplicationContext(),year_month,Toast.LENGTH_LONG).show();
datedisplay.setText(months[mon-1]  +year);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mon+=1;
                if(mon==13)
                {
                    mon=1;
                    year+=1;
                }
                year_month=String.valueOf(year)+String.valueOf(mon);
                datedisplay.setText(months[mon-1] +"-" +year);
                getData(year_month);
//                Toast.makeText(getApplicationContext(),String.valueOf(year)+String.valueOf(mon),Toast.LENGTH_LONG).show();
            }
        });
        previous_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mon-=1;
                if(mon==0)
                {
                    mon=12;
                    year-=1;
                }
                year_month=String.valueOf(year)+String.valueOf(mon);
                datedisplay.setText(months[mon-1] +"-" +year);
                getData(year_month);
            }
        });
getData(year_month);
    }

    private void getData( String date) {
//        Toast.makeText(getApplicationContext(),date,Toast.LENGTH_LONG).show();
        loading = ProgressDialog.show(Events.this,"Please wait...","Fetching...",false,false);

        final String url = Config.get_events_URL+"date="+date.trim();

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
//Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Events.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void showJSON(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
                eventsList.clear();
                for(int i=0;i<result.length();i++)
                {
                JSONObject eve = result.getJSONObject(i);
               String dateinfo = eve.getString(Config.e_date);
                String   events = eve.getString(Config.e_events);
                String event_desc = eve.getString(Config.e_edesc);
                    String longdesc = eve.getString(Config.e_eldesc);

//                    Toast.makeText(getApplicationContext(),dateinfo+event_desc+events,Toast.LENGTH_LONG).show();
                    Eventdec ed=new Eventdec();
                    ed.setDateinfo(dateinfo);
                    ed.setEvents(events);
                    ed.setEvent_desc(event_desc);
                    ed.setLongdesc(longdesc);
                    eventsList.add(ed);
//                    Toast.makeText(getApplicationContext(), (CharSequence) eventsList,Toast.LENGTH_LONG).show();

                }
           Events_Custom adapter=new Events_Custom(this,eventsList);
                adapter.notifyDataSetChanged();
listView.setAdapter(adapter);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

        }
   }
