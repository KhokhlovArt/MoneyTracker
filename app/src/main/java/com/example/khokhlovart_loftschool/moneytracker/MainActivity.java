package com.example.khokhlovart_loftschool.moneytracker;

//import android.support.design.widget.TabLayout;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;


public class MainActivity extends AppCompatActivity {
    private TabLayout tab;
    private Toolbar tool_bar;
    private ViewPager pager;
    private boolean isNeedInitPagerAdaptor = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            tab      = (TabLayout) findViewById(R.id.tabs);
            tool_bar = (Toolbar)   findViewById(R.id.tool_bar);
            pager    = (ViewPager) findViewById(R.id.pager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(AuthActivity.getAccount() != null) {
        //if(((App)getApplication()).isLoggedIn()){
            if (isNeedInitPagerAdaptor) {
                isNeedInitPagerAdaptor = false;
                pager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), getResources()));
                tab.setupWithViewPager(pager);
            }
        }
        else
        {
            Intent intent = new Intent(getBaseContext() , AuthActivity.class);
            startActivity(intent);
        }
    }
}
