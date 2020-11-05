package com.company;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    private final Commands availableCommands;
    private final boolean DEBUG;

    public Validation(Commands availableCommands, boolean DEBUG) {
        this.availableCommands = availableCommands;
        this.DEBUG = DEBUG;
    }

    public Validation(Commands availableCommands) {
        this.availableCommands = availableCommands;
        this.DEBUG = false;
    }

    public boolean checkIntegrity(String raw_command){

        List matchList = Util.parseToList(raw_command);

        //TODO: Use Utils.parseToHashMap to check weird cases of flags.

        //Debug
        if (DEBUG)
            System.out.println(matchList);

        Iterator<String> matchListIterator = matchList.iterator();

        if (matchListIterator.hasNext()){
            String main_command = matchListIterator.next();

            if(!availableCommands.hasCommand(main_command)){
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
                                //TODO: Check integrity of param (is it flag? Does it have "..."?)
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
