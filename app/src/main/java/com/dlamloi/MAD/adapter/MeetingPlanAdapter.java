package com.dlamloi.MAD.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dlamloi.MAD.model.MeetingPlan;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Don on 10/05/2018.
 */

public class MeetingPlanAdapter extends RecyclerView.Adapter<MeetingPlanAdapter.ViewHolder>{

    private ArrayList<MeetingPlan> mMeetingPlans = new ArrayList<>();
    private Context mContext;
    private DatabaseReference mDatabaseReference;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

    }

    public MeetingPlanAdapter(Context context, DatabaseReference databaseReference) {
        this.mContext = context;
        this.mDatabaseReference = databaseReference;

        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MeetingPlan meetingPlan = dataSnapshot.getValue(MeetingPlan.class);
                mMeetingPlans.add(meetingPlan);
                notifyItemInserted(mMeetingPlans.size());
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
