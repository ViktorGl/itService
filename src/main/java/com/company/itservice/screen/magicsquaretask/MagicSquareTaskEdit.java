package com.company.itservice.screen.magicsquaretask;

import com.company.itservice.app.MagicSquareService;
import com.company.itservice.entity.MagicSquareTask;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.*;
import io.jmix.ui.download.Downloader;
import io.jmix.ui.screen.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@UiController("MagicSquareTask.edit")
@UiDescriptor("magic-square-task-edit.xml")
@EditedEntityContainer("magicSquareTaskDc")
public class MagicSquareTaskEdit extends StandardEditor<MagicSquareTask> {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(MagicSquareTaskEdit.class);
    @Autowired
    private DateField<LocalDateTime> dateField;
    @Autowired
    private TextArea<String> inputValueField;
    @Autowired
    private TextArea<String> outputValueField;
    @Autowired
    private Notifications notifications;
    @Autowired
    private MagicSquareService magicSquareService;
    @Autowired
    private TextField<Integer> resultField;
    @Autowired
    private FileStorageUploadField dataFileField;
     @Autowired
    private Downloader downloader;

    @Subscribe("dataFileField")
    public void onDataFileFieldFileUploadSucceed(SingleFileUploadField.FileUploadSucceedEvent event) {
        String text;

        try (InputStream fileContent = dataFileField.getFileContent()) {
          text = new String(fileContent.readAllBytes());
        } catch ( IOException | NullPointerException e ){
            log.error("Error reading data from file!", e);
            notifications.create(Notifications.NotificationType.ERROR)
                    .withCaption("Error reading data from file!")
                    .withDescription(e.getMessage()).show();
            return;
        }

        String[] textArray = text.split("\n");
        if( textArray.length >= 8 && textArray[0].contains("MAGICSQUARE")) {
            dateField.setValue(LocalDateTime.parse(textArray[1]));
            inputValueField.setValue(textArray[2]  + "\n" + textArray[3] + "\n" + textArray[4]);
            outputValueField.setValue(textArray[5] + "\n" + textArray[6] + "\n" + textArray[7]);
            resultField.clear();
        } else {
            notifications.create()
            .withCaption("File content")
            .withDescription("The data from the file does not corresponded to the current procesing!")
            .show();
        }
    }

    @Subscribe("btnDownload")
    public void onBtnDownloadClick(Button.ClickEvent event) {
        String testOut = "\"MAGICSQUARE\""   + "\n"
                + dateField.getValue()       + "\n"
                + inputValueField.getValue() + "\n"
                + outputValueField.getValue();
        byte[] btestOut = testOut.getBytes();
        downloader.download( btestOut, "square.txt");
    }

    @Subscribe
    public void onInitEntity(InitEntityEvent<MagicSquareTask> event) {
        event.getEntity().setDate(LocalDateTime.now());
    }

    @Subscribe("calcBtn")
    public void onCalcBtnClick(Button.ClickEvent event) {
        String input = inputValueField.getValue();
        List<Integer> inpValues = parseInputData(input);
        if (inpValues != null) {
            int[] res = magicSquareService.getMinPriceAndSquare(inpValues);
            resultField.setValue(res[0]);
            StringBuilder sb =  new StringBuilder(res[1]);
            for(int i=1; i < 9; i++) {
                if (i % 3 == 0) sb.append(res[i]).append("\n");
                else sb.append(res[i]).append(" ");
            }
            sb.append(res[9]); // ?????????? ?????????????????? ?????????? ???? ?????????? ???? ???????????? ???? "\n"
            outputValueField.setValue(sb.toString());
        }
    }

    private List<Integer> parseInputData(String input) {
        List<Integer> intValues = new ArrayList<>();
        try {
            String[] values = StringUtils.split(input, " \n");
            if (values.length != 9) {
                throw new IllegalStateException(
                        "The number of numbers must be 9, the separator is a space or a line feed!");
            }
            for (String value : values) {
                intValues.add(Integer.parseInt(value));
            }
        } catch (Exception e) {
            notifications.create(Notifications.NotificationType.ERROR)
                    .withCaption("Data parsing error!")
                    .withDescription(e.getMessage()).show();
            return null;
        }
        return intValues;
    }

}