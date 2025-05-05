package com.group20.dailyreadingtracker.securitymodule.util;

import java.security.MessageDigest;
import java.util.Base64;

public class IntegrityUtil {

    public static String generateChecksum(String data) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(data.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

    public static boolean verifyChecksum(String data, String checksum) throws Exception {
        String generatedChecksum = generateChecksum(data);
        return generatedChecksum.equals(checksum);
    }
}