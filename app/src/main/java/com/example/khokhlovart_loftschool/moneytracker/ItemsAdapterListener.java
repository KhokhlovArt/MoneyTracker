package com.example.khokhlovart_loftschool.moneytracker;

/**
 * Created by Dom on 14.11.2017.
 */

public interface ItemsAdapterListener {
    void onItemClick(ItemCosts item, int position);
    void onItemLongClick(ItemCosts item, int position);
}
