package com.company;

import java.util.HashMap;

public class Commands {
    private HashMap<String, Flags> availableCommands;

    public Commands() {
        this.availableCommands = new HashMap<>();
    }

    public void addCommand(String command){
        availableCommands.put(command, new Flags());
    }

    public void addFlag(String command, String flag, boolean hasParam){
        if(hasCommand(command)){
            availableCommands.get(command).addFlags(flag, hasParam);
        }
    }

    public boolean hasCommand(String command){
        return availableCommands.containsKey(command);
    }

    public boolean hasFlag(String command, String flag){
        if(hasCommand(command)){
            return availableCommands.get(command).hasFlag(flag);
        }
        return false;
    }


}
