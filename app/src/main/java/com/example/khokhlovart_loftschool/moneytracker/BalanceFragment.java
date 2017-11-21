package com.example.khokhlovart_loftschool.moneytracker;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.khokhlovart_loftschool.moneytracker.Api.Api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dom on 04.11.2017.
 */

public class BalanceFragment extends Fragment{
    private static final int LOADER_BALANCE = 4;
    TextView total;
    TextView inp;
    TextView exp;
    Diagram diagram;
    private Api api;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        api     = ((App) getActivity().getApplication()).getApi();
        return inflater.inflate(R.layout.balance, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        total   = (TextView) view.findViewById(R.id.total);
        inp     = (TextView) view.findViewById(R.id.inp);
        exp     = (TextView) view.findViewById(R.id.exp);
        diagram = (Diagram) view.findViewById(R.id.diagram);
        loadBalance();
    }

    private void loadBalance() {
        getLoaderManager().initLoader(LOADER_BALANCE, null, new LoaderManager.LoaderCallbacks<BalanceRes>() {

            @Override
            public Loader<BalanceRes> onCreateLoader(int id, Bundle args) {
                return new AsyncTaskLoader<BalanceRes>(getContext()) {
                    @Override
                    public BalanceRes loadInBackground() {
                        try {
                            return api.balance().execute().body();
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader<BalanceRes> loader, BalanceRes data) {
                if (data != null) {
                    Resources res = getContext().getResources();
                    exp.setText(res.getString(R.string.cost_with_currency, String.valueOf(data.total_expenses)));
                    inp.setText(res.getString(R.string.cost_with_currency, String.valueOf(data.total_income)));
                    total.setText(res.getString(R.string.cost_with_currency, String.valueOf(data.total_income - data.total_expenses)));
                    diagram.update(data.total_expenses, data.total_income);
                }
            }

            @Override
            public void onLoaderReset(Loader<BalanceRes> loader) {

            }
        }).forceLoad();
    }
}
