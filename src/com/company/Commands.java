package com.company;

import java.util.HashMap;

/**
 * The Commands class is a Hashmap<String, Flag>, where Flag is internally another Hashmap.
 *  the idea behind this structure is to keep all commands and flags/switches easily accessible
 *  To facilitate the validation and parsing of the user's input.
 *
 */
public class Commands {
    private HashMap<String, Flags> availableCommands;

    public Commands() {
        this.availableCommands = new HashMap<>();
    }

    /**
     * addCommand Method stores a new command user hashMap and it's respective Flags object.
     *
     * input: String command
     * output: none
     */
    public void addCommand(String command){
        availableCommands.put(command, new Flags());
    }

    /**
     * addFlag inserts in the Hashmap the relation between the command and one of its flags/switches
     *
     * input: String command, String flag, boolean hasParam
     * (The hasParam variable indicate if the flag has any input.
     *
     * Ex: The command "l" is followed by a regex expression between double quotes.
     * > l -t "Die*"
     *
     * output: none
     */
    public void addFlag(String command, String flag, boolean hasParam){
        if(hasCommand(command)){
            availableCommands.get(command).addFlags(flag, hasParam);
        }
    }

    /**
     * The hasCommand method returns true if the command already exists in the Hashmap
     * input: String command
     * output: boolean
     */
    public boolean hasCommand(String command){
        return availableCommands.containsKey(command);
    }

    public boolean hasFlag(String command, String flag){
        if(hasCommand(command)){
            return availableCommands.get(command).hasFlag(flag);
        }
        return false;
    }

    /**
     * The flagHasParam method check if the switch/flag has an extra parameter after the flag/switch
     *
     * input: String command, String flag
     * output: boolean
     */
    public boolean flagHasParam(String command, String flag){
        if(hasCommand(command)){
            return availableCommands.get(command).flagHasParam(flag);
        }
        return false;
    }


}
