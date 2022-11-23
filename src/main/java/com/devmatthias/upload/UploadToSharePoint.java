package com.devmatthias.upload;

import com.devmatthias.auth.AuthenticationProvider;
import com.devmatthias.config.ApplicationProperties;
import com.microsoft.graph.concurrency.ChunkedUploadProvider;
import com.microsoft.graph.concurrency.IProgressCallback;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.models.extensions.DriveItem;
import com.microsoft.graph.models.extensions.DriveItemUploadableProperties;
import com.microsoft.graph.models.extensions.IGraphServiceClient;
import com.microsoft.graph.models.extensions.UploadSession;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UploadToSharePoint {

    private InputStream getInputStream() throws IOException {
        // Get an input stream for the file
        return new FileInputStream(ApplicationProperties.getUploadFile());
    }

    private long getStreamSize(InputStream fileStream) throws IOException {
        return fileStream.available();
    }

    // Create a callback used by the upload provider
    IProgressCallback<DriveItem> callback = new IProgressCallback<>() {
        @Override
        // Called after each slice of the file is uploaded
        public void progress(final long current, final long max) {
            System.out.printf("Uploaded %d bytes of %d total bytes%n", current, max);
        }

        @Override
        public void success(final DriveItem result) {
            System.out.printf("Uploaded file with ID: %s%n", result.id);
        }

        public void failure(final ClientException ex) {
            System.out.printf("Error uploading file: %s%n", ex.getMessage());
        }
    };

    public void setUploadSession() throws IOException {
        final IGraphServiceClient graphClient = new AuthenticationProvider().getAuthProvider();

        // upload to share point
        UploadSession uploadSession1 = graphClient
                .sites()
                .byId(ApplicationProperties.getSiteId())
                .drives(ApplicationProperties.getSiteDrive())
                .items(ApplicationProperties.getUploadPath())
                .itemWithPath("file.txt")
                .createUploadSession(new DriveItemUploadableProperties())
                .buildRequest()
                .post();

        ChunkedUploadProvider<DriveItem> chunkedUploadProvider =
                new ChunkedUploadProvider<>
                        (uploadSession1, graphClient, getInputStream(), getStreamSize(getInputStream()),
                                DriveItem.class);

        // Config parameter is an array of integers
        // customConfig[0] indicates the max slice size
        // Max slice size must be a multiple of 320 KiB
        int[] customConfig = { 320 * 1024 };

        // Do the upload
        chunkedUploadProvider.upload(callback, customConfig);
    }

}