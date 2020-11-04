package com.company;

public class Flag {

    private String flag;
    private final boolean  hasParameter;

    // begin Constructor(s) --------------------------------
    public Flag(String flag, boolean hasParameter) {
        this.flag = flag;
        this.hasParameter = hasParameter;
    }

    public Flag(String flag) {
        this.flag = flag;
        this.hasParameter = false;
    }
    // end Constructor(s) --------------------------------

    // begin geter/seter(s) --------------------------------
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public boolean hasParameter() {
        return hasParameter;
    }
    // end geter/seter(s) --------------------------------

}
