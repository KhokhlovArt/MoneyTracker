package com.example.khokhlovart_loftschool.moneytracker;

/**
 * Created by Dom on 02.11.2017.
 */

public class ItemCosts {
    private int cost;
    private String name;

    public ItemCosts(int cost, String name) {
        this.cost = cost;
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
