package com.dlamloi.MAD.updatecreation;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.dlamloi.MAD.home.GroupHomeActivity;
import com.dlamloi.MAD.R;
import com.dlamloi.MAD.utilities.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

/**
 * This class is responsible for setting up and displaying the UI
 */
public class PostUpdateActivity extends AppCompatActivity implements PostUpdateContract.View {

    private PostUpdateContract.Presenter mPostUpdatePresenter;

    private String mGroupId;

    @BindView(R.id.update_title_textinputlayout)
    TextInputLayout mTitleTextInputLayout;
    @BindView(R.id.update_title_edittext)
    EditText mUpdateTitleEt;
    @BindView(R.id.update_information_edittext)
    TextInputEditText mUpdateInformationEt;

    private Menu mMenu;
    private AlertDialog mLeaveAlertDialog;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getDrawable(R.drawable.cross_icon));
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        mGroupId = getIntent().getStringExtra(GroupHomeActivity.GROUP_KEY);
        mPostUpdatePresenter = new PostUpdatePresenter(this, mGroupId);
        mLeaveAlertDialog = Utility.setUpLeaveAlertDialog(this, getString(R.string.quit_posting_update));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.create_update_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mPostUpdatePresenter.homeButtonPressed(mUpdateTitleEt.getText().toString());
                break;

            case R.id.publish_update:
                item.setEnabled(false);
                String updateTitle = mUpdateTitleEt.getText().toString();
                String updateInformation = mUpdateInformationEt.getText().toString();
                mPostUpdatePresenter.publishUpdate(updateTitle, updateInformation);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBackPressed() {
        mPostUpdatePresenter.homeButtonPressed(mUpdateTitleEt.getText().toString());
    }

    /**
     * Calls the presenter to determine whether publish button should be enabled
     */
    @OnTextChanged(R.id.update_title_edittext)
    public void updateTitleEtChange() {
        mPostUpdatePresenter.shouldPublishBeEnabled(mUpdateTitleEt.getText().toString());
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
    public void disablePublishButton() {
        mMenu.findItem(R.id.publish_update).setEnabled(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enablePublishButton() {
        mMenu.findItem(R.id.publish_update).setEnabled(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showAlertDialog() {
        mLeaveAlertDialog.show();
    }


}
