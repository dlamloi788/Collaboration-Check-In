package com.dlamloi.MAD.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dlamloi.MAD.HomeFragment;
import com.dlamloi.MAD.R;
import com.dlamloi.MAD.model.Group;
import com.dlamloi.MAD.model.Update;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostUpdateActivity extends AppCompatActivity {

    public static final String DATE_TOSTRING = "date toString";

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private Group mGroup;
    @BindView(R.id.update_title_edittext) EditText mUpdateTitleEt;
    @BindView(R.id.update_information_edittext) EditText mUpdateInformationEt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("groups");

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mGroup = getIntent().getParcelableExtra(HomeFragment.EXTRA_GROUP);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.create_update_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.publish_update:
                publishUpdate();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void publishUpdate() {
        String updateTitle = mUpdateTitleEt.getText().toString();
        String updateInformation = mUpdateInformationEt.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMMM yyyy");
        String updateDate = dateFormat.format(Calendar.getInstance().getTime());
        String updatePublisher = mUser.getDisplayName();
        Log.d(DATE_TOSTRING, updateDate);
        Update update = new Update(updateTitle, updateDate, updateInformation, updatePublisher);
        mDatabaseReference.child(mGroup.getId()).child("updates").push().setValue(update);
        Toast.makeText(this, R.string.update_posted, Toast.LENGTH_SHORT).show();
        finish();
    }
}
