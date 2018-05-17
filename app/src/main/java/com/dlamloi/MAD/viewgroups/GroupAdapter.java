package com.dlamloi.MAD.viewgroups;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.dlamloi.MAD.R;
import com.dlamloi.MAD.model.Group;
import com.dlamloi.MAD.model.Update;

import java.util.ArrayList;

/**
 * Created by Don on 11/04/2018.
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    private GroupItemClickListener mGroupItemClickListener;
    private ArrayList<Group> mGroups;


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView groupDisplayIv;
        public TextView groupNameTv;
        public RelativeLayout groupParentRl;

        public ViewHolder(View itemView) {
            super(itemView);
            groupDisplayIv = itemView.findViewById(R.id.group_display_imageview);
            groupNameTv = itemView.findViewById(R.id.group_name_textview);
            groupParentRl = itemView.findViewById(R.id.group_parent_relativelayout);
        }

    }

    public GroupAdapter(ArrayList<Group> groups, GroupItemClickListener groupItemClickListener) {
        this.mGroups = groups;
        this.mGroupItemClickListener = groupItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Group group = mGroups.get(position);
        holder.groupNameTv.setText(group.getName());
        holder.groupParentRl.setOnClickListener(v -> mGroupItemClickListener.groupClick(group.getId(), group.getName()));
    }

    @Override
    public int getItemCount() {
        return mGroups.size();
    }



}
