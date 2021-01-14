package com.example.vaibhav.upcomingmoviesapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.example.vaibhav.upcomingmoviesapp.R;
import com.example.vaibhav.upcomingmoviesapp.adpters.MovieItemAdapter;
import com.example.vaibhav.upcomingmoviesapp.models.MoviePojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
01/01/2019
Created by vaibhav joshi for CodeToArt interview
*/
public class MainActivity extends AppCompatActivity {
    ImageButton btn_info;
    ArrayList<MoviePojo> list;
    MovieItemAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();
        layoutManager= new LinearLayoutManager(this);
        recyclerView=findViewById(R.id.recView);
        recyclerView.setLayoutManager(layoutManager);
        //inflating custom action bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        ////getting customm action bar view
        View view = getSupportActionBar().getCustomView();

        btn_info = view.findViewById(R.id.action_info);
        btn_info.setVisibility(View.VISIBLE);
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Clicked on info button", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, ActivityInfo.class));
            }
        });

        getData(); //this method will be called to load the data..................
    }

    private void getData() {

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.themoviedb.org/3/movie/upcoming?api_key=b7cd3340a794e5a2f35e3abb820b497f", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (dialog.isShowing())
                    dialog.dismiss();
                try {
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    JSONObject object = new JSONObject(response);
                    JSONArray dataArray = object.getJSONArray("results");
                    int length = dataArray.length();

                    for (int i = 0; i < length; i++) {
                        JSONObject innerobj = dataArray.getJSONObject(i);
                        MoviePojo pojo= new MoviePojo();
                        pojo.setId(innerobj.getString("id"));
                        pojo.setTitle(innerobj.getString("title"));
                        pojo.setPoster_path(innerobj.getString("poster_path"));
                        pojo.setRelease_date(innerobj.getString("release_date"));
                        list.add(pojo);
                    }

                    if (list.size()>0){
                        setAdapter();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(MainActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        requestQueue.add(stringRequest);
    }

    private void setAdapter() {
        adapter = new MovieItemAdapter(this, list, new MovieItemAdapter.OnClickListener() {
            @Override
            public void onItemclick(View view, int position) {
                Toast.makeText(MainActivity.this, "Next"+position, Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
