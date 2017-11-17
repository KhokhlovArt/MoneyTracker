package com.example.khokhlovart_loftschool.moneytracker;

//import android.support.design.widget.TabLayout;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = new Intent(getBaseContext() , AuthActivity.class);
        startActivity(intent);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(AuthActivity.account != null) {
            setContentView(R.layout.activity_main);
            TabLayout tab = (TabLayout) findViewById(R.id.tabs);
            Toolbar tool_bar = (Toolbar) findViewById(R.id.tool_bar);
            ViewPager pager = (ViewPager) findViewById(R.id.pager);
            pager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), getResources()));
            tab.setupWithViewPager(pager);
        }
        else
        {
           // Intent intent = new Intent(getBaseContext() , AuthActivity.class);
          //  startActivity(intent);
        }
    }
}
