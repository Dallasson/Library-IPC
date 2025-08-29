package com.app.common;

interface IJsonCallback {
    void onJsonReceived(String jsonData);
    void onFileReceived(in ParcelFileDescriptor fd);
}
