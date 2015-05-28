package com;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.ui.UI;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.Label;
import com.vaadin.ui.Upload.StartedEvent;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;


@Title("UploadTest")
@Theme("valo")

/**
 * Created by heumos on 5/27/15.
 */
public class UploadTest extends UI {

    @Override
    public void init(VaadinRequest request) {

        // configure Components
        TsvUpload tsvUpload = new TsvUpload();
        tsvUpload.setSlow(true);

        Upload sample = new Upload(null, tsvUpload);
        final UploadInfoWindow uploadInfoWindow = new UploadInfoWindow(sample, tsvUpload);
        sample.setImmediate(false);
        sample.setButtonCaption("Upload File");
        sample.addStartedListener(new Upload.StartedListener() {
            @Override
            public void uploadStarted(final StartedEvent event) {
                if (uploadInfoWindow.getParent() == null) {
                    UI.getCurrent().addWindow(uploadInfoWindow);
                }
                uploadInfoWindow.setClosable(false);
            }
        });
        sample.addFinishedListener(new Upload.FinishedListener() {
            @Override
            public void uploadFinished(final Upload.FinishedEvent event) {
                uploadInfoWindow.setClosable(true);
            }
        });

        // buildLayout
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(sample);
        layout.setExpandRatio(sample, 1);
        setContent(layout);


    }

    @StyleSheet("uploadexample.css")
    private static class UploadInfoWindow extends Window implements
            Upload.StartedListener, Upload.ProgressListener,
            Upload.FailedListener, Upload.SucceededListener,
            Upload.FinishedListener {
        private final Label state = new Label();
        private final Label result = new Label();
        private final Label fileName = new Label();
        private final Label textualProgress = new Label();

        private final ProgressBar progressBar = new ProgressBar();

        private final Button cancelButton;
        private final TsvUpload tsvUpload;
        private String upload;

        public UploadInfoWindow(final Upload upload,
                                final TsvUpload tsvUpload) {
            super("Status");
            this.tsvUpload = tsvUpload;
            this.upload = "";

            setWidth(350, Unit.PIXELS);

            addStyleName("upload-info");

            setResizable(false);
            setDraggable(false);

            final FormLayout l = new FormLayout();
            setContent(l);
            l.setMargin(true);

            final HorizontalLayout stateLayout = new HorizontalLayout();
            stateLayout.setSpacing(true);
            stateLayout.addComponent(state);

            cancelButton = new Button("Cancel");
            cancelButton.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(final Button.ClickEvent event) {
                    upload.interruptUpload();
                }
            });
            cancelButton.setVisible(false);
            cancelButton.setStyleName("small");
            stateLayout.addComponent(cancelButton);

            stateLayout.setCaption("Current state");
            state.setValue("Idle");
            l.addComponent(stateLayout);

            fileName.setCaption("File name");
            l.addComponent(fileName);

            result.setCaption("Line breaks counted");
            l.addComponent(result);

            progressBar.setCaption("Progress");
            progressBar.setVisible(false);
            l.addComponent(progressBar);

            textualProgress.setVisible(false);
            l.addComponent(textualProgress);

            upload.addStartedListener(this);
            upload.addProgressListener(this);
            upload.addFailedListener(this);
            upload.addSucceededListener(this);
            upload.addFinishedListener(this);

        }

        @Override
        public void uploadFinished(final Upload.FinishedEvent event) {
            state.setValue("Idle");
            progressBar.setVisible(false);
            textualProgress.setVisible(false);
            cancelButton.setVisible(false);
        }

        @Override
        public void uploadStarted(final StartedEvent event) {
            // this method gets called immediately after upload is started
            progressBar.setValue(0f);
            progressBar.setVisible(true);
            UI.getCurrent().setPollInterval(500);
            textualProgress.setVisible(true);
            // updates to client
            state.setValue("Uploading");
            fileName.setValue(event.getFilename());

            cancelButton.setVisible(true);
        }

        @Override
        public void updateProgress(final long readBytes,
                                   final long contentLength) {
            // this method gets called several times during the update
            progressBar.setValue(new Float(readBytes / (float) contentLength));
            textualProgress.setValue("Processed " + readBytes + " bytes of "
                    + contentLength);
            result.setValue(tsvUpload.getLineBreakCount() + " (counting...)");
        }

        @Override
        public void uploadSucceeded(final Upload.SucceededEvent event) {
            result.setValue(tsvUpload.getLineBreakCount() + " (total)");
            // TODO important line here
            this.upload = tsvUpload.getUploadBuilder().toString();
        }

        @Override
        public void uploadFailed(final Upload.FailedEvent event) {
            result.setValue(tsvUpload.getLineBreakCount()
                    + " (counting interrupted at "
                    + Math.round(100 * progressBar.getValue()) + "%)");
        }

    }

    private static class TsvUpload implements Upload.Receiver {
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
}
