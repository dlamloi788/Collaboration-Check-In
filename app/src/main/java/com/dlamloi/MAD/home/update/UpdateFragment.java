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
import com.dlamloi.MAD.home.GroupHomeActivity;
import com.dlamloi.MAD.model.Update;

import java.util.ArrayList;

/**
 * This class is responsible for displaying the update screen
 */
public class UpdateFragment extends Fragment implements UpdateContract.View {


    private RecyclerView mUpdatesRv;
    private String mGroupId;
    private UpdateAdapter mUpdateAdapter;
    private UpdateContract.Presenter mUpdatePresenter;
    private ArrayList<Update> mUpdates;

    public UpdateFragment() {
        //Empty constructor required
    }

    /**
     * Creates a new instance of the update fragment
     *
     * @param groupId the id of the group the user is currently in
     * @return an instance of the update fragment
     */
    public static UpdateFragment newInstance(String groupId) {
        UpdateFragment fragment = new UpdateFragment();
        Bundle args = new Bundle();
        args.putString(GroupHomeActivity.GROUP_KEY, groupId);
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        if (extras != null) {
            mGroupId = extras.getString(GroupHomeActivity.GROUP_KEY);
        }
        mUpdates = new ArrayList<>();
        mUpdatePresenter = new UpdatePresenter(this, mGroupId);
        mUpdateAdapter = new UpdateAdapter(mUpdates);
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mUpdatesRv = view.findViewById(R.id.update_recyclerview);
        mUpdatesRv.setAdapter(mUpdateAdapter);
        mUpdatesRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mUpdatesRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mUpdatePresenter.scrollStateChanged(newState);
            }
        });
        return view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void populateRecyclerView(ArrayList<Update> updates) {
        if (!mUpdates.isEmpty()) {
            mUpdates.clear();
        }
        mUpdates.addAll(updates);
        notifyItemInserted(updates.size());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyItemInserted(int position) {
        mUpdateAdapter.notifyItemInserted(position);
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
        ((GroupHomeActivity)getActivity()).showFab();

    }


}
