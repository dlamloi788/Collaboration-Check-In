package com.dlamloi.MAD.home.update;

import android.support.v7.widget.RecyclerView;

import com.dlamloi.MAD.model.Update;
import com.dlamloi.MAD.utilities.FirebaseCallbackManager;

import java.util.ArrayList;

/**
 * Created by Don on 16/05/2018.
 */

public class UpdatePresenter implements UpdateContract.Presenter, UpdateContract.UpdateListener {

    private final UpdateContract.View mView;
    private FirebaseCallbackManager mFirebaseCallbackManager;
    private ArrayList<Update> mUpdates = new ArrayList<>();


    public UpdatePresenter(UpdateContract.View view, String groupId) {
        mView = view;
        mFirebaseCallbackManager = new FirebaseCallbackManager(groupId);
        mFirebaseCallbackManager.attachUpdatesListener(this);

    }

    @Override
    public void onUpdateAdd(Update update) {
        mUpdates.add(update);
        mView.populateRecyclerView(mUpdates);
    }


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
