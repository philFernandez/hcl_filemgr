package com.hcl.interfaces;

import java.io.IOException;

public interface ICoreBusiness {
    void addFile() throws IOException;

    void deleteFile();

    void searchFile();
}
