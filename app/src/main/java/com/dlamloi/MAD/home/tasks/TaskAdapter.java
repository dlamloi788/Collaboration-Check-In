package com.dlamloi.MAD.home.tasks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.model.Task;
import com.dlamloi.MAD.taskcreation.CreateTaskActivity;
import com.dlamloi.MAD.taskcreation.CreateTaskPresenter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * Created by Don on 14/05/2018.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private ArrayList<Task> mTasks = new ArrayList<>();


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView statusIv;
        public TextView taskAssignedMemberTv;
        public TextView taskTitleTv;


        public ViewHolder(View itemView) {
            super(itemView);
            statusIv = itemView.findViewById(R.id.status_imageview);
            taskAssignedMemberTv = itemView.findViewById(R.id.task_assigned_member_textview);
            taskTitleTv = itemView.findViewById(R.id.task_title_textview);
        }

    }


    public TaskAdapter(ArrayList<Task> tasks) {
        this.mTasks = tasks;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.task_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = mTasks.get(position);

        String status = task.getStatus();
        int drawableReference;
        if (status.equals(CreateTaskPresenter.STATUS_PENDING)) {
            drawableReference = R.drawable.alarm_icon;
        } else if (status.equals(CreateTaskPresenter.STATUS_COMPLETE)) {
            drawableReference = R.drawable.complete_icon;

        } else{
            drawableReference = R.drawable.overdue_icon;
        }
        holder.statusIv.setImageResource(drawableReference);
        holder.taskAssignedMemberTv.setText(task.getAssignedMember());
        holder.taskTitleTv.setText(task.getTitle());



    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }



}
