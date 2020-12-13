package com.hcl.tools;

public class Utils {

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static <T> T last(T[] array) {
        return array[array.length - 1];
    }

}
