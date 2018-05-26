package com.dlamloi.MAD.home.files;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.home.GroupHomeActivity;
import com.dlamloi.MAD.home.update.UpdateFragment;
import com.dlamloi.MAD.model.File;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FileFragment extends Fragment {

    private String mGroupId;
    private ArrayList<File> mFiles;
    private FilePresenter mFilePresenter;
    private FileAdapter mFileAdapter;


    public FileFragment() {
    }

    public static FileFragment newInstance(String groupId) {
        FileFragment fragment = new FileFragment();
        Bundle args = new Bundle();
        args.putString(GroupHomeActivity.GROUP_KEY, groupId);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        if (extras != null) {
            mGroupId = extras.getString(GroupHomeActivity.GROUP_KEY);
        }
        mFiles = new ArrayList<>();
        

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_file, container, false);
    }

}
