package com.company.itservice.screen.substringtask;

import com.company.itservice.app.SubStringService;
import com.company.itservice.entity.SubStringTask;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.*;

import io.jmix.ui.download.Downloader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

@UiController("SubStringTask.edit")
@UiDescriptor("sub-string-task-edit.xml")
@EditedEntityContainer("subStringTaskDc")
public class SubStringTaskEdit extends StandardEditor<SubStringTask> {

    @Autowired
    private TextArea<String> subStringsField;

    @Autowired
    private TextArea<String> cmpStringsField;

    @Autowired
    private DateField<@NotNull LocalDateTime> dateField;

    @Autowired
    private Notifications notifications;

    @Autowired
    private TextField<String> resultField;
    @Autowired
    private FileStorageUploadField dataSubFileField;
    @Autowired
    private Downloader downloader;

    @Subscribe
    public void onInitEntity(InitEntityEvent<SubStringTask> event) {
        event.getEntity().setDate(LocalDateTime.now());
    }

    @Subscribe("calcBtn")
    public void onCalcBtnClick(Button.ClickEvent event) {
        String sub = subStringsField.getValue();
        String cmp = cmpStringsField.getValue();

        resultField.clear();
        if ((sub != null) && (cmp != null) ) {
            SubStringService subStringService = new SubStringService();
            String result = subStringService.calculate(sub, cmp);
            if(result.length() > 0)     resultField.setInputPrompt(result);
        }
    }

    @Subscribe("btnDownload")
    public void onBtnDownloadClick(Button.ClickEvent event) {
        String testOut = "\"SUBSTRING\"" + "\n" + dateField.getValue() +
                "\n" + subStringsField.getValue() +
                "\n" + cmpStringsField.getValue();

        byte[] btestOut = testOut.getBytes();
        downloader.download( btestOut, "substr.txt");
    }

    @Subscribe("dataSubFileField")
    public void onDataSubFileFieldFileUploadSucceed(SingleFileUploadField.FileUploadSucceedEvent event) {
        InputStream fileContent = dataSubFileField.getFileContent();
        String text;
        try {
             text = new String(fileContent.readAllBytes());
        } catch (IOException e) {
            throw new IllegalStateException("Error reading data from file!");
        } catch (NullPointerException e) {
            throw new IllegalStateException("Error reading file (NullPointerException)!");
        }

        String[] textArray = text.split("\n");

        if( textArray.length >= 4 && textArray[0].contains("SUBSTRING") ) {
            dateField.setValue(LocalDateTime.parse(textArray[1]));
            subStringsField.setValue(textArray[2]);
            cmpStringsField.setValue(textArray[3]);
        } else {
            notifications.create()
                .withCaption("File content")
                .withDescription("The data from the file does not corresponded to the current processing")
                .show();
        }
    }

}
