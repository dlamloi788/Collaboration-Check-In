package com.dlamloi.MAD.home.tasks;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.base.BaseView;
import com.dlamloi.MAD.model.Task;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment implements BaseView<Task> {

    public static final int SPAN_COUNT = 2;
    private RecyclerView mTasksRv;

    public TaskFragment() {
        // Required empty public constructor
    }

    public static TaskFragment newInstance() {
        TaskFragment fragment = new TaskFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task, container, false);
        mTasksRv = view.findViewById(R.id.task_recyclerview);
        mTasksRv.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));


        return view;
    }

    @Override
    public void setRecyclerViewData(ArrayList<Task> t) {

    }

    @Override
    public void notifyItemInserted(int position) {

    }
}
