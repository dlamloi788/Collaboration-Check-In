package com.dlamloi.MAD.home.meetings;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.model.Meeting;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Don on 10/05/2018.
 */

/**
 * This class displays the list of meetings in rows of a recyclerview
 */
public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder> {

    private ArrayList<Meeting> mMeetings;
    private MeetingContract.MeetingItemClickListener mMeetingItemClickListener;

    /**
     * Caches the rows the recyclerview
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout meetingRowLayout;
        public View colorMarginV;
        public TextView meetingTitleTv;
        public TextView meetingDateTv;
        public TextView meetingTimeTv;
        public TextView meetingLocationTv;
        public TextView meetingAuthorTv;

        public ViewHolder(View itemView) {
            super(itemView);
            meetingRowLayout = itemView.findViewById(R.id.meeting_row_relativelayout);
            colorMarginV = itemView.findViewById(R.id.color_margin);
            meetingTitleTv = itemView.findViewById(R.id.meeting_title_textview);
            meetingDateTv = itemView.findViewById(R.id.meeting_date_textview);
            meetingTimeTv = itemView.findViewById(R.id.meeting_time_textview);
            meetingLocationTv = itemView.findViewById(R.id.meeting_publish_date);
            meetingAuthorTv = itemView.findViewById(R.id.meeting_author_textview);

        }
    }

    /**
     * Creates a new instance of the meeting adapter
     *
     * @param meetings the meetings to displayed in the recyclerview
     * @param meetingItemClickListener the recyclerview row tap listener
     */
    public MeetingAdapter(ArrayList<Meeting> meetings, MeetingContract.MeetingItemClickListener meetingItemClickListener) {
        mMeetings = meetings;
        mMeetingItemClickListener = meetingItemClickListener;
    }

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.meeting_row, parent, false);
        return new ViewHolder(view);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meeting meeting = mMeetings.get(position);

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
        holder.meetingLocationTv.setText(meeting.getMeetingLocation());
        holder.meetingDateTv.setText(meeting.getMeetingDate());
        holder.meetingRowLayout.setOnClickListener(v -> mMeetingItemClickListener.meetingClick(meeting.getId()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    /**
     * Sorts the meeting list by date in ascending order
     */
    public void sort() {
        Collections.sort(mMeetings, new Meeting.MeetingComparator());
    }



}
