package com.dlamloi.MAD.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.dlamloi.MAD.R;
import com.dlamloi.MAD.activity.GroupHomeActivity;
import com.dlamloi.MAD.model.Group;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import ViewHolder.GroupViewHolder;

/**
 * Created by Don on 11/04/2018.
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupViewHolder> {

    private Context mContext;
    private ArrayList<Group> mGroupList = new ArrayList<>();
    private DatabaseReference mDatabaseReference;
    private FirebaseUser mUser;

    public GroupAdapter() {

    }

    public GroupAdapter(Context context, DatabaseReference databaseReference) {
        mContext = context;
        mDatabaseReference = databaseReference;
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        mDatabaseReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Group group = dataSnapshot.getValue(Group.class);
                if (isUserAMember(group)) {
                    mGroupList.add(group);
                }
                notifyItemInserted(mGroupList.size());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Group group = dataSnapshot.getValue(Group.class);
                if (!mGroupList.isEmpty()) {
                    int index = indexOfGroup(group);
                    mGroupList.set(index, group);
                    notifyItemChanged(index);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                int index = indexOfGroupWithKey(key);
                mGroupList.remove(index);
                notifyDataSetChanged();
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
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.group_item, parent, false);
        return new GroupViewHolder(view);

    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        Group group = mGroupList.get(position);
        holder.groupNameTv.setText(group.getName());
        holder.groupParentRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewGroupIntent = new Intent(mContext, GroupHomeActivity.class);
                viewGroupIntent.putExtra(GroupHomeActivity.GROUP_KEY, group);
                mContext.startActivity(viewGroupIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mGroupList.size();
    }


    private int indexOfGroupWithKey(String key) {
        int index = 0;
        for (Group group : mGroupList) {
            if (group.getId().equals(key)) {
                return index;
            }
            ++index;
        }
        return -1;
    }

    private int indexOfGroup(Group newGroup) {
        int index = 0;
        for (Group group : mGroupList) {
            if (group.getId().equals(newGroup.getId())) {
                return index;
            }
            ++index;
        }
        return -1;
    }

    private boolean isUserAMember(Group group) {
        String currentUserEmail = mUser.getEmail();
        if (group.getAdminEmail().equals(currentUserEmail)) {
            return true;
        }

        for (String email : group.getMemberEmails()) {
            if (currentUserEmail.equals(email)) {
                return true;
            }
        }

        return false;
    }
}
