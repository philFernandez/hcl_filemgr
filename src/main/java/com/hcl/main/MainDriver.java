package com.hcl.main;

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
import com.hcl.filemgr.ContextMenu;
import com.hcl.tools.InputReader;

public class MainDriver {
    public static void main(String[] args) {
        new MainDriver().startup();
    }

    public void startup() {
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

        } while (choice != 1 && choice != 2);


        menuController(choice);

    }

    public void displayAscending() {
        // TODO Test this on window
        String os = System.getProperty("os.name"); // Mac OS X on mac
        if (os.equals("Mac OS X")) {
            try (Stream<Path> walk = Files.walk(Paths.get("./LockMe"))) {
                List<String> nodes = walk.filter(Files::isRegularFile).sorted()
                        .map(f -> f.toString()).collect(Collectors.toList());
                nodes.forEach(System.out::println);

            } catch (IOException e) {
                e.printStackTrace();
            }
            startup();
        }
    }

    public void menuController(int opt) {
        switch (opt) {
            case 1:
                displayAscending();
                break;
            case 2:
                System.out.println("you chose 2");
                break;
        }
    }

    public int mainMenu() throws InvalidMenuChoiceException, InputReaderClosedException {
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
            in.nextLine();
            // set opt to 0 so subsequent InvalideMenuChoiceException is thrown
            opt = 0;
        }

        if (opt != 1 && opt != 2) {
            throw new InvalidMenuChoiceException("Invalid Menu Choice");
        }

        return opt;
    }
}
