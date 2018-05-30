package com.dlamloi.MAD.home.tasks;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.model.Task;
import com.dlamloi.MAD.taskcreation.CreateTaskPresenter;

import java.util.ArrayList;


/**
 * Created by Don on 14/05/2018.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private TaskContract.TaskItemClickListener mTaskItemClickListener;
    private ArrayList<Task> mTasks = new ArrayList<>();


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout mTaskRowContainer;
        public ImageView mStatusIv;
        public TextView mTaskAssignedMemberTv;
        public TextView mTaskTitleTv;
        public TextView mTaskDueDateTv;


        public ViewHolder(View itemView) {
            super(itemView);

            mTaskRowContainer = itemView.findViewById(R.id.task_row_container);
            mStatusIv = itemView.findViewById(R.id.status_imageview);
            mTaskAssignedMemberTv = itemView.findViewById(R.id.task_assigned_member_textview);
            mTaskTitleTv = itemView.findViewById(R.id.task_title_textview);
            mTaskDueDateTv = itemView.findViewById(R.id.task_due_date_textview);
        }

    }


    public TaskAdapter(ArrayList<Task> tasks, TaskContract.TaskItemClickListener taskItemClickListener) {
        this.mTasks = tasks;
        this.mTaskItemClickListener = taskItemClickListener;
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

        } else {
            drawableReference = R.drawable.overdue_icon;
        }
        holder.mStatusIv.setImageResource(drawableReference);
        holder.mTaskAssignedMemberTv.setText(task.getAssignedMember());
        holder.mTaskTitleTv.setText(task.getTitle());
        holder.mTaskDueDateTv.setText(holder.itemView.getContext().getString(R.string.due, task.getDueDate()));
        holder.mTaskRowContainer.setOnClickListener(v -> mTaskItemClickListener.taskClick(task.getId()));


    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }


}
