package com.hcl.main;

import java.util.Scanner;
import com.hcl.exceptions.InvalidMenuChoiceException;
import com.hcl.filemgr.ContextMenu;

public class MainDriver {
    public static void main(String[] args) {
        int choice;

        try {
            choice = mainMenu();

        } catch (InvalidMenuChoiceException e) {
            System.out.println(e.getMessage());
            choice = 1;
        }

        menuController(choice);

    }

    public static void menuController(int opt) {
        switch (opt) {
            case 1:
                System.out.println("you chose 1");
                break;
            case 2:
                System.out.println("you chose 2");
                break;
        }
    }

    public static int mainMenu() throws InvalidMenuChoiceException {
        System.out.println();
        Scanner in = new Scanner(System.in);

        new ContextMenu("Welcome to LockMe.com File Manager",
                new String[] {"Please Choose an Option", "(1) Display Files",
                        "(2) Display Business Level Operations"},
                "~");

        int opt = in.nextInt();
        if (opt != 1 && opt != 2) {
            in.close();
            throw new InvalidMenuChoiceException("Invalid Menu Choice");
        }
        in.close();
        return opt;
    }
}
