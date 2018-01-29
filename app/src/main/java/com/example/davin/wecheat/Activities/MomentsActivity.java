package com.example.davin.wecheat.Activities;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.davin.wecheat.R;

public class MomentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moments);

        initToolBar();
    }

    private void initToolBar() {
        Toolbar mToolbarTb = (Toolbar) findViewById(R.id.tb_toolbar);
        setSupportActionBar(mToolbarTb);
        mToolbarTb.setNavigationIcon(ContextCompat.getDrawable(
                this,R.drawable.ic_action_name));

    }
}
