package com.dlamloi.MAD.home.files;

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

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder>{

    private ArrayList<CloudFile> mCloudFiles = new ArrayList<>();
    private FileDownloadListener mFileDownloadListener;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView fileNameTv;
        public Button downloadBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            fileNameTv = itemView.findViewById(R.id.file_name_textview);
            downloadBtn = itemView.findViewById(R.id.download_file_button);
        }
    }


    public FileAdapter(ArrayList<CloudFile> cloudFiles, FileDownloadListener fileDownloadListener) {
        mCloudFiles = cloudFiles;
        mFileDownloadListener = fileDownloadListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.file_row, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CloudFile cloudFile = mCloudFiles.get(position);
        holder.fileNameTv.setText(cloudFile.getName());
        holder.downloadBtn.setOnClickListener(v -> mFileDownloadListener.downloadFile(cloudFile.getName(), cloudFile.getUri(),
                holder.downloadBtn.getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getPath()));
    }

    @Override
    public int getItemCount() {
        return mCloudFiles.size();
    }

}
