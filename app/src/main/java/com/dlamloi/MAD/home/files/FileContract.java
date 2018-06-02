package com.dlamloi.MAD.home.files;

import android.app.Activity;

import com.dlamloi.MAD.base.BasePresenter;
import com.dlamloi.MAD.base.BaseView;
import com.dlamloi.MAD.model.CloudFile;

/**
 * Created by Don on 24/05/2018.
 */

/**
 * This interface is a contract between the file view and presenter
 */
public interface FileContract {

    /**
     * The view associated with the contract
     */
    interface View extends BaseView<CloudFile> {

        /**
         * Shows the progressbar
         */
        void showProgressBar();

        /**
         * Hides the progressbar
         */
        void hideProgressBar();

        /**
         * Updates the progress on the progressbar
         *
         * @param currentProgress the progress of the progressbar
         */
        void updateProgressBar(int currentProgress);

        /**
         * Shows a toast stating that the download is complete
         */
        void showDownloadCompleteToast();
    }

    /**
     * The presenter associated with the contract
     */
    interface Presenter extends BasePresenter{

        /**
         * Calls the appropriate class to download a selected file
         *
         * @param activity the activity that called this method
         * @param name the name of the file
         * @param uri the uri of the file
         * @param downloadFolderDir the download folder of the device
         */
        void downloadFile(Activity activity, String name, String uri, String downloadFolderDir);
    }

    /**
     * Listens for new files being uploaded
     */
    interface newFileListener {

        /**
         * Notifies presenter that a file has been uploaded
         * @param file the file that has just been uploaded
         */
        void onFileUpload(CloudFile file);

    }

    /**
     * Listens for the download button click on the recyclerview
     */
    interface FileDownloadListener {

        /**
         * Calls the appropriate class to download a selected file
         *
         * @param name the name of the file
         * @param uri the uri of the file
         * @param downloadFolderDir the download folder of the device
         */
        void downloadFile(String name, String uri, String downloadFolderDir);
    }
}
