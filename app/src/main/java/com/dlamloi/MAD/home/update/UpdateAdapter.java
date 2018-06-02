package com.dlamloi.MAD.home.update;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.model.Update;

import java.util.ArrayList;

/**
 * Created by Don on 20/04/2018.
 */

/**
 * This class helps display a provided list as recyclerview rows
 */
public class UpdateAdapter extends RecyclerView.Adapter<UpdateAdapter.ViewHolder> {

    private ArrayList<Update> mUpdates;

    /**
     * Caches the update rows
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTv;
        public TextView publishedDateTv;
        public TextView detailsTv;
        public TextView publisherTv;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.update_title_textview);
            publisherTv = itemView.findViewById(R.id.update_publisher_textview);
            detailsTv = itemView.findViewById(R.id.update_details_textview);
            publishedDateTv = itemView.findViewById(R.id.update_published_date_textview);

        }
    }

    /**
     * Creates an instance of the update adapter
     *
     * @param updates the list to be converted into recyclerview rows
     */
    public UpdateAdapter(ArrayList<Update> updates) {
        this.mUpdates = updates;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.update_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Update update = mUpdates.get(position);
        holder.titleTv.setText(update.getTitle());
        holder.publishedDateTv.setText(update.getDate());
        holder.detailsTv.setText(update.getDetails());
        holder.publisherTv.setText(update.getPublisher());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemCount() {
        return mUpdates.size();
    }


}
