package com.hcl.main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import com.hcl.exceptions.InputReaderClosedException;
import com.hcl.exceptions.InvalidMenuChoiceException;
import com.hcl.filemgr.ContextMenu;
import com.hcl.tools.InputReader;

public class MainDriver {
    public static void main(String[] args) throws IOException {
        int choice = -1;

        do {
            try {
                choice = mainMenu();
            } catch (InvalidMenuChoiceException e) {
                System.out.println(e.getMessage());
            } catch (InputReaderClosedException e) {
                // Log this exception to disk (This should never happen in production)
                FileWriter fw = new FileWriter(".exceptions.txt", true);
                PrintWriter pw = new PrintWriter(fw);
                e.printStackTrace(pw);
                pw.close();
                fw.close();
                e.printStackTrace(pw);
                System.exit(1);
            }

        } while (choice != 1 && choice != 2);


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

    public static int mainMenu()
            throws InvalidMenuChoiceException, InputReaderClosedException {
        System.out.println();
        new ContextMenu("Welcome to LockMe.com File Manager",
                new String[] {"Please Choose an Option", "(1) Display Files",
                        "(2) Display Business Level Operations"},
                "~");

        InputReader in = InputReader.getInstance();

        int opt;
        try {
            opt = in.nextInt();
        } catch (InputMismatchException e) {
            // clear InputReader buffer of mismatched input
            in.next();
            // set opt to 0 so subsequent InvalideMenuChoiceException is thrown
            opt = 0;
        }

        if (opt != 1 && opt != 2) {
            throw new InvalidMenuChoiceException("Invalid Menu Choice");
        }

        in.close();

        return opt;
    }
}
