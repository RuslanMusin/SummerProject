package com.summer.itis.summerproject.ui.member.member_list;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.summer.itis.summerproject.model.User;
import com.summer.itis.summerproject.ui.base.BaseAdapter;

import java.util.List;

public class MemberAdapter extends BaseAdapter<User, MemberItemHolder> {

    public MemberAdapter(@NonNull List<User> items) {
        super(items);
    }

    @Override
    public MemberItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return MemberItemHolder.create(parent.getContext());
    }

    @Override
    public void onBindViewHolder(MemberItemHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        User item = getItem(position);
        holder.bind(item);
    }
}
