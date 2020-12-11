package com.hcl.filemgr;

import java.util.Arrays;

public class ContextMenu {
    private String[] items;
    private String border;
    private String header;

    public ContextMenu(String[] items, String border) {
        this.items = items;
        this.border = border;
    }

    public ContextMenu(String item, String border) {
        this.items = new String[1];
        this.items[0] = item;
        this.border = border;
    }

    public ContextMenu(String header, String[] items, String border) {
        this.items = items;
        this.border = border;
        this.header = header;
        display();
    }

    private void display() {
        if (header != null) {
            System.out.println(header);
        }
        displayBorder();
        Arrays.asList(items).forEach(System.out::println);
        displayBorder();
    }

    private void displayBorder() {
        for (int i = 0; i < borderWidth(); i++) {
            System.out.print(border);
        }
        System.out.println();
    }

    private int borderWidth() {
        int longest = 0;
        for (String str : items) {
            if (str.length() > longest) {
                longest = str.length();
            }
        }

        if (header != null && header.length() > longest) {
            longest = header.length();
        }
        return longest;
    }


}
