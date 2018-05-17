package com.dlamloi.MAD.updatecreation;

import com.dlamloi.MAD.model.Update;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Don on 17/05/2018.
 */

public class PostUpdatePresenter implements PostUpdateContract.Presenter {

    private final PostUpdateContract.View mView;
    private DatabaseReference mDatabaseReference;
    private FirebaseUser mUser;



    public PostUpdatePresenter(PostUpdateContract.View view, String groupId) {
        mView = view;
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("groups").child(groupId).child("updates");
        mUser = FirebaseAuth.getInstance().getCurrentUser();

    }

    @Override
    public void publishUpdate(String updateTitle, String updateInformation) {
        String id = mDatabaseReference.push().getKey();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        String updateDate = dateFormat.format(Calendar.getInstance().getTime());
        String updatePublisher = mUser.getDisplayName();
        Update update = new Update(id, updateTitle, updateDate, updateInformation, updatePublisher);
        mDatabaseReference.child(id).setValue(update);
        mView.leave();

    }
}
