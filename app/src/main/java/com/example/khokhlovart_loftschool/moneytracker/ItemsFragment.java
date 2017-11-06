package com.example.khokhlovart_loftschool.moneytracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Dom on 02.11.2017.
 */

public class ItemsFragment extends Fragment{
    public static final int PAGE_UNKNOWN = -1;
    public static final int PAGE_EXPENSE = 0;
    public static final int PAGE_INCOMES = 1;

    private static final String KEY_TYPE = "TYPE";
    private int type = -1;

    public static ItemsFragment CreateItemsFragment(int type)
    {
        ItemsFragment f = new ItemsFragment();
        Bundle b = new Bundle();
        b.putInt(ItemsFragment.KEY_TYPE, type);
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        type = getArguments().getInt(KEY_TYPE, PAGE_UNKNOWN);
        return inflater.inflate(type == PAGE_UNKNOWN ? R.layout.error_layout : R.layout.items_list , container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (type != PAGE_UNKNOWN)
        {
            RecyclerView itemsRecyclerView = (RecyclerView) view.findViewById(R.id.items_recycler_view);
            itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            itemsRecyclerView.setAdapter(new ItemsAdaptor(getContext(), type));
        }
    }
}
