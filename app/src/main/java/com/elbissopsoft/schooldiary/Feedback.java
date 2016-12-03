package com.elbissopsoft.schooldiary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity {

    private EditText edittext;
    TextView textview;
    Button submit;
Config config=new Config();

    String feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String reg_no = sharedPreferences.getString(Config.REGNO_SHARED_PREF,"Not Available");
        Toast.makeText(getApplicationContext(),reg_no,Toast.LENGTH_LONG).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);

        edittext = (EditText) findViewById(R.id.et);
        textview = (TextView) findViewById(R.id.tv);
        submit = (Button) findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
             insertdb(reg_no);
             edittext.setText("");
             }
        });
    }

    private void insertdb(final String register_no){

        final String feedback = edittext.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.FEEDBACK_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(Feedback.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                     @Override
                    public void onErrorResponse(VolleyError error) {



                        Toast.makeText(Feedback.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<String, String>();
                params.put(config.KEY_reg_no,register_no);
                params.put(config.FEEDBACK,feedback);

                return params;
            }
//            @Override
//            public Priority getPriority() {
//                return Priority.IMMEDIATE;
//            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
