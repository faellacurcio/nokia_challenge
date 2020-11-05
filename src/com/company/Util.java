package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Util class is a facilitator, Composed by functions to parse the user command and availableCommands
 *
 */

public class Util {
    /**
     * The parseToList takes the raw_command and returns it as a list
     * l -t "Nightcall"  ====>  [l, -t, "Nightcall"]
     *
     * input raw_command (user command)
     * outpur List<String>
     */
    public static List<String> parseToList(String raw_command){
        List<String> matchList = new ArrayList<String>();
        Pattern regex = Pattern.compile("[^\\s\"']+|\"[^\"]*\"|'[^']*'");
        Matcher regexMatcher = regex.matcher(raw_command);
        while (regexMatcher.find()) {
            matchList.add(regexMatcher.group());
        }

        return matchList;
    }

    /**
     * The parseToGetKeys takes the raw_command and availableCommands then returns it as a List
     * l -t "Nightcall" -v  ====>  [-t, -v]
     *
     * input raw_command (user command)
     * outpur List<String>
     */
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
                        if (availableCommands.flagHasParam(main_command,flagOrParam)) {
                            isParam = true;
                        }
                    }
                }
            }
        }
        return flagsList;
    }

    /**
     * The parseToGetKeys takes the raw_command and availableCommands then returns it as a Hashmap
     * l -t "Nightcall" -v  ====>  {'-v': "", '-t': "Nightcall"}
     *
     * input raw_command (user command)
     * outpur Hashmap<String, String>
     */
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
