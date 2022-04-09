package com.company.itservice.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SubStringService {
    public String calculate(String userSubString, String userTestedString) {
        String[] subStrings = userStringToArrayStrings( userSubString );
        String[] testedStrings = userStringToArrayStrings( userTestedString );
        List<String> list = new ArrayList<>();
        for (String subString : subStrings) if (isContains(subString, testedStrings)) list.add(subString);

        String outString = "";
        if( !list.isEmpty() ) {
            Collections.sort(list);
            StringBuilder sb = new StringBuilder();
            sb.append(list.get(0));
            for (int i = 1; i < list.size(); i++) sb.append(", ").append(list.get(i));
            outString = sb.toString();
        }
        return outString;
    }

    private boolean isContains(String sub, String[] testedStrings) {
        for (String testedString : testedStrings) if (testedString.contains(sub)) return true;
        return false;
    }

    private String[] userStringToArrayStrings( String userString ) {
        // Преобразуем строку подстрок в массив подстрок и удалим лишние символы "
        String sub = seachReplaceSubString(userString);
        String[] subStrs = sub.split("#");
        for(int i = 0; i < subStrs.length; i++) {
            subStrs[i] = subStrs[i].replace("\"", " ");
            subStrs[i] = subStrs[i].trim();
        }
        return subStrs;
    }

    private String seachReplaceSubString(String s ) {
        // Подстроки по условиям приведем к единообразию формата разделителей
        for(String s2 : Arrays.asList(" ,", " ,", ", ", ","))  s = s.replace(s2,  "#");
        return s;
    }

}
