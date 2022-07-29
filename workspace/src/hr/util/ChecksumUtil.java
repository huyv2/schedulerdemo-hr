package hr.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChecksumUtil {
	public static String md5Checksum(String clearData) throws NoSuchAlgorithmException {
		String hashingData = "";
        
        // MessageDigest instance for MD5
        MessageDigest md = MessageDigest.getInstance("MD5");
        // Update MessageDigest with input text in bytes
        md.update(clearData.getBytes(StandardCharsets.UTF_8));
        // Get the hashbytes
        byte[] hashBytes = md.digest();
        //Convert hash bytes to hex format
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        // Print the hashed text
        hashingData = sb.toString();
		
		return hashingData;
	}
}
