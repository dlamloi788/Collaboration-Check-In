package com.dlamloi.MAD.taskcreation;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.home.GroupHomeActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;

public class CreateTaskActivity extends AppCompatActivity implements CreateTaskContract.View {

    private CreateTaskPresenter mCreateTaskPresenter;
    private String mGroupId;
    private ArrayAdapter<String> mSpinnerAdapter;
    private ArrayList<String> mStrings;

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
        mCreateTaskPresenter = new CreateTaskPresenter(this, mGroupId);
        mCreateTaskPresenter.loadSpinnerData(mGroupId);

    }

    @OnClick(R.id.task_duedate_edittext)
    public void taskDueDateEtClick() {
        mCreateTaskPresenter.taskDateClicked();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.create_task_menu_button:
                String assignedMember = "dlamloi2415@gmail.com"; //Until spinner works use some dummy data
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
    public void showSpinnerData(ArrayList<String> userEmails) {
        mSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, userEmails);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAssignMemberSp.setAdapter(mSpinnerAdapter);
        mAssignMemberSp.setSelection(0);

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


}
