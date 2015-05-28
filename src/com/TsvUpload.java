package com;

import com.vaadin.ui.Upload;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by heumos on 5/28/15.
 */
public class TsvUpload implements Upload.Receiver {
    private int counter;
    private int total;
    private boolean sleep;
    public StringBuilder uploadBuilder = new StringBuilder();

    /**
     * return an OutputStream that simply counts lineends
     */
    @Override
    public OutputStream receiveUpload(final String filename,
                                      final String MIMEType) {
        System.out.println(filename);
        System.out.println(new File(filename).getAbsolutePath());

        counter = 0;
        total = 0;
        OutputStream outputStream =  new OutputStream() {
            private static final int searchedByte = '\n';
            @Override
            public void write(final int b) throws IOException {
                uploadBuilder.append((char) b);
                total++;
                if (b == searchedByte) {
                    counter++;
                }
                if (sleep && total % 1000 == 0) {
                    try {
                        Thread.sleep(100);
                    } catch (final InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        return outputStream;
    }

    public int getLineBreakCount() {
        return counter;
    }

    public void setSlow(final boolean value) {
        sleep = value;
    }

    public StringBuilder getUploadBuilder() {
        return this.uploadBuilder;
    }

}
