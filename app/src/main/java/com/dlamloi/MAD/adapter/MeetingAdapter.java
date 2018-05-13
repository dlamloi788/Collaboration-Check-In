package com.dlamloi.MAD.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.model.Meeting;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import ViewHolder.MeetingViewHolder;

/**
 * Created by Don on 10/05/2018.
 */

public class MeetingAdapter extends RecyclerView.Adapter<MeetingViewHolder>{

    private ArrayList<Meeting> mMeetings = new ArrayList<>();
    private Context mContext;
    private DatabaseReference mDatabaseReference;

    public MeetingAdapter(Context context, DatabaseReference databaseReference) {
        this.mContext = context;
        this.mDatabaseReference = databaseReference;

        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Meeting meeting = dataSnapshot.getValue(Meeting.class);
                mMeetings.add(meeting);
                notifyItemInserted(mMeetings.size());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.meeting_row, parent, false);
        return new MeetingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {
        Meeting meeting = mMeetings.get(position);

        if (position % 2 == 1) {
            holder.meetingRowLayout.setBackgroundColor(mContext.getResources().getColor(R.color.meeting_row_tint));
        }

        holder.meetingTitleTv.setText(meeting.getMeetingTitle());
        holder.meetingTimeTv.setText(meeting.getMeetingTime());
        holder.meetingSuburbTv.setText(meeting.getMeetingSuburb());
        holder.meetingAuthorTv.setText(meeting.getCreatorEmail());
        holder.meetingDateTv.setText(meeting.getMeetingDate());
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }
}
