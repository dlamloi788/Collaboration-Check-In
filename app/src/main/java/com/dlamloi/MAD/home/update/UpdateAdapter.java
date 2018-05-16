package com.dlamloi.MAD.home.update;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.model.Group;
import com.dlamloi.MAD.model.Update;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import ViewHolder.UpdateViewHolder;

/**
 * Created by Don on 20/04/2018.
 */

public class UpdateAdapter extends RecyclerView.Adapter<UpdateViewHolder> {

    private ArrayList<Update> mUpdates;


    public UpdateAdapter(ArrayList<Update> updates) {
        this.mUpdates = updates;
    }

    @Override
    public UpdateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.update_item, parent, false);
        return new UpdateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UpdateViewHolder holder, int position) {
        Update update = mUpdates.get(position);
        holder.titleTv.setText(update.getTitle());
        holder.publishedDateTv.setText(update.getDate());
        holder.detailsTv.setText(update.getDetails());
        holder.publisherTv.setText(update.getPublisher());
    }

    @Override
    public int getItemCount() {
        return mUpdates.size();
    }


}
