package com.dlamloi.MAD.home.update;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.activity.PostUpdateActivity;
import com.dlamloi.MAD.model.Group;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UpdateFragment extends Fragment {

    public static final String EXTRA_UPDATE = "updates";
    public static final String EXTRA_GROUP = "group";

    private RecyclerView mUpdatesRv;
    private Group mGroup;
    private UpdateAdapter mUpdateAdapter;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    public UpdateFragment() {

    }

    public static UpdateFragment newInstance(Group group) {
        UpdateFragment fragment = new UpdateFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_GROUP, group);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        if (extras != null) {
            mGroup = extras.getParcelable(EXTRA_GROUP);
        }

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("groups").child(mGroup.getId()).child("updates");

        mUpdateAdapter = new UpdateAdapter(getContext(), mDatabaseReference);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mUpdatesRv = view.findViewById(R.id.update_recyclerview);
        mUpdatesRv.setAdapter(mUpdateAdapter);
        mUpdatesRv.setLayoutManager(new LinearLayoutManager(getContext()));



        return view;

    }

}
