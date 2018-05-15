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

/**
 * Created by Don on 11/04/2018.
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    private ViewGroupPresenter mViewGroupPresenter;



    public class ViewHolder extends RecyclerView.ViewHolder implements ViewGroupContract.RowView{

        public ImageView groupDisplayIv;
        public TextView groupNameTv;
        public RelativeLayout groupParentRl;

        public ViewHolder(View itemView) {
            super(itemView);
            groupDisplayIv = itemView.findViewById(R.id.group_display_imageview);
            groupNameTv = itemView.findViewById(R.id.group_name_textview);
            groupParentRl = itemView.findViewById(R.id.group_parent_relativelayout);
            groupParentRl.setOnClickListener(v -> {
                mViewGroupPresenter.rowTapped(getAdapterPosition());
            });
        }

        @Override
        public void setGroupName(String name) {
            groupNameTv.setText(name);
        }

    }

    public GroupAdapter(ViewGroupPresenter presenter) {
        this.mViewGroupPresenter = presenter;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mViewGroupPresenter.onBindGroupViewAtPosition(holder, position);
    }

    @Override
    public int getItemCount() {
        return mViewGroupPresenter.getGroupCount();
    }



}
