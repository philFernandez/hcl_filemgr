package com.hcl.filemgr;

import java.io.File;
import java.io.IOException;
import com.hcl.interfaces.ICoreBusiness;

public class BusinessLogic implements ICoreBusiness {
    private String fileName;
    private String baseDirName;

    public BusinessLogic(String fileName) {
        baseDirName = "LockMeFileManagerRoot";
        this.fileName = fileName;
    }

    public BusinessLogic(String baseDirName, String fileName) {
        this.baseDirName = baseDirName;
        this.fileName = fileName;
    }

    @Override
    public void addFile() throws IOException {
        File file = new File(baseDirName, fileName);
        if (file.createNewFile()) {
            System.out.println(file.getName() + " : File Created");
        } else {
            System.out.println(file.getName() + " : Already Exists");
        }
    }

    @Override
    public void deleteFile() {
        // TODO Auto-generated method stub

    }

    @Override
    public void searchFile() {
        // TODO Auto-generated method stub

    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBaseDirName() {
        return baseDirName;
    }

    public void setBaseDirName(String baseDirName) {
        this.baseDirName = baseDirName;
    }

    @Override
    public String toString() {
        return "BusinessLogic [baseDirName=" + baseDirName + ", fileName=" + fileName
                + "]";
    }
}
