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

/**
 * This class displays a list as rows in a recyclerview
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private TaskContract.TaskItemClickListener mTaskItemClickListener;
    private ArrayList<Task> mTasks = new ArrayList<>();

    /**
     * Caches the task rows
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout taskRowContainer;
        public ImageView statusIv;
        public TextView taskAssignedMemberTv;
        public TextView taskTitleTv;
        public TextView taskDueDateTv;


        public ViewHolder(View itemView) {
            super(itemView);
            taskRowContainer = itemView.findViewById(R.id.task_row_container);
            statusIv = itemView.findViewById(R.id.status_imageview);
            taskAssignedMemberTv = itemView.findViewById(R.id.task_assigned_member_textview);
            taskTitleTv = itemView.findViewById(R.id.task_title_textview);
            taskDueDateTv = itemView.findViewById(R.id.task_due_date_textview);
        }

    }

    /**
     * Creates a new instance of the task adapter
     *
     * @param tasks the list of the tasks to be displayed as rows
     * @param taskItemClickListener the listener row recyclerview row taps
     */
    public TaskAdapter(ArrayList<Task> tasks, TaskContract.TaskItemClickListener taskItemClickListener) {
        this.mTasks = tasks;
        this.mTaskItemClickListener = taskItemClickListener;
    }


    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.task_row, parent, false);
        return new ViewHolder(view);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = mTasks.get(position);
        String status = task.getStatus();
        int drawableReference;
        if (status.equals(CreateTaskPresenter.STATUS_PENDING)) {
            drawableReference = R.drawable.alarm_icon;
        } else {
            drawableReference = R.drawable.complete_icon;
        }
        holder.statusIv.setImageResource(drawableReference);
        holder.taskAssignedMemberTv.setText(task.getAssignedMemberDisplayName());
        holder.taskTitleTv.setText(task.getTitle());
        holder.taskDueDateTv.setText(holder.itemView.getContext().getString(R.string.due, task.getDueDate()));
        holder.taskRowContainer.setOnClickListener(v -> mTaskItemClickListener.taskClick(task.getId()));


    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemCount() {
        return mTasks.size();
    }


}
