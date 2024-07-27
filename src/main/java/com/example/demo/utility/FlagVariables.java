package com.example.demo.utility;

public class FlagVariables {

    private static Integer flagValue = 1;

    public static Integer getFlagValue() {
        return flagValue;
    }

    public static void setFlagValue(Integer flagValue) {
        FlagVariables.flagValue = flagValue;
    }


}
