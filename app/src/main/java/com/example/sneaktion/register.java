package com.example.sneaktion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sneaktion.config.ServerApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    Button btnLogin,btnReg;
    EditText _nama,_email,_username,_password;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    ProgressBar progressBar;
    String URL_REGIS="http://192.168.1.92/kelompok-2-TIF-Inter/sneaktion/api";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnLogin = (Button)findViewById(R.id.btnlogin);
        btnReg = (Button)findViewById(R.id.btnregistrasi);
        _nama = (EditText)findViewById(R.id.txtnama);
        _email = (EditText)findViewById(R.id.txtemail);
        _username = (EditText)findViewById(R.id.txtuser);
        _password = (EditText)findViewById(R.id.txtpassword);
        requestQueue = Volley.newRequestQueue(register.this);
        progressDialog = new ProgressDialog(this);
        progressBar = new ProgressBar(register.this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent masuk = new Intent(register.this , MainActivity.class);
                startActivity(masuk);
            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRegistration();
            }
        });
    }

    public void UserRegistration() {
        final String namapem = this._nama.getText().toString().trim();
        final String emailpem = this._email.getText().toString().trim();
        final String userpem = this._username.getText().toString().trim();
        final String alamatpem = this._password.getText().toString().trim();

        if (namapem.matches("")){
            Toast.makeText(this, "Masukkan Nama Anda", Toast.LENGTH_SHORT).show();
            return;
        }
        if (emailpem.matches("")){
            Toast.makeText(this, "Masukkan Email Anda", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userpem.matches("")){
            Toast.makeText(this, "Masukkan Username Anda", Toast.LENGTH_SHORT).show();
            return;
        }
        if (alamatpem.matches("")){
            Toast.makeText(this, "Masukkan Password Anda", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.GONE);
        btnReg.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String error = jsonObject.getString("error");
                    String message = jsonObject.getString("message");

                    if (status.equals("200") && error.equals("false")) {
                        Toast.makeText(register.this, message, Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent2 = new Intent(register.this, MainActivity.class);
                                startActivity(intent2);
                            }
                        }, 1500);
                    } else {
                        Toast.makeText(register.this, message, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        btnReg.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                    Intent intent3 = new Intent(register.this, MainActivity.class);
                    startActivity(intent3);
                    Toast.makeText(register.this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(register.this, "Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                btnReg.setVisibility(View.VISIBLE);
            }
        })
        {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nama", namapem);
                params.put("email", emailpem);
                params.put("username", userpem);
                params.put("password", alamatpem);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public class AuthFailureError extends VolleyError{
        private Intent mResolutionIntent;

        public AuthFailureError() {}

        public AuthFailureError(Intent intent) {
            mResolutionIntent = intent;
        }

        public AuthFailureError(NetworkResponse response) {
            super(response);
        }

        public AuthFailureError(String message) {
            super(message);
        }

        public AuthFailureError(String message, Exception reason) {
            super(message, reason);
        }

        public Intent getResolutionIntent() {
            return mResolutionIntent;
        }

        @Override
        public String getMessage() {
            if (mResolutionIntent != null) {
                return "User needs to (re)enter credentials.";
            }
            return super.getMessage();
        }
    }
    }

