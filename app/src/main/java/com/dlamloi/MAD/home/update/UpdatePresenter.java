package com.dlamloi.MAD.home.update;

import android.support.v7.widget.RecyclerView;

import com.dlamloi.MAD.model.Update;
import com.dlamloi.MAD.firebasemanager.FirebaseCallbackManager;

import java.util.ArrayList;

/**
 * Created by Don on 16/05/2018.
 */

/**
 * This class handles the presentation logic and listeners for updates being added to the database
 */
public class UpdatePresenter implements UpdateContract.Presenter, UpdateContract.UpdateListener {

    private final UpdateContract.View mView;
    private FirebaseCallbackManager mFirebaseCallbackManager;
    private ArrayList<Update> mUpdates = new ArrayList<>();

    /**
     * Creates a new instance of the update presenter
     *
     * @param view    the view that the presenter is moderating
     * @param groupId the id of the group that the user is currently in
     */
    public UpdatePresenter(UpdateContract.View view, String groupId) {
        mView = view;
        mFirebaseCallbackManager = new FirebaseCallbackManager(groupId);
        mFirebaseCallbackManager.attachUpdatesListener(this);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onUpdateAdd(Update update) {
        mUpdates.add(update);
        mView.populateRecyclerView(mUpdates);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scrollStateChanged(int state) {
        switch (state) {
            case RecyclerView.SCROLL_STATE_IDLE:
                mView.showFab();
                break;

            default:
                mView.hideFab();
                break;
        }
    }
}
