package com.todos.hkboam.todos.Utils;

import android.content.Context;

import com.todos.hkboam.todos.bdd.modal.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Created by mohamedamin on 18/10/2015.
 */
public class Util {

    /**
     * Methode permettant de convertir une chaine de caractere en une cle md5
     *
     * @param md5 : chaine a convertir
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

    public void saveUserInFile(Context c, User u, String fileName) {
        String FILENAME = fileName;
        String content = u.getId() + "\n" + u.getUsername() + "\n" + u.getMail() + "\n" + u.getPassword();
        try {
            FileOutputStream fos = c.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(content.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUserFromFile(Context c, String fileName) {
        User u;
        String s = "";
        try {
            FileInputStream fis = c.openFileInput(fileName);
            InputStreamReader inputRead = new InputStreamReader(fis);

            char[] inputBuffer = new char[100];

            int charRead;

            while ((charRead = inputRead.read(inputBuffer)) > 0) {
                // char to string conversion
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                s += readstring;
            }
            inputRead.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (s.length() > 0) {
            String[] t = s.split("\n");
            if (t.length >= 4) {
                u = new User(Long.parseLong(t[0]), t[1], t[2], t[3]);
                return u;
            }
        }
        return null;
    }
}
