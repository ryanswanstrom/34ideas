package controllers;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import models.User;

import models.Valid;
import org.apache.commons.lang.StringUtils;

import play.Logger;
import play.libs.Codec;

/**
 * 
 * Implements OWASP recommendations for Java
 * https://www.owasp.org/index.php/Hashing_Java
 * 
 */
public class Security extends Secure.Security {

    static boolean authenticate(String username, String password) {
        Logger.info("the user logging in is %s", username);
        User user = User.find("byUsernameAndValidAndActive", username, Valid.Y, Valid.Y).first();

        if (user == null) {
            return false;
        } else {
            // hash the pw and compare
            return validateLogin(user, password);
        }
    }

    private static boolean validateLogin(User user, String attemptedPw) {
        boolean rVal = false;
        if (null != user && StringUtils.isNotBlank(user.salt) && null != user.hashIters) {
            try {
                // get user.salt and user.hashIters
                // then hash attemptedPw
                byte[] hashedPw = getHash(user.hashIters, attemptedPw, Codec.decodeBASE64(user.salt));
                // compare with user.password
                rVal = Arrays.equals(hashedPw, Codec.decodeBASE64(user.password));
            } catch (NoSuchAlgorithmException e) {
                Logger.error(e, "cannot hash password for user[%]", user.username);
                rVal = false;
            } catch (UnsupportedEncodingException e) {
                Logger.error(e, "bad String encoding for user[%]", user.username);
                rVal = false;
            }

        }
        
            Logger.info("user [%s] has a validated password [%s]", user.username, rVal);
        return rVal;
    }

    /**
     * From a password, a number of iterations and a salt, returns the
     * corresponding digest
     * 
     * @param iters
     *            int The number of iterations of the algorithm
     * @param password
     *            String The password to encrypt
     * @param salt
     *            byte[] The salt
     * @return byte[] The digested password
     * @throws NoSuchAlgorithmException
     *             If the algorithm doesn't exist
     * @throws UnsupportedEncodingException 
     */
    public static byte[] getHash(int iters, String password, byte[] salt)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        digest.reset();
        digest.update(salt);
        byte[] input = digest.digest(password.getBytes("UTF-8"));
        for (int i = 0; i < iters; i++) {
            digest.reset();
            input = digest.digest(input);
        }
        return input;
    }

//    public static void onAuthenticated() {
//        Logger.info("in onAuthenticated: %s", connected());
//        UsersOpen.show(connected());
//    }
}
