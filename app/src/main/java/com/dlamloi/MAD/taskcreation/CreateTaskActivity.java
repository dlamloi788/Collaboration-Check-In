package com.dlamloi.MAD.taskcreation;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.home.GroupHomeActivity;
import com.dlamloi.MAD.utilities.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * This class is reponsible for displaying the UI of the create meeting activity
 */
public class CreateTaskActivity extends AppCompatActivity implements CreateTaskContract.View {

    public static final String SPINNER_SELECT = "Spinner item selected";


    private CreateTaskContract.Presenter mCreateTaskPresenter;
    private String mGroupId;
    private ArrayAdapter<String> mSpinnerAdapter;

    @BindView(R.id.assign_member_spinner)
    Spinner mAssignMemberSp;
    @BindView(R.id.task_title_edittext)
    EditText mTaskTitleEt;
    @BindView(R.id.task_duedate_edittext)
    EditText mTaskDueDatEt;
    @BindView(R.id.task_description_edittext)
    EditText mTaskDescriptionEt;

    private Menu mMenu;
    private AlertDialog mLeaveAlertDialog;

    /**
     * {@inheritDoc}
     */
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
        mLeaveAlertDialog = Utility.setUpLeaveAlertDialog(this, getString(R.string.quit_assigning_task));
        mAssignMemberSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mAssignMemberSp.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick(R.id.task_duedate_edittext)
    public void taskDueDateEtClick() {
        mCreateTaskPresenter.taskDateClicked();
    }


    @OnTextChanged(value = {R.id.task_title_edittext, R.id.task_duedate_edittext,
            R.id.task_description_edittext},
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void shouldAssignBeEnabled() {
        mCreateTaskPresenter.shouldAssignBeEnabled(
                (String) mAssignMemberSp.getSelectedItem(),
                mTaskTitleEt.getText().toString(),
                mTaskDueDatEt.getText().toString()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mCreateTaskPresenter.homeButtonPressed(mTaskTitleEt.getText().toString(),
                        mTaskDueDatEt.getText().toString()
                );
                break;

            case R.id.create_task_menu_button:
                String assignedMember = mAssignMemberSp.getSelectedItem().toString();
                String taskTitle = mTaskTitleEt.getText().toString();
                String dueDate = mTaskDueDatEt.getText().toString();
                String taskDescription = mTaskDescriptionEt.getText().toString();
                mCreateTaskPresenter.assignTask(assignedMember, taskTitle, dueDate, taskDescription);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_task_meeting, menu);
        mMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUpSpinnerData(ArrayList<String> displayNames) {
        mSpinnerAdapter.addAll(displayNames);
        mSpinnerAdapter.notifyDataSetChanged();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showDateDialog(int currentYear, int currentMonth, int currentDayOfMonth) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    mCreateTaskPresenter.datePicked(year, month, dayOfMonth);
                }, currentYear, currentMonth, currentDayOfMonth);

        datePickerDialog.show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDueDate(String date) {
        mTaskDueDatEt.setText(date);
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
    public void disableAssignButton() {
        mMenu.findItem(R.id.create_task_menu_button).setEnabled(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enableAssignButton() {
        mMenu.findItem(R.id.create_task_menu_button).setEnabled(true);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showAlertDialog() {
        mLeaveAlertDialog.show();
    }

}
