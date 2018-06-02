package com.dlamloi.MAD.home.files;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.home.GroupHomeActivity;
import com.dlamloi.MAD.model.CloudFile;

import java.util.ArrayList;

/**
 * This class is reponsible for displaying the file screen
 */
public class FileFragment extends Fragment implements FileContract.View {

    private String mGroupId;
    private ArrayList<CloudFile> mCloudFiles;
    private FileContract.Presenter mFilePresenter;
    private FileAdapter mFileAdapter;
    private RecyclerView mFileRv;
    private FileContract.FileDownloadListener mFileDownloadListener;


    public FileFragment() {
        //Empty constructor required
    }

    /**
     * Creates an instance of the file fragment
     *
     * @param groupId
     * @return
     */
    public static FileFragment newInstance(String groupId) {
        FileFragment fragment = new FileFragment();
        Bundle args = new Bundle();
        args.putString(GroupHomeActivity.GROUP_KEY, groupId);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        if (extras != null) {
            mGroupId = extras.getString(GroupHomeActivity.GROUP_KEY);
        }
        mCloudFiles = new ArrayList<>();
        mFilePresenter = new FilePresenter(this, mGroupId);
        mFileDownloadListener = (name, uri, downloadFolderDir) -> {
            mFilePresenter.downloadFile(getActivity(), name, uri, downloadFolderDir);
        };
        mFileAdapter = new FileAdapter(mCloudFiles, mFileDownloadListener);


    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file, container, false);
        mFileRv = view.findViewById(R.id.files_recyclerview);
        mFileRv.setAdapter(mFileAdapter);
        mFileRv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mFileRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mFilePresenter.scrollStateChanged(newState);
            }
        });
        return view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void populateRecyclerView(ArrayList<CloudFile> cloudFiles) {
        if (!mCloudFiles.isEmpty()) {
            mCloudFiles.clear();
        }
        mCloudFiles.addAll(cloudFiles);
        notifyItemInserted(cloudFiles.size());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyItemInserted(int position) {
        mFileAdapter.notifyItemInserted(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hideFab() {
        ((GroupHomeActivity)getActivity()).hideFab();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showFab() {
        ((GroupHomeActivity)getActivity()).showFab();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showProgressBar() {
        ((GroupHomeActivity)getActivity()).showDownloadProgressbar();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hideProgressBar() {
        ((GroupHomeActivity)getActivity()).hideDownloadProgressbar();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateProgressBar(int currentProgress) {
        ((GroupHomeActivity)getActivity()).updateDownloadProgressBar(currentProgress);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showDownloadCompleteToast() {
        Toast.makeText(getActivity(), R.string.download_complete, Toast.LENGTH_LONG).show();
    }
}

