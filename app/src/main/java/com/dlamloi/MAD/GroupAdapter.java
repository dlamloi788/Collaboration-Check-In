package com.dlamloi.MAD;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.dlamloi.MAD.model.Group;

import java.util.List;

import ViewHolder.GroupViewHolder;

/**
 * Created by Don on 11/04/2018.
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupViewHolder> {

    List<Group> mGroups;

    public GroupAdapter() {

    }

    public GroupAdapter(List<Group> groups) {
        mGroups = groups;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.group_item, parent, false);
        return new GroupViewHolder(view);

    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        Group group = mGroups.get(position);
        holder.groupNameTv.setText(group.getName());
    }

    @Override
    public int getItemCount() {
        return mGroups.size();
    }
}
