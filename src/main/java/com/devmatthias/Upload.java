package com.devmatthias;

import com.devmatthias.upload.UploadToSharePoint;

import java.io.IOException;

public class Upload {

    public static void main(String[] args) throws IOException {
        UploadToSharePoint uploadToSharePoint = new UploadToSharePoint();
        uploadToSharePoint.setUploadSession();
    }
}
