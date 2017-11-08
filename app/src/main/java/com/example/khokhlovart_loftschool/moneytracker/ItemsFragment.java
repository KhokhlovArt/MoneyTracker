package com.example.khokhlovart_loftschool.moneytracker;

import android.content.ClipData;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.khokhlovart_loftschool.moneytracker.Api.Api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dom on 02.11.2017.
 */

public class ItemsFragment extends Fragment{
    public static final int PAGE_UNKNOWN = -1;
    public static final int PAGE_EXPENSE = 0;
    public static final int PAGE_INCOMES = 1;

    private static final String KEY_TYPE = "TYPE";
    private int type = -1;
    private static final int LOADER_ITEMS = 0;

    private ItemsAdaptor adaptor;
    private Api api;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        type    = getArguments().getInt(KEY_TYPE, PAGE_UNKNOWN);
        adaptor = new ItemsAdaptor(getContext(), type);
        api     = ((App) getActivity().getApplication()).getApi();

    }

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
        return inflater.inflate(type == PAGE_UNKNOWN ? R.layout.error_layout : R.layout.items_list , container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (type != PAGE_UNKNOWN)
        {
            RecyclerView itemsRecyclerView = (RecyclerView) view.findViewById(R.id.items_recycler_view);
            itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            itemsRecyclerView.setAdapter(adaptor);
            if (type == PAGE_EXPENSE) {
                loadItems();
            }
            else {
                addItem();
            }
        }
    }
    private void addItem() {
        getLoaderManager().initLoader(LOADER_ITEMS, null, new LoaderManager.LoaderCallbacks<ItemCosts>() {
            @Override
            public Loader<ItemCosts> onCreateLoader(int id, Bundle args) {
                return new AsyncTaskLoader<ItemCosts>(getContext()) {
                    @Override

                    public ItemCosts loadInBackground() {
                        try {
                            ItemCosts item = api.add().execute().body();
                            return item;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader<ItemCosts> loader, ItemCosts items) {
                if (items == null) {
                    adaptor.setItems(new ArrayList<ItemCosts>());
                    showError(getContext().getResources().getString(R.string.error_msg));
                } else {
                    adaptor.addItems(items);
                }
            }

            @Override
            public void onLoaderReset(Loader<ItemCosts> loader) {

            }
        }).forceLoad();
    }

    private void loadItems() {
        getLoaderManager().initLoader(LOADER_ITEMS, null, new LoaderManager.LoaderCallbacks<List<ItemCosts>>() {
            @Override
            public Loader<List<ItemCosts>> onCreateLoader(int id, Bundle args) {
                return new AsyncTaskLoader<List<ItemCosts>>(getContext()) {
                    @Override

                    public List<ItemCosts> loadInBackground() {
                        try {
                            List<ItemCosts> items = api.items(type).execute().body();
                            return items;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader<List<ItemCosts>> loader, List<ItemCosts> items) {
                if (items == null) {
                    adaptor.setItems(new ArrayList<ItemCosts>());
                    showError(getContext().getResources().getString(R.string.error_msg));
                } else {
                    adaptor.setItems(items);
                }
            }

            @Override
            public void onLoaderReset(Loader<List<ItemCosts>> loader) {

            }
        }).forceLoad();
    }

    private void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
