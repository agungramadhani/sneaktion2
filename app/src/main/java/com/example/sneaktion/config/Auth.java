package com.example.sneaktion.config;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.sneaktion.MainActivity;
import com.example.sneaktion.HomeActivity;

public class Auth {
    SharedPreferences sharedPreferences;
    public Context mCtx;

    public static final String SHARED_PREF_NAME = "kos4";
    private static final String sudahlogin = "n";
    public SharedPreferences.Editor editor;

    private static final String namauser = "nama";
    private static final String emailuser = "email";
    private static final String username = "username";
    private static final String passuser = "password";
    public static final String LOGIN_STATUS = "LOGIN_STATUS";




    public Auth(Context context){
        this.mCtx = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setdatauser(String xnama, String xemail, String xusername, String xpassword){

        editor.putBoolean(LOGIN_STATUS, true);
        editor.putString(namauser, xnama);
        editor.putString(emailuser, xemail);
        editor.putString(username, xusername);
        editor.putString(passuser, xpassword);
        editor.putString(sudahlogin, "y");
        editor.apply();
    }




    public boolean isLogin(){
        return sharedPreferences.getBoolean(LOGIN_STATUS, false);
    }

    public void logout(){
        editor.clear();
        editor.commit();

        Intent login = new Intent(mCtx, MainActivity.class);
        mCtx.startActivity(login);
    }

    public String getnama() {
        return sharedPreferences.getString(namauser, null);
    }
    public String getemail() {
        return sharedPreferences.getString(emailuser, null);
    }
    public String getusername() {
        return sharedPreferences.getString(username, null);
    }
    public String getpassuser() {
        return sharedPreferences.getString(passuser, null);
    }

}
