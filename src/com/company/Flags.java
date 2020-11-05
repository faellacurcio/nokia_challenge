package com.company;

import java.util.HashMap;

/**
 * The Flags class is a Hashmap<String, Boolean> that stores all flags/switches for a given command
 *  the idea behind this structure is to keep all flags/switches easily accessible
 *  To facilitate the validation and parsing of the user's input.
 *
 */

public class Flags {
    private HashMap<String, Boolean> availableFlags;

    // begin Constructor(s) --------------------------------
    public Flags() {
        availableFlags = new HashMap<>();
    }
    // end Constructor(s) --------------------------------

    /**
     * The addFlags method add new flags/switches to the hashmap<flag, param>
     *
     * input: String flag, Boolean hasParam
     * output: none
     */
    public void addFlags(String flag, Boolean hasParam) {
        this.availableFlags.put(flag, hasParam);
    }

    /**
     * The hasFlag method check if a given flag/switch requires a parameter
     *
     * input: String flag
     * output: boolean
     */
    public boolean hasFlag(String flag){
        return this.availableFlags.containsKey(flag);
    }

    /**
     * The flagHasParam returns the hasParam boolean of that flag/switch
     *
     * input: String flag
     * output: boolean
     */
    public boolean flagHasParam(String flag){
        return this.availableFlags.get(flag);
    }

}
