package com.dlamloi.MAD.home.files;

import android.app.Activity;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.model.CloudFile;

import java.util.ArrayList;
/**
 * Created by Don on 24/05/2018.
 */

/**
 * This class displays the list files in rows of a recyclerview
 */
public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder>{

    private ArrayList<CloudFile> mCloudFiles = new ArrayList<>();
    private FileContract.FileDownloadListener mFileDownloadListener;

    /**
     * Caches the file rows of the recyclerview
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView fileNameTv;
        public Button downloadBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            fileNameTv = itemView.findViewById(R.id.file_name_textview);
            downloadBtn = itemView.findViewById(R.id.download_file_button);
        }
    }

    /**
     * Creates an instance of the file adapter
     *
     * @param cloudFiles the list of files to display
     * @param fileDownloadListener the listener for download button clicks
     */
    public FileAdapter(ArrayList<CloudFile> cloudFiles, FileContract.FileDownloadListener fileDownloadListener) {
        mCloudFiles = cloudFiles;
        mFileDownloadListener = fileDownloadListener;

    }

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.file_row, parent, false);
        return new ViewHolder(view);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CloudFile cloudFile = mCloudFiles.get(position);
        holder.fileNameTv.setText(cloudFile.getName());
        holder.downloadBtn.setOnClickListener(v -> mFileDownloadListener.downloadFile(
                cloudFile.getName(), cloudFile.getUri(),
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemCount() {
        return mCloudFiles.size();
    }

}
