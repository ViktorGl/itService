package com.company.itservice.screen.magicsquaretask;

import com.company.itservice.app.MagicSquareService;
import com.company.itservice.entity.MagicSquareTask;
import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.*;
import io.jmix.ui.download.Downloader;
import io.jmix.ui.screen.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@UiController("MagicSquareTask.edit")
@UiDescriptor("magic-square-task-edit.xml")
@EditedEntityContainer("magicSquareTaskDc")
public class MagicSquareTaskEdit extends StandardEditor<MagicSquareTask> {
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
    private Dialogs dialogs;
    @Autowired
    protected UiComponents uiComponents;
    @Autowired
    private Downloader downloader;

    @Subscribe("dataFileField")
    public void onDataFileFieldFileUploadSucceed(SingleFileUploadField.FileUploadSucceedEvent event) {
        InputStream fileContent = dataFileField.getFileContent();
        String text;
        try {
            text = new String(fileContent.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Ошибка чтения файла!");
        }
        notifications.create()
                .withCaption("File content")
                .withDescription(text)
                .show();

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
            for(int i=1; i <10; i++)
                if( i%3 == 0 ) sb.append(res[i]).append("\n");
                else sb.append(res[i]).append(" ");
            outputValueField.setValue(sb.toString());
        }
    }

    private List<Integer> parseInputData(String input) {
        List<Integer> intValues = new ArrayList<>();
        try {
            String[] values = StringUtils.split(input, " \n");
            if (values.length != 9) {
                throw new IllegalStateException("Количество чисел должно быть равным 9, разделитель - пробел или перевод строки");
            }
            for (String value : values) {
                intValues.add(Integer.parseInt(value));
            }
        } catch (Exception e) {
            notifications.create(Notifications.NotificationType.ERROR)
                    .withCaption("Ошибка парсинга данных")
                    .withDescription(e.getMessage()).show();
            return null;
        }
        return intValues;
    }

    @Subscribe("btnDownload")
    public void onBtnDownloadClick(Button.ClickEvent event) {
        byte[] testOut = "wewewe".getBytes();
        downloader.download(testOut, "test.txt");
    }

}