package com.pro1041.util;

/**
 *
 * @author HUNG
 */
public class listData {

    public boolean isEmail(String str) {
        String pattern = "\\w+@\\w+(\\.\\w+)*";
        return str.matches(pattern);
    }

    public boolean isSdt(String str) {
        String pattern = "0\\d{9}";
        return str.matches(pattern);
    }

    public boolean isCmnd(String str) {
        String pattern = "0\\d{11}";
        return str.matches(pattern);
    }

   public boolean isNotEmpty(String... strs) {
    for (String str : strs) {
        if (str.isEmpty()) {
            return false;
        }
    }
    return true;
}
}
