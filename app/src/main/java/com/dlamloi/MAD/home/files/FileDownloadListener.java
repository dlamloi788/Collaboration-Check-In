package com.dlamloi.MAD.home.files;

/**
 * Created by Don on 27/05/2018.
 */

public interface FileDownloadListener {

    void downloadFile(String name, String uri, String externalFilesDir);
}
