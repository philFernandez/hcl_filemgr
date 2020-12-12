package com.hcl.tools;

import java.util.Scanner;
import com.hcl.exceptions.InputReaderClosedException;

/**
 * Singleton InputReader wraps java Scanner for avoiding problems with closing System.in when using
 * Scanner default behavior
 *
 */
public class InputReader {
    private Scanner inputReader;
    private static InputReader singleton = null;
    private boolean isClosed;

    private InputReader() {
        isClosed = false;
        inputReader = new Scanner(System.in);
    }

    public static InputReader getInstance() {
        if (singleton == null) {
            singleton = new InputReader();
        }
        return singleton;
    }

    public int nextInt() throws InputReaderClosedException {
        if (isClosed) {
            throw new InputReaderClosedException("InputReader is closed");
        }
        return inputReader.nextInt();
    }

    public String next() throws InputReaderClosedException {
        if (isClosed) {
            throw new InputReaderClosedException("InputReader is closed");
        }
        return inputReader.next();
    }

    public String nextLine() throws InputReaderClosedException {
        if (isClosed) {
            throw new InputReaderClosedException("InputReader is closed");
        }
        return inputReader.nextLine();
    }

    public void close() {
        isClosed = true;
        inputReader.close();
    }

}
