package com.company.itservice.screen.magicsquaretask;

import com.company.itservice.app.MagicSquareService;
import com.company.itservice.entity.MagicSquareTask;
import io.jmix.ui.Notifications;
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
         InputStream fileContent = dataFileField.getFileContent();
        String text;
        try {
            text = new String(fileContent.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Ошибка чтения файла!");
        }

        String str = text.toString();
        String[] textArray = str.split("\n");

        // Разобраться с кодировкой (кириллица)
        String marker = textArray[0];
        if(marker.contains("MAGICSQUARE")) {
            dateField.setValue(LocalDateTime.parse(textArray[1]));
            inputValueField.setValue( textArray[2] + "\n" + textArray[3] + "\n" + textArray[4]);
            outputValueField.setValue(textArray[5] + "\n" + textArray[6] + "\n" + textArray[7]);
        } else {
            notifications.create()
                    .withCaption("File content")
                    .withDescription("Данные из файла не соответствуют текущей обработке (MAGICSQUARE)")
                    .show();
        };
    }

    @Subscribe("btnDownload")
    public void onBtnDownloadClick(Button.ClickEvent event) {
        StringBuilder sb = new StringBuilder("\"MAGICSQUARE\"");
        sb.append("\n" + dateField.getValue());
        sb.append("\n" + inputValueField.getValue());
        sb.append("\n" + outputValueField.getValue());
        String testOut = sb.toString();
        byte[] btestOut = testOut.getBytes();;
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

}