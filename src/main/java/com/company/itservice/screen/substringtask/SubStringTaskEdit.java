package com.company.itservice.screen.substringtask;

import com.company.itservice.app.MagicSquareService;
import com.company.itservice.app.SubStringService;
import com.company.itservice.entity.MagicSquareTask;
import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.*;
import io.jmix.ui.download.Downloader;
import io.jmix.ui.meta.PropertyType;
import io.jmix.ui.meta.StudioProperty;
import io.jmix.ui.screen.*;
import com.company.itservice.entity.SubStringTask;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@UiController("SubStringTask.edit")
@UiDescriptor("sub-string-task-edit.xml")
@EditedEntityContainer("subStringTaskDc")
public class SubStringTaskEdit extends StandardEditor<SubStringTask> {

    @Autowired
    private TextArea<String> subStringsField;

    @Autowired
    private TextArea<String> cmpStringsField;

    @Autowired
    private Notifications notifications;

    //@Autowired
    //private MagicSquareService magicSquareService;

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
        // Сообщение-инф. для отладки
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
        String sub = subStringsField.getValue();
        //String[] sFinds = { "\" ,", " \","," \" ,"};
        //SubStringService sss = new SubStringService();
        //sub = sss.seachReplaceSubString(sub, sFinds);
        //String[] subs = sub.split("\n,\n");


        String cmp = cmpStringsField.getValue();
        //cmp = sss.seachReplaceSubString(cmp, sFinds);
        //String[] cmps = cmp.split("\n,\n");




        resultField.clear();
        if ((sub.length() > 0) && (cmp.length() > 0)) {
            SubStringService subStringService = new SubStringService(sub, cmp);
            String result = subStringService.calculate();
            if(result.length() > 0)
        //    int[] res = magicSquareService.getMinPriceAndSquare(inpValues);
        //    resultField.setValue(res[0]);
        //    StringBuilder sb =  new StringBuilder(res[1]);
        //    for(int i=1; i <10; i++)
        //        if( i%3 == 0 ) sb.append(res[i]).append("\n");
        //        else sb.append(res[i]).append(" ");

            if(result.length() > 0)     resultField.setInputPrompt(result);
        }
    }



    @Subscribe("btnDownload")
    public void onBtnDownloadClick(Button.ClickEvent event) {
        byte[] testOut = "wewewe".getBytes();
        downloader.download(testOut, "test.txt");
    }


}
