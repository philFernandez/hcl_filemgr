package com.hcl.tools;

public class Utils {

    /**
     * Simulate clear console (works on all platforms)
     */
    public static void clearScreen() {
        System.out.println(new String(new char[50]).replace("\0", "\r\n"));
    }

    public static <T> T last(T[] array) {
        return array[array.length - 1];
    }

}
