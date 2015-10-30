package com.todos.hkboam.todos.Utils;

/**
 * Created by mohamedamin on 18/10/2015.
 */
public class Util {

    /**
     * Methode permettant de convertir une chaine de caractere en une cle md5
     *
     * @param md5
     *            : chaine a convertir
     * @return chaine convertie
     */
    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}
