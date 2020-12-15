package com.hcl.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.regex.Pattern;
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
        // Show welcome banner
        driver.welcome();
        // Startup main menu
        driver.startup();
        // InputReader is a singleton wrapper around Scanner. It is closed
        // here at the very end of the program (see com.hcl.tools.InputReader.java)
        InputReader.getInstance().close();
    }

    private void welcome() {
        String[] about =
                {"Developer      : Phil Fernandez", "Product Owner  : Lockers Pvt. Ltd"};
        new ContextMenu("Welcome to the LockedMe.com File Manager!", about, "-");
    }

    /**
     * Runs the startup menu until valid input is given
     */
    private void startup() {
        int choice = -1;
        do {
            try {
                choice = mainMenu();
            } catch (InvalidMenuChoiceException e) {
                System.out.println(e.getMessage());
            } catch (InputReaderClosedException e) {
                // Log this exception to disk (This should never happen in production)
                try (FileWriter fw = new FileWriter(".exceptions", true);
                        PrintWriter pw = new PrintWriter(fw)) {
                    e.printStackTrace(pw);
                    System.exit(1);
                } catch (IOException e2) {
                    e2.addSuppressed(e2);
                }
            }

        } while (choice != 1 && choice != 2 && choice != 3);

        mainMenuController(choice);
    }

    /**
     * Shows main menu and propmpts user to make a choice returns user's choice
     * 
     * @return int
     */
    private int mainMenu()
            throws InvalidMenuChoiceException, InputReaderClosedException {
        System.out.println('\n');
        String[] options = {"Please Choose an Option", "(1) Display Files",
                "(2) Display Business Level Operations", "(3) Exit"};
        new ContextMenu("File Manager Main Menu", options, "~");

        InputReader in = InputReader.getInstance();

        int opt;
        try {
            opt = in.nextInt();
            // prevent crash from wrong type input (ex. letters rather than expected digits)
        } catch (InputMismatchException e) {
            // clear InputReader buffer of mismatched input
            in.nextLine();
            // set opt to 0 so subsequent InvalideMenuChoiceException is thrown
            opt = 0;
        }

        // Throw custom exception if an invalid input is detected
        if (opt != 1 && opt != 2 && opt != 3) {
            throw new InvalidMenuChoiceException("Invalid Menu Choice");
        }

        return opt;
    }

    // Is called from "startup()" after user input is obtained
    // Controls the flow of program depending on input from main menu
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

    /**
     * Runs the business operations menu until valid input is given
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
                try (FileWriter fw = new FileWriter(".exceptions", true);
                        PrintWriter pw = new PrintWriter(fw)) {
                    e.printStackTrace(pw);
                    System.exit(1);
                } catch (IOException e2) {
                    e2.addSuppressed(e2);
                }
            }

        } while (choice != 1 && choice != 2 && choice != 3 && choice != 4);
        businessMenuController(choice);
    }

    /**
     * Show business operations menu to user and prompt for choice returns the user's choice
     * 
     * @return int
     */
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
            // handle wrong type input (ie. user inputs string instead of number)
        } catch (InputMismatchException e) {
            in.nextLine();
            opt = 0;
        }

        // throw custom excepiton if invalid input is given
        if (opt != 1 && opt != 2 && opt != 3 && opt != 4) {
            throw new InvalidMenuChoiceException("Invalid Menu Choice");
        }

        return opt;
    }

    // Is called from "startupBusinessOps()" after user input is obtained
    // Controls the flow of program depending on input from business ops menu
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
                searchFile();
                break;
            case 4:
                Utils.clearScreen();
                startup();
                break;
        }
    }

    /**
     * Displays current files in caseinsensitive alphabetical order
     */
    private void displayAscending() {
        Utils.clearScreen();
        final String OS = System.getProperty("os.name"); // Mac OS X on mac
        final String ROOT = "LockMeFileManagerRoot";
        File rootDir = new File(ROOT);
        if (!rootDir.isDirectory()) {
            rootDir.mkdir();
        }
        List<String> nodes = new ArrayList<>();

        if (OS.contains("Mac")) {
            try (Stream<Path> walk = Files.walk(Paths.get(ROOT))) {
                nodes = walk.filter(Files::isRegularFile).map(f -> f.toString())
                        .collect(Collectors.toList());

                Collections.sort(nodes, String.CASE_INSENSITIVE_ORDER);
                nodes.forEach(node -> System.out.println(Utils.last(node.split("/"))));

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (OS.contains("Windows")) {
            try (Stream<Path> walk = Files.walk(Paths.get(ROOT))) {
                nodes = walk.filter(Files::isRegularFile).map(f -> f.toString())
                        .collect(Collectors.toList());

                Collections.sort(nodes, String.CASE_INSENSITIVE_ORDER);
                nodes.forEach(node -> System.out
                        .println(Utils.last(node.split(Pattern.quote("\\")))));


            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Only Windows and Mac OS are supported at this time");
            System.exit(1);
        }
        if (nodes.size() == 0) {
            System.out.println("**EMPTY**");
        }
        startup();
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

            // This will only happen if InputReader is closed. It isn't closed, so
            // this will never happen.
            try (FileWriter fw = new FileWriter(".exceptions", true);
                    PrintWriter pw = new PrintWriter(fw)) {
                e.printStackTrace(pw);

            } catch (IOException e2) {
                e2.addSuppressed(e2);
            }

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

            // This will only happen if InputReader is closed. It isn't closed, so
            // this will never happen.
            try (FileWriter fw = new FileWriter(".exceptions", true);
                    PrintWriter pw = new PrintWriter(fw)) {
                e.printStackTrace(pw);

            } catch (IOException e2) {
                e2.addSuppressed(e2);
            }
        }
        startupBusinessOps();
    }

    private void searchFile() {
        Utils.clearScreen();
        InputReader in = InputReader.getInstance();
        try {
            System.out.print("Enter File Name To Search : ");
            String fileName = in.nextLine();
            new BusinessLogic(fileName).searchFile();
        } catch (InputReaderClosedException e) {

            // This will only happen if InputReader is closed. It isn't closed, so
            // this will never happen.
            try (FileWriter fw = new FileWriter(".exceptions", true);
                    PrintWriter pw = new PrintWriter(fw)) {
                e.printStackTrace(pw);

            } catch (IOException e2) {
                e2.addSuppressed(e2);
            }
        }
        startupBusinessOps();
    }
}
