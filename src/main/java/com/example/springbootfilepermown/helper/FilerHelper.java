package com.example.springbootfilepermown.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.HashSet;
import java.util.Set;

@Service
public class FilerHelper {
    private Logger logger = LogManager.getLogger(FilerHelper.class);

    public void setPermission(File file) {
        try {
            Set<PosixFilePermission> perms = new HashSet<>();
            perms.add(PosixFilePermission.OWNER_READ);
            perms.add(PosixFilePermission.OWNER_WRITE);
            perms.add(PosixFilePermission.OWNER_EXECUTE);

            perms.add(PosixFilePermission.OTHERS_READ);
            perms.add(PosixFilePermission.OTHERS_WRITE);
            perms.add(PosixFilePermission.OTHERS_EXECUTE);

            perms.add(PosixFilePermission.GROUP_READ);
            perms.add(PosixFilePermission.GROUP_WRITE);
            perms.add(PosixFilePermission.GROUP_EXECUTE);

            Files.setPosixFilePermissions(Paths.get(file.getPath()), perms);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Change file permission error: " + e.getMessage());
        }

    }

    public void changeOwnership(File file, String user, String group) {
        Path path = Paths.get(file.getPath());
        logger.info("New user: " + user + ",  New group: " + group);

        // newUser and newGroup are strings.
        UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();
        logger.info(lookupService.toString());

        try {
            UserPrincipal userPrincipal = lookupService.lookupPrincipalByName(user);
            GroupPrincipal groupPrincipal = lookupService.lookupPrincipalByGroupName(group);
            Files.setAttribute(path, "posix:owner", userPrincipal, LinkOption.NOFOLLOW_LINKS);
            Files.setAttribute(path, "posix:group", groupPrincipal, LinkOption.NOFOLLOW_LINKS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Change file owner error: " + e.getMessage());
        }
    }
}
