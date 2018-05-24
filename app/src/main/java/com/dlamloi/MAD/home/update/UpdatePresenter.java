package com.dlamloi.MAD.home.update;

import android.util.Log;

import com.dlamloi.MAD.model.Update;

import java.util.ArrayList;

/**
 * Created by Don on 16/05/2018.
 */

public class UpdatePresenter implements UpdateContract.Presenter, UpdateContract.OnUpdateListener {

    private final UpdateContract.View mView;
    private UpdateInteractor mUpdateInteractor;


    public UpdatePresenter(UpdateContract.View view, String groupId) {
        mView = view;
        mUpdateInteractor = new UpdateInteractor(this, groupId);
        mUpdateInteractor.onAttach();
    }

    @Override
    public void onUpdateAdd(ArrayList<Update> updates) {
        mView.populateRecyclerView(updates);
    }
}
