package com.apptechbd.haatbazaar.utils;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class StaffIdGenerator {

    private static final int SALT_SIZE = 16; // Size of the random salt in bytes

    public String generateStaffId(String email) throws Exception {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[SALT_SIZE];
        random.nextBytes(salt);

        // Combine email and salt (optional, adjust based on your needs)
        String combined = email + new String(salt, "UTF-8");

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(combined.getBytes("UTF-8"));
        byte[] hash = messageDigest.digest();

        // Truncate the hash to a desired length (adjust as needed)
        int truncatedLength = 16;
        byte[] truncatedHash = new byte[truncatedLength];
        System.arraycopy(hash, 0, truncatedHash, 0, truncatedLength);

        // Encode using android.util.Base64 (pre-API 26)
        String encodedId = android.util.Base64.encodeToString(truncatedHash, android.util.Base64.NO_WRAP);
        return encodedId;
    }
}
