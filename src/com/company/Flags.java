package com.company;

import java.util.HashMap;

public class Flags {
    private HashMap<String, Boolean> availableFlags;

    // begin Constructor(s) --------------------------------
    public Flags() {
        availableFlags = new HashMap<>();
    }
    // end Constructor(s) --------------------------------


    public void addFlags(String flag, Boolean hasParam) {
        this.availableFlags.put(flag, hasParam);
    }

    public boolean hasFlag(String flag){
        return this.availableFlags.containsKey(flag);
    }

    public boolean flagHasParam(String flag){
        return this.availableFlags.get(flag);
    }

}
