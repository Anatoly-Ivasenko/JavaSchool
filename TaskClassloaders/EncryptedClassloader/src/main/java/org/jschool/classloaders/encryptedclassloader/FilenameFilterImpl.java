package org.jschool.classloaders.encryptedclassloader;

import java.io.File;
import java.io.FilenameFilter;

public class FilenameFilterImpl implements FilenameFilter {
    private File dir;
    private String name;

    public FilenameFilterImpl(File dir, String name) {
        this.dir = dir;
        this.name = name;
    }

    @Override
    public boolean accept(File dir, String name) {
        return (dir.getName() == (name + ".class"));
    }
}
