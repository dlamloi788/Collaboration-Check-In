package com.dlamloi.MAD.updatecreation;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.dlamloi.MAD.home.GroupHomeActivity;
import com.dlamloi.MAD.home.update.UpdateFragment;
import com.dlamloi.MAD.R;
import com.dlamloi.MAD.model.Group;
import com.dlamloi.MAD.model.Update;
import com.dlamloi.MAD.viewgroups.ViewGroupsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class PostUpdateActivity extends AppCompatActivity implements PostUpdateContract.View {

    public static final String DATE_TOSTRING = "date toString";
    public static final String GROUP_ID = "Group ID";

    private PostUpdatePresenter mPostUpdatePresenter;

    private String mGroupId;

    @BindView(R.id.update_title_textinputlayout)
    TextInputLayout mTitleTextInputLayout;
    @BindView(R.id.update_title_edittext)
    EditText mUpdateTitleEt;
    @BindView(R.id.update_information_edittext)
    TextInputEditText mUpdateInformationEt;

    private Menu mMenu;


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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.create_update_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                leave();
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

    @OnTextChanged(R.id.update_title_edittext)
    public void updateTitleEtChange() {
        mPostUpdatePresenter.shouldPublishBeEnabled(mUpdateTitleEt.getText().toString());
    }


    @Override
    public void leave() {
        finish();
    }

    @Override
    public void disablePublishButton() {
        mMenu.findItem(R.id.publish_update).setEnabled(false);
    }

    @Override
    public void enablePublishButton() {
        mMenu.findItem(R.id.publish_update).setEnabled(true);
    }


}
