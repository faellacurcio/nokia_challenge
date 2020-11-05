package com.company;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    private static Commands availableCommands;
    private boolean DEBUG = false;

    public Validation(Commands availableCommands) {
        this.availableCommands = availableCommands;
    }

    public boolean checkIntegrity(String raw_command){

        List<String> matchList = new ArrayList<>();
        Pattern regex;
        regex = Pattern.compile("[^\\s\"']+|\"[^\"]*\"|'[^']*'");
        Matcher regexMatcher = regex.matcher(raw_command);
        while (regexMatcher.find()) {
            matchList.add(regexMatcher.group());
        }

        //Debug
        if (DEBUG)
            System.out.println(matchList);

        Iterator<String> matchListIterator = matchList.iterator();

        if (matchListIterator.hasNext()){
            String main_command = matchListIterator.next();

            if(!this.availableCommands.hasCommand(main_command)){
                System.out.println(main_command+" is not a valid operation.");
            }else{
                boolean isParam = false;

                // Loop through flags and parameters
                while(matchListIterator.hasNext()){
                    String testingSwitch = matchListIterator.next();

                    if(isParam){
                        // Check integrity of regex
                        isParam = false;
                    }else{
                        if (DEBUG)
                            System.out.println("Testing "+testingSwitch+", is it a flag?  "+availableCommands.hasFlag(main_command, testingSwitch));

                        if(availableCommands.hasFlag(main_command, testingSwitch)){
                            if (availableCommands.flagHasParam(main_command,testingSwitch)){
                                if (DEBUG)
                                    System.out.println("flag: "+testingSwitch+" has parameters");
                                isParam = true;
                            }else{
                                //TODO: Check integrity of param
                                if (DEBUG)
                                    System.out.println("flag: "+testingSwitch+" has no parameters");
                            }
                        }else{
                            System.out.println(main_command+" is not a valid flag.");
                            return false;
                        }
                    }
                }

            }
        }


        return true;
    }
}
