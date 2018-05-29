package com.dlamloi.MAD.taskcreation;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.home.GroupHomeActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateTaskActivity extends AppCompatActivity implements CreateTaskContract.View {

    public static final String SPINNER_SELECT = "Spinner item selected";


    private CreateTaskPresenter mCreateTaskPresenter;
    private String mGroupId;
    private ArrayAdapter<String> mSpinnerAdapter;

    @BindView(R.id.assign_member_spinner)
    Spinner mAssignMemberSp;
    @BindView(R.id.task_title_edittext)
    EditText taskTitleEt;
    @BindView(R.id.task_duedate_edittext)
    EditText taskDueDatEt;
    @BindView(R.id.task_description_edittext)
    EditText taskDescriptionEt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getDrawable(R.drawable.cross_icon));
        ButterKnife.bind(this);
        mGroupId = getIntent().getStringExtra(GroupHomeActivity.GROUP_KEY);
        mSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        mAssignMemberSp.setAdapter(mSpinnerAdapter);

        mCreateTaskPresenter = new CreateTaskPresenter(this, mGroupId);
    }

    @OnClick(R.id.task_duedate_edittext)
    public void taskDueDateEtClick() {
        mCreateTaskPresenter.taskDateClicked();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                leave();
                break;

            case R.id.create_task_menu_button:
                String assignedMember = mAssignMemberSp.getSelectedItem().toString();
                String taskTitle = taskTitleEt.getText().toString();
                String dueDate = taskDueDatEt.getText().toString();
                String taskDescription = taskDescriptionEt.getText().toString();
                mCreateTaskPresenter.assignTask(assignedMember, taskTitle, dueDate, taskDescription);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_task_meeting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void setUpSpinnerData(ArrayList<String> displayNames) {
        mSpinnerAdapter.addAll(displayNames);
        mSpinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showDateDialog(int currentYear, int currentMonth, int currentDayOfMonth) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    mCreateTaskPresenter.datePicked(year, month, dayOfMonth);
                }, currentYear, currentMonth, currentDayOfMonth);

        datePickerDialog.show();
    }

    @Override
    public void setDueDate(String date) {
        taskDueDatEt.setText(date);
    }

    @Override
    public void leave() {
        finish();
    }

}
