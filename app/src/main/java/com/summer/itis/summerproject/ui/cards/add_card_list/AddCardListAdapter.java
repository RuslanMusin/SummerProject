package com.summer.itis.summerproject.ui.cards.add_card_list;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.summer.itis.summerproject.model.pojo.opensearch.Item;
import com.summer.itis.summerproject.ui.base.BaseAdapter;

import java.util.List;

public class AddCardListAdapter extends BaseAdapter<Item, AddCardListHolder> {

    public AddCardListAdapter(@NonNull List<Item> items) {
        super(items);
    }

    @Override
    public AddCardListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return AddCardListHolder.create(parent.getContext());
    }

    @Override
    public void onBindViewHolder(AddCardListHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Item item = getItem(position);
        holder.bind(item);
    }
}
