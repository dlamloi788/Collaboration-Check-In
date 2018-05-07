package com.dlamloi.MAD;

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

import com.dlamloi.MAD.activity.PostUpdateActivity;
import com.dlamloi.MAD.adapter.UpdateAdapter;
import com.dlamloi.MAD.model.Group;
import com.dlamloi.MAD.model.Update;

import java.util.ArrayList;
import java.util.HashMap;


public class HomeFragment extends Fragment {

    public static final String EXTRA_UPDATE = "updates";
    public static final String EXTRA_GROUP = "group";

    private RecyclerView mUpdatesRecyclerView;
    private ArrayList<Update> mUpdates = new ArrayList<>();
    private Group mGroup;

    public HomeFragment() {

    }

    public static HomeFragment newInstance(HashMap<String, Update> updates, Group group) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_UPDATE, updates);
        args.putParcelable(EXTRA_GROUP, group);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        HashMap<String, Update> updateMap = (HashMap<String, Update>) extras.getSerializable(EXTRA_UPDATE);
        for (Update item : updateMap.values()) {
            mUpdates.add(item);
        }
        mGroup = extras.getParcelable(EXTRA_GROUP);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mUpdatesRecyclerView = view.findViewById(R.id.update_recyclerview);
        UpdateAdapter updateAdapter =  new UpdateAdapter(getContext(), mUpdates);
        mUpdatesRecyclerView.setAdapter(updateAdapter);
        mUpdatesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FloatingActionButton fab = view.findViewById(R.id.add_post_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent postActivityIntent = new Intent(getContext(), PostUpdateActivity.class);
                postActivityIntent.putExtra(EXTRA_GROUP, mGroup);
                startActivity(postActivityIntent);
            }
        });

        return view;

    }


}
