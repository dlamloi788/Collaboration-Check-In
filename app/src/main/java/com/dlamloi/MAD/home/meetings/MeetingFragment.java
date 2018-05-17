package com.dlamloi.MAD.home.meetings;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.meetingcreation.CreateMeetingActivity;
import com.dlamloi.MAD.home.GroupHomeActivity;
import com.dlamloi.MAD.model.Group;
import com.dlamloi.MAD.model.Meeting;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeetingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeetingFragment extends Fragment implements MeetingContract.View {

    public static final String GROUP_KEY = "group";

    private RecyclerView mMeetingPlansRv;
    private Group mGroup;
    private MeetingPresenter mMeetingPresenter;
    private MeetingAdapter mMeetingAdapter;


    public MeetingFragment() {
        // Required empty public constructor
    }

    //Returns an instance of MeetingFragment
    public static MeetingFragment newInstance(Group group) {
        MeetingFragment meetingFragment = new MeetingFragment();
        Bundle args = new Bundle();
        args.putParcelable(GroupHomeActivity.GROUP_KEY, group);
        meetingFragment.setArguments(args);
        return meetingFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        if (extras != null) {
            mGroup = extras.getParcelable(GroupHomeActivity.GROUP_KEY);
        }
        mMeetingPresenter = new MeetingPresenter(this, mGroup.getId());
        mMeetingPresenter.loadAdapterData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting, container, false);
        mMeetingPlansRv = view.findViewById(R.id.meeting_plans_recyclerview);
        mMeetingPlansRv.setAdapter(mMeetingAdapter);
        mMeetingPlansRv.setLayoutManager(new LinearLayoutManager(view.getContext()));

        FloatingActionButton scheduleMeetingFab = view.findViewById(R.id.schedule_meeting_button);
        scheduleMeetingFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scheduleMeetingIntent = new Intent(getContext(), CreateMeetingActivity.class);
                scheduleMeetingIntent.putExtra(GROUP_KEY, mGroup);
                startActivity(scheduleMeetingIntent);
            }
        });


        return view;
    }

    @Override
    public void setRecyclerViewData(ArrayList<Meeting> meetings) {
        mMeetingAdapter = new MeetingAdapter(meetings);
    }

    @Override
    public void notifyItemInserted(int position) {
        mMeetingAdapter.notifyItemInserted(position);
    }
}
