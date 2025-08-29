package com.app.common;

import com.app.common.IJsonCallback;

interface IJsonTransfer {

    void sendJson(String jsonData);
    void sendFile(in ParcelFileDescriptor fd);

    // Register/unregister callbacks
    void registerCallback(IJsonCallback cb);
    void unregisterCallback(IJsonCallback cb);
}
