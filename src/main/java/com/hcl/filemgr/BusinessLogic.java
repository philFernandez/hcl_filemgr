package com.hcl.filemgr;

import java.io.File;
import java.io.IOException;
import com.hcl.interfaces.ICoreBusiness;

public class BusinessLogic implements ICoreBusiness {
    private String fileName;
    private String baseDirName;


    public BusinessLogic(String baseDirName, String fileName) {
        this.baseDirName = baseDirName;
        this.fileName = fileName;
        File base = new File(baseDirName);
        if (!base.isDirectory()) {
            base.mkdir();
        }
    }

    public BusinessLogic(String fileName) {
        this("LockMeFileManagerRoot", fileName);
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
        File file = new File(baseDirName, fileName);
        if (file.delete()) {
            System.out.println(file.getName() + " : File Deleted");
        } else {
            System.out.println(file.getName() + " : No Such File Exists");
        }
    }

    @Override
    public void searchFile() {
        File file = new File(baseDirName, fileName);
        if (file.exists()) {
            System.out.println(file.getName() + " : This File Is Present");
        } else {
            System.out.println(file.getName() + " : No Such File Exists");
        }

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
