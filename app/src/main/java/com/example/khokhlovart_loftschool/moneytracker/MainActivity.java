package com.example.khokhlovart_loftschool.moneytracker;

//import android.support.design.widget.TabLayout;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tab      = (TabLayout) findViewById(R.id.tabs);
        Toolbar   tool_bar = (Toolbar)   findViewById(R.id.tool_bar);
        ViewPager pager    = (ViewPager) findViewById(R.id.pager);

        pager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), getResources()));
        tab.setupWithViewPager(pager);

    }
}
