package com.dlamloi.MAD;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dlamloi.MAD.adapter.UpdateAdapter;
import com.dlamloi.MAD.model.Update;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    public static final String EXTRA_UPDATE = "updates";

    private RecyclerView mUpdatesRecyclerView;
    private ArrayList<Update> mUpdates = new ArrayList<>();

    public HomeFragment() {

    }

    public static HomeFragment newInstance(ArrayList<Update> updates) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(EXTRA_UPDATE, updates);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        mUpdates = extras.getParcelableArrayList(EXTRA_UPDATE);



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

            }
        });

        return view;

    }


}
