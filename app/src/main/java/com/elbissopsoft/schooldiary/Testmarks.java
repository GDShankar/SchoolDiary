package com.elbissopsoft.schooldiary;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
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

public class Testmarks extends AppCompatActivity {
    CalendarView calendarView;
    ProgressDialog loading;
    TextView dateDisplay;
    TextView English,Tamil,Science,Social,Maths;
    String date;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testmarks);
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String reg_no = sharedPreferences.getString(Config.REGNO_SHARED_PREF,"Not Available");
        English=(TextView)findViewById(R.id.english);
        Tamil=(TextView)findViewById(R.id.tamil);
        Science=(TextView)findViewById(R.id.science);
        Social=(TextView)findViewById(R.id.social);
        Maths=(TextView)findViewById(R.id.maths);final SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        dateDisplay = (TextView) findViewById(R.id.datedisplay);
        dateDisplay.setText("Date: "+simpleDateFormat.format(calendarView.getDate()));
        calendarView.setMaxDate(calendarView.getDate());
        date=simpleDateFormat.format(calendarView.getDate());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                int mon=month+1;
                date=year+"-"+mon+"-"+day;
                dateDisplay.setText("Date: "+date);


                getData(date,reg_no);
            }
        });
        getData(date,reg_no);

    }

//    private  int numLength(int n){
//        for(int length =1;n%Math.pow(10,length))
//    }

    private void getData(String dateinfo,String register_no) {
        loading = ProgressDialog.show(Testmarks.this,"Please wait...","Fetching...",false,false);

        final String url = Config.get_testmarks_URL+"date="+dateinfo.trim()+"&register_no="+register_no.trim();

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
                        Toast.makeText(Testmarks.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        String tamil = "";
        String english = "";
        String maths = "";
        String science = "";
        String social = "";
        if (!response.toLowerCase().trim().equals("failure")) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
                JSONObject collegeData = result.getJSONObject(0);
                tamil = collegeData.getString(Config.d_tam);
                english = collegeData.getString(Config.d_eng);
                maths = collegeData.getString(Config.d_mat);
                science = collegeData.getString(Config.d_sci);
                social = collegeData.getString(Config.d_soc);

            } catch (JSONException e) {
                e.printStackTrace();
            }
//        String s = "<b>Bolded text</b>, <i>italic text</i>, even <u>underlined</u>!";
            if(!english.isEmpty()) {
                English.setText("\n English: \t" + english);
            }
            else{
                English.setText("\n English:\t NO Test");

            }
            if(!tamil.isEmpty()) {
                Tamil.setText("\nTamil: \t" + tamil);}
            else{
                Tamil.setText("\n Tamil:\t No Test");

            }
            if (!science.isEmpty()) {
                Science.setText("\n Science: \t" + science); }
            else{
                Science.setText("\n Science:\tNo Test");
            }
            if (!social.isEmpty()) {
                Social.setText("\nSocial: \t" + social);}
            else {
                Social.setText("\n Social:\t No Test");
            }
            if (!maths.isEmpty()) {
                Maths.setText("\n Maths: \t" + maths);
            } else {
                Maths.setText("\n Maths:\t No Test");
            }



        }
        else{
            Toast.makeText(getApplicationContext(),"No Test",Toast.LENGTH_LONG).show();
            English.setText("");
            Tamil.setText("");
            Science.setText("");
            Social.setText("");
            Maths.setText("");

        }


    }


}

