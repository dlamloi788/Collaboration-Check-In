package com.dlamloi.MAD.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.dlamloi.MAD.R;
import com.dlamloi.MAD.activity.ViewGroupActivity;
import com.dlamloi.MAD.model.Group;

import java.util.List;

import ViewHolder.GroupViewHolder;

/**
 * Created by Don on 11/04/2018.
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupViewHolder> {

    Context mContext;
    List<Group> mGroups;


    public GroupAdapter() {

    }

    public GroupAdapter(Context context, List<Group> groups) {
        mContext = context;
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
        holder.groupParentRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewGroupIntent = new Intent(mContext, ViewGroupActivity.class);
                viewGroupIntent.putExtra(ViewGroupActivity.GROUP_KEY, group);
                mContext.startActivity(viewGroupIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mGroups.size();
    }
}
