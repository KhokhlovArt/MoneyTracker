package com.example.khokhlovart_loftschool.moneytracker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dom on 02.11.2017.
 */

public class ItemsAdaptor extends RecyclerView.Adapter<ItemsAdaptor.ItemViewHolder> {
    private static int COLOR_CURRENCY = Color.rgb(150,150,150);
    private List<ItemCosts> itemCostList = new ArrayList<>();

    private Context context;
    public ItemsAdaptor(Context context, int type)
    {
        this.context = context;
       /* switch (type) {
            case (ItemsFragment.PAGE_EXPENSE):
                itemCostList.add(new ItemCosts(1, "Диван"));
                itemCostList.add(new ItemCosts(1, "Чемодан"));
                itemCostList.add(new ItemCosts(12, "Саквояж"));
                itemCostList.add(new ItemCosts(644, "Картину"));
                itemCostList.add(new ItemCosts(5, "Корзину"));
                itemCostList.add(new ItemCosts(4, "Картонку"));
                itemCostList.add(new ItemCosts(15, "И маленькую собачонку"));
                itemCostList.add(new ItemCosts(3, "Выдали даме на станции Четыре зелёных квитанции О том, что получен багаж"));
                itemCostList.add(new ItemCosts(7, "Диван"));
                itemCostList.add(new ItemCosts(3, "Чемодан"));
                itemCostList.add(new ItemCosts(33, "Саквояж"));
                itemCostList.add(new ItemCosts(13, "Картина"));
                itemCostList.add(new ItemCosts(2, "Корзина"));
                itemCostList.add(new ItemCosts(3, "Картонка"));
                itemCostList.add(new ItemCosts(777, "И маленькая собачонка."));
                break;
            case (ItemsFragment.PAGE_INCOMES):
                itemCostList.add(new ItemCosts(1000, "Зарплата"));
                itemCostList.add(new ItemCosts(666, "Подарок от тёщи"));
                itemCostList.add(new ItemCosts(10, "Продали старый диван"));
                itemCostList.add(new ItemCosts(5000, "Сдача бутылок"));
                itemCostList.add(new ItemCosts(777, "Выиграл в лотерею"));
                break;
            default:
                break;
        }*/
    }
    public void setItems(List<ItemCosts> items) {
        this.itemCostList = items;
        notifyDataSetChanged();
    }

    public  void  addItems(ItemCosts item)
    {
        this.itemCostList.add(item);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return  new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_costs, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        Resources res = context.getResources();
        String text_from_pattern = res.getString(R.string.cost_with_currency, String.valueOf(itemCostList.get(position).cost));
        Spannable text = new SpannableString(text_from_pattern);
        text.setSpan(new ForegroundColorSpan(COLOR_CURRENCY), text.length()-1, text.length() ,  Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.name.setText(itemCostList.get(position).name);
        holder.cost.setText(text);
    }


    @Override
    public int getItemCount() {
        return itemCostList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        private TextView name;
        private TextView  cost;
        public ItemViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_name);
            cost = (TextView) itemView.findViewById(R.id.item_cost);
        }
    }
}
