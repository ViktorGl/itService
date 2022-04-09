package com.company.itservice.app;

import java.util.*;

public class SubStringService {
    public String calculate(String userSubString, String userTestedString) {
        ArrayList<String> arraySubStrings = userStringToArrayList(userSubString);
        if(countChar(userSubString)%2 != 0 ) {
            return "Error in input substrings: The number of \" characters must be even";
        }

        ArrayList<String> arrayTestedStrings = userStringToArrayList(userTestedString);
        if(countChar(userTestedString)%2 != 0 ) {
            return "Error in tested strings: The number of \" characters must be even";
        }

        SortedSet<String> stringSet = new TreeSet<>();
        for(String subString : arraySubStrings)
            if (isContains(subString, arrayTestedStrings)) stringSet.add(subString);

        String outString = "";
        if( !stringSet.isEmpty() ) {
            List<String> list = new ArrayList<>(stringSet);
            StringBuilder sb = new StringBuilder();
            sb.append("\"").append(list.get(0)).append("\"");
            for(int i = 1; i < list.size(); i++) sb.append(", \"").append(list.get(i)).append("\"");
            outString = sb.toString();
            }
        return outString;
    }

    private boolean isContains(String sub, ArrayList<String> arrayTestedStrings) {
        for(String testedString : arrayTestedStrings) if(testedString.contains(sub)) return true;
        return false;
    }

    int countChar(String str) {
        int counter = 0;
        for (int i = 0; i < str.length(); i++)   if(str.charAt(i) == '"') counter++;
        return counter;
    }

    private ArrayList<String> userStringToArrayList( String userString ) {
        // Преобразуем строку из поля формы в массив подстрок
        String[] userStrings = userString.split("\"");
        ArrayList<String> arrayList = new ArrayList<>();
        for(int i = 1; i < userStrings.length; i+=2 ) arrayList.add(userStrings[i]);
        return arrayList;
    }
}
