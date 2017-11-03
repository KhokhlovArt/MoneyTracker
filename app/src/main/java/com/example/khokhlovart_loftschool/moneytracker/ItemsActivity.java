package com.example.khokhlovart_loftschool.moneytracker;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;

/**
 * Created by Dom on 02.11.2017.
 */

public class ItemsActivity  extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_list);
        RecyclerView itemsRecyclerView = (RecyclerView) findViewById(R.id.items_recycler_view);
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemsRecyclerView.setAdapter(new ItemsAdaptor(this));
    }
}
