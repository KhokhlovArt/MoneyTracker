package com.example.khokhlovart_loftschool.moneytracker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by Dom on 14.11.2017.
 */

public class ConfirmationDialog extends DialogFragment {
    private ItemsFragment itemsFragment;
    public void setItemsFragment(ItemsFragment itemsFragment)
    {
        this.itemsFragment = itemsFragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setCancelable(false)
                .setTitle(R.string.titel_delete_elements)
                .setMessage(R.string.Approve)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (itemsFragment == null) return;
                        itemsFragment.deleteSelectedItems();
                    }
                })
                .setNegativeButton(android.R.string.cancel,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (itemsFragment == null) return;
                        itemsFragment.stopActionMode();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        if (itemsFragment == null) return;
                        itemsFragment.stopActionMode();
                    }
                });

        return builder.create();
    }
}
