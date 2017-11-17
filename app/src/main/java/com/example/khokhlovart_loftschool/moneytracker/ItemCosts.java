package com.example.khokhlovart_loftschool.moneytracker;

import java.io.Serializable;

/**
 * Created by Dom on 02.11.2017.
 */

public class ItemCosts implements Serializable {
    public int price;
    public String name;

    public ItemCosts(int cost, String name) {
        this.price = cost;
        this.name = name;
    }
}
