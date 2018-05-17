package com.dlamloi.MAD.home.update;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.home.GroupHomeContract;
import com.dlamloi.MAD.model.Group;
import com.dlamloi.MAD.model.Update;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class UpdateFragment extends Fragment implements UpdateContract.View {

    public static final String EXTRA_GROUP = "group";

    private RecyclerView mUpdatesRv;
    private String mGroupId;
    private UpdateAdapter mUpdateAdapter;
    private UpdatePresenter mUpdatePresenter;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    public UpdateFragment() {
    }

    public static UpdateFragment newInstance(String groupId) {
        UpdateFragment fragment = new UpdateFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_GROUP, groupId);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        if (extras != null) {
            mGroupId = extras.getString(EXTRA_GROUP);
        }
        mUpdatePresenter = new UpdatePresenter(this, mGroupId);
        mUpdatePresenter.loadAdapterData();
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

    @Override
    public void setRecyclerViewData(ArrayList<Update> updates) {
        mUpdateAdapter = new UpdateAdapter(updates);
    }

    @Override
    public void notifyItemInserted(int position) {
        mUpdateAdapter.notifyItemInserted(position);
    }
}
