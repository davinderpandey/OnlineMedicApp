package com.jaiselrahman.filepicker.model;

import android.net.Uri;

public class FileImage {
    private Uri uri;

    public Uri uri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    private String name;

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String size;

    public String size() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    private String dateTaken;

    public String dateTaken() {
        return dateTaken;
    }

    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }
}
