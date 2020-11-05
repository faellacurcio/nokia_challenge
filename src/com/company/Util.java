package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    public static List<String> parseToList(String raw_command){
        List<String> matchList = new ArrayList<>();
        Pattern regex;
        regex = Pattern.compile("[^\\s\"']+|\"[^\"]*\"|'[^']*'");
        Matcher regexMatcher = regex.matcher(raw_command);
        while (regexMatcher.find()) {
            matchList.add(regexMatcher.group());
        }

        return matchList;
    }

    public static List<String> parseToGetKeys(String raw_command, Commands availableCommands){

        List<String> matchList = Util.parseToList(raw_command);
        List<String> flagsList = new ArrayList<>();

        Iterator<String> matchListIterator = matchList.iterator();

        if (matchListIterator.hasNext()){

            String main_command = matchListIterator.next();
            String lastFlag="";
            boolean isParam = false;

            // Loop through flags and parameters
            while(matchListIterator.hasNext()){
                String flagOrParam = matchListIterator.next();

                if(isParam){
                    isParam = false;
                }else{
                    if (availableCommands.hasFlag(main_command,flagOrParam)){
                        flagsList.add(flagOrParam);
                        isParam = true;
                    }
                }
            }
        }
        return flagsList;
    }

    public static HashMap<String, String> parseToHashMap(String raw_command, Commands availableCommands){
        HashMap<String, String> instructions = new HashMap<String, String>();

        List<String> matchList = Util.parseToList(raw_command);

        Iterator<String> matchListIterator = matchList.iterator();

        if (matchListIterator.hasNext()){

            String main_command = matchListIterator.next();
            String lastFlag="";
            boolean isParam = false;

            // Loop through flags and parameters
            while(matchListIterator.hasNext()){
                String flagOrParam = matchListIterator.next();

                if(isParam){
                    instructions.put(lastFlag, flagOrParam);
                    isParam = false;
                }else{
                        if (availableCommands.flagHasParam(main_command,flagOrParam)){
                            lastFlag = flagOrParam;
                            isParam = true;
                        }else{
                            instructions.put(flagOrParam,"");
                        }
                }
            }
        }
        return instructions;
    }
}
