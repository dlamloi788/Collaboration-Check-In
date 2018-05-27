package com.dlamloi.MAD.home.files;

import com.dlamloi.MAD.base.BaseView;
import com.dlamloi.MAD.model.CloudFile;

/**
 * Created by Don on 24/05/2018.
 */

public interface FileContract {

    interface View extends BaseView<CloudFile> {

    }

    interface Presenter {

        void downloadFile(String name, String uri, String externalFilesDir);
    }

    interface Interactor {

    }

    interface FileListener {

        void onFileUpload(CloudFile file);

    }
}
