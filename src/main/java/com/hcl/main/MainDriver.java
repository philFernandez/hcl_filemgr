package com.hcl.main;

import com.hcl.filemgr.ContextMenu;

public class MainDriver {
    public static void main(String[] args) {
        // welcome();
        mainMenu();
    }

    public static void mainMenu() {
        System.out.println();
        new ContextMenu("Welcome to LockMe.com File Manager",
                new String[] {"Please Choose an Option", "(1) Display Files",
                        "(2) Display File Operations Menu"},
                "~");
    }

}
