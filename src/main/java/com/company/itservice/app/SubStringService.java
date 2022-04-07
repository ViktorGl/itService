package com.company.itservice.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubStringService {
    private String srcSubString;
    private String tstString;

    private String[] subStrings;
    private String[] tstStrings;

    public SubStringService(String subS, String tstS ) {
        srcSubString = subS;
        tstString    = tstS;
    }

    public String calculate() {

        // Преобразуем строку подстрок в массивы
        subStrings = userStringToArrayStrings( srcSubString );
        tstStrings = userStringToArrayStrings( tstString );

        List<String> list = new ArrayList<String>();
        for(int i = 0; i < subStrings.length; i++ ) {
            if (isContains(subStrings[i])) list.add(subStrings[i]);
        }
        String outString = "";
        if( !list.isEmpty() ) {
            Collections.sort(list);
            // Формировать в СтрингБилдере!
            outString = list.get(0);
            for(int i = 1; i < list.size(); i++) {
                outString = outString + ", " + list.get(i);
            }
        }
        return outString;
    }

    private boolean isContains( String sub ) {
        for(int i=0; i < tstStrings.length; i++) if (tstStrings[i].contains(sub)) return true;
        return false;
    }

    private String[] userStringToArrayStrings( String userString ) {
        // Преобразуем строку подстрок в массив
        String sub = seachReplaceSubString(userString);
        String[] subStrs = sub.split("#");
        for(int i = 0; i < subStrs.length; i++) {
            subStrs[i] = subStrs[i].replace("\"", " ");
            subStrs[i] = subStrs[i].trim();
        };
        return subStrs;
    }

    private String seachReplaceSubString(String s ) {
        // Подстроки по условиям приведем к единообразию разделения на массив подстрок
        s = s.replace(" ,", "#");
        s = s.replace(" ,", "#");
        s = s.replace(", ", "#");
        s = s.replace(",",  "#");
        return s;
    }

}
