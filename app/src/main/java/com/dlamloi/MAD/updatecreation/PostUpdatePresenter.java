package com.dlamloi.MAD.updatecreation;

import com.dlamloi.MAD.model.Update;
import com.dlamloi.MAD.utilities.FirebaseAuthenticationManager;
import com.dlamloi.MAD.repo.FirebaseRepositoryManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Don on 17/05/2018.
 */

public class PostUpdatePresenter implements PostUpdateContract.Presenter {

    private final PostUpdateContract.View mView;
    private FirebaseRepositoryManager mFirebaseRepositoryManager;
    private FirebaseAuthenticationManager mFirebaseAuthenticationManager;


    public PostUpdatePresenter(PostUpdateContract.View view, String groupId) {
        mView = view;
        mFirebaseRepositoryManager = new FirebaseRepositoryManager(groupId);
        mFirebaseAuthenticationManager = new FirebaseAuthenticationManager();

    }

    @Override
    public void publishUpdate(String updateTitle, String updateInformation) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        String updateDate = dateFormat.format(Calendar.getInstance().getTime());
        String updatePublisher = mFirebaseAuthenticationManager.getCurrentUserDisplayName();
        Update update = new Update(updateTitle, updateDate, updateInformation, updatePublisher);
        mFirebaseRepositoryManager.addUpdate(update);
        mView.leave();


    }

    @Override
    public void shouldPublishBeEnabled(String updateTitle) {
        if (updateTitle.isEmpty()) {
            mView.disablePublishButton();
        } else {
            mView.enablePublishButton();
        }
    }
}
