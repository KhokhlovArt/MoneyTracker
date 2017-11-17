package com.example.khokhlovart_loftschool.moneytracker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.SparseBooleanArray;
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
    private ItemsAdapterListener clickListener = null;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    private Context context;
    public ItemsAdaptor(Context context, int type)
    {
        this.context = context;

    }

    public void setListener(ItemsAdapterListener listener) {
            this.clickListener = listener;
    }


    public void addItem(ItemCosts items) {
        this.itemCostList.add(items);
        notifyDataSetChanged();
    }
    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
                selectedItems.delete(pos);
            } else {
                selectedItems.put(pos, true);
            }
        notifyItemChanged(pos);
    }

    void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    int getSelectedItemCount() {
        return selectedItems.size();
    }

            List<Integer> getSelectedItems() {
            List<Integer> items = new ArrayList<>(selectedItems.size());
            for (int i = 0; i < selectedItems.size(); i++) {
                    items.add(selectedItems.keyAt(i));
                }
            return items;
        }

            ItemCosts remove(int pos) {
            final ItemCosts item = itemCostList.remove(pos);
            notifyItemRemoved(pos);
            return item;
       }

    public void setItems(List<ItemCosts> items) {
        this.itemCostList = items;
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return  new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_costs, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        Resources res = context.getResources();
        String text_from_pattern = res.getString(R.string.cost_with_currency, String.valueOf(itemCostList.get(position).price));
        Spannable text = new SpannableString(text_from_pattern);
        text.setSpan(new ForegroundColorSpan(COLOR_CURRENCY), text.length()-1, text.length() ,  Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.name.setText(itemCostList.get(position).name);
        holder.cost.setText(text);
        ItemCosts item = itemCostList.get(position);
        holder.bind(item, position, selectedItems.get(position, false), clickListener);
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

        void bind(final ItemCosts item, final int position, boolean selected, final ItemsAdapterListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item, position);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(item, position);
                    return true;
                }
            });

            itemView.setActivated(selected);
        }
    }
}
