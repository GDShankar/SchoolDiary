package com.elbissopsoft.schooldiary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import static com.elbissopsoft.schooldiary.Config.KEY_pwd;
import static com.elbissopsoft.schooldiary.Config.KEY_reg_no;
import static com.elbissopsoft.schooldiary.Config.LOGIN_URL;

public class Login extends AppCompatActivity implements View.OnClickListener{


    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;

    private String register_no;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editTextUsername = (EditText) findViewById(R.id.editText2);
        editTextPassword = (EditText) findViewById(R.id.editText3);

        buttonLogin = (Button) findViewById(R.id.loginbtn);

        buttonLogin.setOnClickListener(this);
    }


    private void userLogin() {
        register_no = editTextUsername.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
//Toast.makeText(getApplicationContext(),register_no+password,Toast.LENGTH_LONG).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equalsIgnoreCase(Config.LOGIN_SUCCESS)){
                            openProfile();
                        }else{
                            Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_reg_no,register_no);
                map.put(KEY_pwd,password);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void openProfile(){
        //Creating a shared preference
        SharedPreferences sharedPreferences = Login.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Creating editor to store values to shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Adding values to editor
        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
        editor.putString(Config.REGNO_SHARED_PREF, register_no);

        //Saving values to editor
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        userLogin();
    }
}