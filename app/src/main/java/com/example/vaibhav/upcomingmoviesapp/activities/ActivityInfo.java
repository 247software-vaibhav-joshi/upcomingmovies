package com.example.vaibhav.upcomingmoviesapp.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.vaibhav.upcomingmoviesapp.R;

public class ActivityInfo extends AppCompatActivity {
    TextView tv_label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        ////getting customm action bar view
        View view = getSupportActionBar().getCustomView();
        tv_label=view.findViewById(R.id.tv_label);
        tv_label.setText("Information");
    }
}
