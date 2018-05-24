package com.dlamloi.MAD.home.update;

import com.dlamloi.MAD.model.Update;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Don on 22/05/2018.
 */

public class UpdateInteractor implements UpdateContract.Interactor {

    private DatabaseReference mDatabaseReference;
    private UpdateContract.OnUpdateListener mOnUpdateListener;
    private ArrayList<Update> mUpdates = new ArrayList<>();


    public UpdateInteractor(UpdateContract.OnUpdateListener OnUpdateListener, String groupId) {
        mOnUpdateListener = OnUpdateListener;
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("groups").child(groupId).child("updates");
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Update update = dataSnapshot.getValue(Update.class);
                mUpdates.add(update);
                mOnUpdateListener.onUpdateAdd(mUpdates);
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

    @Override
    public void onAttach() {


    }
}
