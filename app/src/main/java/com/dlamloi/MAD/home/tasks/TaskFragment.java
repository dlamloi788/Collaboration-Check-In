package com.dlamloi.MAD.home.tasks;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.home.GroupHomeActivity;
import com.dlamloi.MAD.model.Task;
import com.dlamloi.MAD.viewtask.ViewTaskActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment implements TaskContract.View {

    public static final int SPAN_COUNT = 2;
    public static final String TASK_ID = "task id";
    public static final String COMPLETE = "Complete";

    private RecyclerView mTasksRv;
    private String mGroupId;
    private TaskPresenter mTaskPresenter;
    private TaskAdapter mTaskAdapter;
    private ArrayList<Task> mTasks = new ArrayList<>();

    private TaskContract.TaskItemClickListener mTaskItemClickListener = taskId -> {
        Intent intent = new Intent(getActivity(), ViewTaskActivity.class);
        intent.putExtra(TASK_ID, taskId);
        intent.putExtra(GroupHomeActivity.GROUP_KEY, mGroupId);
        startActivity(intent);

    };

    public TaskFragment() {
        // Required empty public constructor
    }

    public static TaskFragment newInstance(String groupId) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putString(GroupHomeActivity.GROUP_KEY, groupId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        if (extras != null) {
            mGroupId = extras.getString(GroupHomeActivity.GROUP_KEY);
        }
        mTasks = new ArrayList<>();
        mTaskPresenter = new TaskPresenter(this, mGroupId);
        mTaskAdapter = new TaskAdapter(mTasks, mTaskItemClickListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        mTasksRv = view.findViewById(R.id.task_recyclerview);
        mTasksRv.setAdapter(mTaskAdapter);
        mTasksRv.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));

        mTasksRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mTaskPresenter.scrollStateChanged(newState);
            }
        });
        return view;
    }

    @Override
    public void notifyItemInserted(int position) {
        mTaskAdapter.notifyItemInserted(position);
    }

    @Override
    public void hideFab() {
        ((GroupHomeActivity)getActivity()).hideFab();
    }

    @Override
    public void showFab() {
        ((GroupHomeActivity)getActivity()).showFab();

    }

    @Override
    public void populateRecyclerView(ArrayList<Task> tasks) {
        if (!mTasks.isEmpty()) {
            mTasks.clear();
        }
        mTasks.addAll(tasks);
        notifyItemInserted(tasks.size());
    }

    @Override
    public ArrayList<Task> getTasks() {
        return mTasks;
    }

    @Override
    public void taskCompleted(int index) {
        mTasks.get(index).setStatus(COMPLETE);
        mTaskAdapter.notifyItemChanged(index);
    }
}
