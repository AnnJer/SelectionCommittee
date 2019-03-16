package dataAccess;

import org.postgresql.util.MD5Digest;
import user.User;

import java.math.BigInteger;
import java.security.CryptoPrimitive;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Crypto {

   private static byte[] getEncodingSalt() {
       return new byte[]{'c', '+', '+', 'r', 'u', 'l', 'i', 't', '!'};
   }

    public static byte[] encodePassword(byte[] user, byte[] password) {
        return MD5Digest.encode(user, password, getEncodingSalt());
    }

    public static String createToken(User user) {

        String input = user.getLogin() + new Date().toString();

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] messageDigest = md.digest(input.getBytes());

        BigInteger no = new BigInteger(1, messageDigest);

        String hashtext = no.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }

}
