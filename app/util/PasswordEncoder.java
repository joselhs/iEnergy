package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Sebastian Beigel
 *
 */
public class PasswordEncoder {

    public static String encodePassword(String rawPass, String salt) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("MD5 not supported!");
        }

        byte[] digest;

        String pass = mergePasswordAndSalt(rawPass, salt);
        try {
            digest = messageDigest.digest(pass.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 not supported!");
        }

        return new String(Hex.encode(digest));
    }
    
    protected static String mergePasswordAndSalt(String password, Object salt) {
        if (password == null) {
            password = "";
        }

        if ((salt == null) || "".equals(salt)) {
            return password;
        } else {
            return password + "{" + salt.toString() + "}";
        }
    }

}
