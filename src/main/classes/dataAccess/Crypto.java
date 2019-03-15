package dataAccess;

import org.postgresql.util.MD5Digest;

public class Crypto {

   private static byte[] getEncodingSalt() {
       return new byte[]{'c', '+', '+', 'r', 'u', 'l', 'i', 't', '!'};
   }

    public static byte[] encodePassword(byte[] user, byte[] password) {
        return MD5Digest.encode(user, password, getEncodingSalt());
    }

}
