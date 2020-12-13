package com.hcl.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.hcl.exceptions.InputReaderClosedException;
import com.hcl.exceptions.InvalidMenuChoiceException;
import com.hcl.filemgr.BusinessLogic;
import com.hcl.filemgr.ContextMenu;
import com.hcl.tools.InputReader;
import com.hcl.tools.Utils;

public class MainDriver {
    public static void main(String[] args) {
        Utils.clearScreen();
        MainDriver driver = new MainDriver();
        driver.welcome();
        driver.startup();
    }

    private void welcome() {
        new ContextMenu("Welcome to the LockMe.com File Manager!", "-");
    }

    private void startup() {
        int choice = -1;

        do {
            try {
                choice = mainMenu();
            } catch (InvalidMenuChoiceException e) {
                System.out.println(e.getMessage());
            } catch (InputReaderClosedException e) {
                // Log this exception to disk (This should never happen in production)
                try (FileWriter fw = new FileWriter(".exceptions.txt", true);
                        PrintWriter pw = new PrintWriter(fw)) {
                    e.printStackTrace(pw);
                    e.printStackTrace(pw);
                    System.exit(1);
                } catch (IOException e2) {
                    e2.addSuppressed(e2);
                }
            }

        } while (choice != 1 && choice != 2 && choice != 3);

        mainMenuController(choice);
    }

    private void mainMenuController(int opt) {
        switch (opt) {
            case 1:
                displayAscending();
                break;
            case 2:
                Utils.clearScreen();
                startupBusinessOps();
                break;
            case 3:
                System.exit(0);
        }
    }

    private int mainMenu()
            throws InvalidMenuChoiceException, InputReaderClosedException {
        System.out.println('\n');
        new ContextMenu("File Manager Main Menu",
                new String[] {"Please Choose an Option", "(1) Display Files",
                        "(2) Display Business Level Operations", "(3) Exit"},
                "~");

        InputReader in = InputReader.getInstance();

        int opt;
        try {
            opt = in.nextInt();
        } catch (InputMismatchException e) {
            // clear InputReader buffer of mismatched input
            in.nextLine();
            // set opt to 0 so subsequent InvalideMenuChoiceException is thrown
            opt = 0;
        }

        if (opt != 1 && opt != 2 && opt != 3) {
            throw new InvalidMenuChoiceException("Invalid Menu Choice");
        }

        return opt;
    }

    /**
     * Set this up like how startup and mainMenu are. So this will be like starup, and need another
     * method like mainMenu (or make mainMenu be able to handle both) (maybe make starup handle both. it
     * could take a flag telling it what to do)
     */
    private void startupBusinessOps() {
        int choice = -1;
        do {
            try {
                choice = businessOpsMenu();
            } catch (InvalidMenuChoiceException e) {
                System.out.println(e.getMessage());
            } catch (InputReaderClosedException e) {
                // Log this exception to disk (This should never happen in production)
                try (FileWriter fw = new FileWriter(".exceptions.txt", true);
                        PrintWriter pw = new PrintWriter(fw)) {
                    e.printStackTrace(pw);
                    e.printStackTrace(pw);
                    System.exit(1);
                } catch (IOException e2) {
                    e2.addSuppressed(e2);
                }
            }

        } while (choice != 1 && choice != 2 && choice != 3 && choice != 4);
        businessMenuController(choice);

    }

    private int businessOpsMenu()
            throws InvalidMenuChoiceException, InputReaderClosedException {
        System.out.println('\n');
        new ContextMenu(
                "Business Level Operations Menu", new String[] {"(1) Add File",
                        "(2) Delete File", "(3) Search File", "(4) Return to Main Menu"},
                "~");
        InputReader in = InputReader.getInstance();
        int opt;
        try {
            opt = in.nextInt();
        } catch (InputMismatchException e) {
            in.nextLine();
            opt = 0;
        }

        if (opt != 1 && opt != 2 && opt != 3 && opt != 4) {
            throw new InvalidMenuChoiceException("Invalid Menu Choice");
        }

        return opt;
    }

    private void businessMenuController(int opt) {
        switch (opt) {
            case 1:
                // add file
                addFile();
                break;
            case 2:
                // delete
                deleteFile();
                break;
            case 3:
                // search
                break;
            case 4:
                Utils.clearScreen();
                startup();
                break;
        }
    }

    private void displayAscending() {
        Utils.clearScreen();
        // TODO Test this on windows os
        final String OS = System.getProperty("os.name"); // Mac OS X on mac
        final String ROOT = "LockMeFileManagerRoot";
        File rootDir = new File(ROOT);
        if (!rootDir.isDirectory()) {
            rootDir.mkdir();
        }
        if (OS.equals("Mac OS X")) {
            try (Stream<Path> walk = Files.walk(Paths.get(ROOT))) {
                List<String> nodes = walk.filter(Files::isRegularFile).sorted()
                        .map(f -> f.toString()).collect(Collectors.toList());
                nodes.forEach(System.out::println);

            } catch (IOException e) {
                e.printStackTrace();
            }
            startup();
        }
    }

    private void addFile() {
        Utils.clearScreen();
        InputReader in = InputReader.getInstance();

        try {
            System.out.print("Enter New File Name : ");
            String fileName = in.nextLine();
            BusinessLogic newFile = new BusinessLogic(fileName);
            newFile.addFile();
        } catch (InputReaderClosedException | IOException e) {
            // TODO set this up to log
            e.printStackTrace();
        }
        startupBusinessOps();
    }

    private void deleteFile() {
        Utils.clearScreen();
        InputReader in = InputReader.getInstance();
        try {
            System.out.print("Enter File Name To Delete : ");
            String fileName = in.nextLine();
            BusinessLogic removeFile = new BusinessLogic(fileName);
            removeFile.deleteFile();
        } catch (InputReaderClosedException e) {
            // TODO set this up to log
            e.printStackTrace();
        }
        startupBusinessOps();
    }
}
