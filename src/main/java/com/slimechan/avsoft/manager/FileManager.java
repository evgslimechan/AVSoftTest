package com.slimechan.avsoft.manager;

import com.slimechan.avsoft.entity.user.AbstractUser;
import com.slimechan.avsoft.manager.impl.FileManagerImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

public interface FileManager {

    FileManager Instance = new FileManagerImpl("/home/slimechan/web_files", true);

    File[] fileList(String... extensions);

    File findFile(String fileName);

    boolean hasAccess(AbstractUser abstractUser, String fileName);

    boolean download(String fileName, HttpServletResponse response);

}
