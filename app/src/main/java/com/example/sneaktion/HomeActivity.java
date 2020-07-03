package com.example.sneaktion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.icu.math.BigDecimal;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class HomeActivity extends AppCompatActivity{
    ListView listView;
    ArrayList<DataHome> dataHomeArrayList;
//    String mTitle[]={"Vans","Adidas","Puma","Nike","Converse","Diadora"};
//    String mDescription[]={"This Is ORI Vans Shoes","This Is ORI Adidas Shoes","This Is ORI Puma Shoes","This Is ORI Nike Shoes","This Is ORI Converse Shoes","This Is ORI Diadora Shoes"};
//    int img[]={R.drawable.vans,R.drawable.adidas,R.drawable.puma,R.drawable.nike,R.drawable.converse,R.drawable.diadora};
    String URL_REGIS="http://192.168.1.92/kelompok-2-TIF-Inter/sneaktion/threadapi";
    private ListAdapter listAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.legitupload:
                        startActivity(new Intent(getApplicationContext(),legitupload.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.notif:
                        startActivity(new Intent(getApplicationContext(),notif.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        listView = findViewById(R.id.listvieww);

        Ambildata();
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> pa, View v, int p, long id) {
//                // klik intent
//                Toast.makeText(HomeActivity.this, "hayooo", Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(HomeActivity.this, DetailActivity.class);
//                i.putExtra("title", );
//                startActivity(i);
//            }
//        });


    }



    public void Ambildata() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_REGIS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");

//                    String message = jsonObject.getString("message");

                    if (status.equals("200")) {
                        dataHomeArrayList = new ArrayList<>();
                        JSONArray dataArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < dataArray.length(); i++) {
                            DataHome datamodel = new DataHome();
                            JSONObject dataobj = dataArray.getJSONObject(i);
                            datamodel.setTitle(dataobj.getString("title_threads"));
                            final String isi_threads = dataobj.getString("isi_threads");
                            datamodel.setDeskripsi(dataobj.getString("isi_threads"));
                            datamodel.setImage_url(dataobj.getString("img_threads"));
                            dataHomeArrayList.add(datamodel);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> pa, View v, int p, long id) {
                                    // klik intent
                                    Toast.makeText(HomeActivity.this, "hayooo", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(HomeActivity.this, MainActivity.class);
                                i.putExtra("deskripsi",isi_threads);
                                startActivity(i);
                                }
                            });

                        }
                        setupListview();


                    } else {
                        Toast.makeText(HomeActivity.this, "message", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    Intent intent3 = new Intent(HomeActivity.this, MainActivity.class);
//                    startActivity(intent3);
//                    Toast.makeText(HomeActivity.this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, "Error! " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setupListview(){
        listAdapter = new ListAdapter(this, dataHomeArrayList);
        listView.setAdapter(listAdapter);
    }

    public static void removeSimpleProgressDialog() {
        try {

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    class MyAdapter extends ArrayAdapter<String> {
//        Context context;
//        String rTitle[];
//        String rDescription[];
//        int rImgs[];
//
//        MyAdapter(Context c, String title[], String description[], int imgs[]){
//            super(c, R.layout.row, R.id.txtview1, title);
//            this.context = c;
//            this.rTitle = title;
//            this.rDescription = description;
//            this.rImgs = imgs;
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View row = layoutInflater.inflate(R.layout.row, parent, false);
//            ImageView images = row.findViewById(R.id.imagess);
//            TextView myTitle = row.findViewById(R.id.txtview1);
//            TextView myDescription = row.findViewById(R.id.txtview2);
//
//            images.setImageResource(rImgs[position]);
//            myTitle.setText(rTitle[position]);
//            myDescription.setText(rDescription[position]);
//
//            return row;
//        }
//    }
}
