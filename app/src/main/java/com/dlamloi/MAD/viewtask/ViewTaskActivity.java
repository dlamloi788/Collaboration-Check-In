package com.dlamloi.MAD.viewtask;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.home.GroupHomeActivity;
import com.dlamloi.MAD.home.tasks.TaskFragment;
import com.dlamloi.MAD.utilities.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This class is responsible for displaying the UI of viewing tasks
 */
public class ViewTaskActivity extends AppCompatActivity implements ViewTaskContract.View{

    @BindView(R.id.view_task_title_textview)
    TextView mViewTaskTitleTv;
    @BindView(R.id.view_task_description_textview)
    TextView mViewTaskDescriptionTv;
    @BindView(R.id.view_task_due_date_textview)
    TextView mViewTaskDueDateTv;

    private String mTaskId;
    private String mGroupId;
    private ViewTaskPresenter mViewTaskPresenter;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.cross_icon);
        setTitle(getString(R.string.view_task));
        ButterKnife.bind(this);
        mTaskId = getIntent().getStringExtra(TaskFragment.TASK_ID);
        mGroupId = getIntent().getStringExtra(GroupHomeActivity.GROUP_KEY);
        mViewTaskPresenter = new ViewTaskPresenter(this, mGroupId, mTaskId);
        mViewTaskPresenter.initTaskData();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_task_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mViewTaskPresenter.leave();
                break;

            case R.id.task_completed_button:
                mViewTaskPresenter.taskCompleted();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void bindTaskData(String title, String detail, String dueDate) {
        mViewTaskTitleTv.setText(title);
        mViewTaskDescriptionTv.setText(detail);
        mViewTaskDueDateTv.setText(dueDate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void leave() {
        finish();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showTaskCompleteError() {
        Utility.showToast(this, getString(R.string.not_assigned_to_task));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showTaskCompleteToast() {
        Utility.showToast(this, "Great Job! Task Complete");
    }
}
