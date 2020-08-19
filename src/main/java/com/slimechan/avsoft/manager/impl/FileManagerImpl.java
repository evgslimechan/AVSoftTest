package com.slimechan.avsoft.manager.impl;

import com.slimechan.avsoft.entity.permission.Permission;
import com.slimechan.avsoft.entity.user.AbstractUser;
import com.slimechan.avsoft.manager.FileManager;
import com.slimechan.avsoft.manager.PropertiesManager;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

public class FileManagerImpl implements FileManager {

    private static final String EXTENSIONS_REGEX = PropertiesManager.Instance.getProperty("file.extesions.regex");
    private File defaultFolder;

    public FileManagerImpl(String defaultPath) {
        this.defaultFolder = new File(defaultPath);
    }

    public FileManagerImpl(String defaultPath, boolean createIfNotExists) {
        this.defaultFolder = new File(defaultPath);
        if (createIfNotExists) {
            if (!this.defaultFolder.exists()) {
                this.defaultFolder.mkdir();
            }
        }
    }

    @Override
    public File[] fileList(String... extensions) {
        return Arrays.stream(defaultFolder.listFiles()).parallel()
                .filter(file -> checkExtensions(extensions, file.getName()))
                .collect(Collectors.toList())
                .toArray(new File[0]);
    }

    @Override
    public File findFile(String fileName) {
        return new File(defaultFolder, fileName);
    }

    @Override
    public boolean hasAccess(AbstractUser user, String fileName) {
        boolean isAdmin = user.getPermissions().parallelStream()
                .anyMatch(permission -> permission.getName().equalsIgnoreCase("*"));
        if (isAdmin) return true;

        String[] extensions = user.getPermissions().parallelStream()
                .filter(permission -> permission.getName().equalsIgnoreCase("file.download"))
                .map(Permission::getValue)
                .collect(Collectors.toList()).toArray(new String[0]);

        return checkExtensions(extensions, fileName);
    }

    private boolean checkExtensions(String[] extensions, String fileName) {
        return fileName.matches(String.format(EXTENSIONS_REGEX, String.join("|", Arrays.stream(extensions)
                .map(ext -> ext.startsWith(".") ? "." + ext : ext)
                .collect(Collectors.toList()))));
    }

    @Override
    public boolean download(String fileName, HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        try {
            OutputStream out = response.getOutputStream();
            FileInputStream in = new FileInputStream(findFile(fileName).getAbsolutePath());
            response.setContentLength(in.available());
            byte[] buffer = new byte[4096];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
