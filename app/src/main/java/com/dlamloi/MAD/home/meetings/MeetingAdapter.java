package com.dlamloi.MAD.home.meetings;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.model.Meeting;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Don on 10/05/2018.
 */

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder> {

    private ArrayList<Meeting> mMeetings;


    public class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout meetingRowLayout;
        public View colorMarginV;
        public TextView meetingTitleTv;
        public TextView meetingDateTv;
        public TextView meetingTimeTv;
        public TextView meetingPublishDateTv;
        public TextView meetingAuthorTv;

        public ViewHolder(View itemView) {
            super(itemView);
            meetingRowLayout = itemView.findViewById(R.id.meeting_row_relativelayout);
            colorMarginV = itemView.findViewById(R.id.color_margin);
            meetingTitleTv = itemView.findViewById(R.id.meeting_title_textview);
            meetingDateTv = itemView.findViewById(R.id.meeting_date_textview);
            meetingTimeTv = itemView.findViewById(R.id.meeting_time_textview);
            meetingPublishDateTv = itemView.findViewById(R.id.meeting_publish_date);
            meetingAuthorTv = itemView.findViewById(R.id.meeting_author_textview);

        }
    }

    public MeetingAdapter(ArrayList<Meeting> meetings) {
        mMeetings = meetings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.meeting_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meeting meeting = mMeetings.get(position);

        if (position % 2 == 1) {
            holder.meetingRowLayout.setBackgroundColor(holder.colorMarginV.getContext()
                    .getResources().getColor(R.color.meeting_row_tint));
        }

        if (position % 3 == 0) {
            holder.colorMarginV.setBackgroundColor(Color.RED);
        } else if (position % 3 == 1) {
            holder.colorMarginV.setBackgroundColor(Color.GREEN);
        } else {
            holder.colorMarginV.setBackgroundColor(Color.BLUE);
        }

        holder.meetingTitleTv.setText(meeting.getMeetingTitle());
        holder.meetingTimeTv.setText(meeting.getMeetingTime());
        holder.meetingAuthorTv.setText(meeting.getCreatorEmail());
        holder.meetingPublishDateTv.setText(meeting.getMeetingPublishDate());
        holder.meetingDateTv.setText(meeting.getMeetingDate());
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }
}
