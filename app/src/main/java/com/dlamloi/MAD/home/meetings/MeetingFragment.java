package com.dlamloi.MAD.home.meetings;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.home.GroupHomeActivity;
import com.dlamloi.MAD.model.Meeting;
import com.dlamloi.MAD.viewmeeting.ViewMeetingActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * This class is responsible for displaying the meeting screen
 */
public class MeetingFragment extends Fragment implements MeetingContract.View {

    public static final String MEETING_ID = "Meeting id";

    private RecyclerView mMeetingPlansRv;
    private String mGroupId;
    private MeetingContract.Presenter mMeetingPresenter;
    private MeetingAdapter mMeetingAdapter;
    private ArrayList<Meeting> mMeetings;
    private MeetingContract.MeetingItemClickListener mMeetingItemClickListener = meetingId -> {
        Intent intent = new Intent(getActivity(), ViewMeetingActivity.class);
        intent.putExtra(GroupHomeActivity.GROUP_KEY, mGroupId);
        intent.putExtra(MEETING_ID, meetingId);
        startActivity(intent);
    };


    public MeetingFragment() {
        // Required empty public constructor
    }

    //Returns an instance of MeetingFragment
    public static MeetingFragment newInstance(String groupId) {
        MeetingFragment meetingFragment = new MeetingFragment();
        Bundle args = new Bundle();
        args.putString(GroupHomeActivity.GROUP_KEY, groupId);
        meetingFragment.setArguments(args);
        return meetingFragment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        if (extras != null) {
            mGroupId = extras.getString(GroupHomeActivity.GROUP_KEY);
        }
        mMeetings = new ArrayList<>();
        mMeetingPresenter = new MeetingPresenter(this, mGroupId);
        mMeetingAdapter = new MeetingAdapter(mMeetings, mMeetingItemClickListener);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting, container, false);

        mMeetingPlansRv = view.findViewById(R.id.meeting_plans_recyclerview);
        mMeetingPlansRv.setAdapter(mMeetingAdapter);
        mMeetingPlansRv.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mMeetingPlansRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mMeetingPresenter.scrollStateChanged(newState);
            }
        });
        return view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void populateRecyclerView(ArrayList<Meeting> meetings) {
        if (!mMeetings.isEmpty()) {
            mMeetings.clear();
        }
        mMeetings.addAll(meetings);
        notifyItemInserted(meetings.size());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyItemInserted(int position) {
        mMeetingAdapter.sort();
        mMeetingAdapter.notifyItemRangeChanged(0, position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hideFab() {
        ((GroupHomeActivity) getActivity()).hideFab();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showFab() {
        ((GroupHomeActivity) getActivity()).showFab();

    }
}
