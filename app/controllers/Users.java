package controllers;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;

import java.util.List;
import models.BaseModel.Valid;
import models.Idea;
import models.User;

import org.apache.commons.mail.SimpleEmail;

import play.Logger;
import play.data.validation.Email;
import play.data.validation.Equals;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.libs.Codec;
import play.libs.Mail;
import play.mvc.Controller;
import play.mvc.Http.Request;

public class Users extends Controller {

    /**
     * The number of times to hash a password.
     * Why 1153? it is a prime number
     */
    private static final int HASH_ITERS = 1153;

    public static void register() {
        if (Security.isConnected()) {
            Users.show(Security.connected());
        }
        render();
    }

    public static void add(@Required @MinSize(2) String username, @Required @MinSize(6) String password, @Required @Equals("password") String pwConfirm, @Required @Email String email) throws Throwable {
        if (validation.hasErrors()) {
            params.flash();
            validation.keep();
            register();
        }
        if (User.count("byUsername", username) > 0) {
            validation.addError("username", "username already taken");
            params.flash();
            validation.keep();
            register();
        }
        if (User.count("byEmail", email) > 0) {
            validation.addError("email", "email already taken");
            params.flash();
            validation.keep();
            register();
        }


        User u = new User();
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            // Salt generation 64 bits long
            byte[] bSalt = new byte[8];
            random.nextBytes(bSalt);

            u.username = username;
            u.email = email;
            u.lastLogin = new Date();
            u.hashIters = HASH_ITERS;
            u.password = Codec.encodeBASE64(Security.getHash(u.hashIters, password, bSalt));
            u.salt = Codec.encodeBASE64(bSalt);
        } catch (NoSuchAlgorithmException e) {
            Logger.error(e, "bad SecureRandom instance SHA1PRNG");
            Validation.addError("password", "The password cannot be hashed.");
            Application.index();
        } catch (UnsupportedEncodingException e) {
            Logger.error(e, "bad encoding UTF-8");
            Validation.addError("password", "The password cannot be encoded properly.");
            Application.index();
        }

        if (!u.validateAndCreate()) {
            Validation.keep();
            params.flash();
            register();
        }
        // send verificationEmail
        SimpleEmail msg = new SimpleEmail();
        msg.setFrom("support@34ideas.com");
        msg.addTo(u.email);
        msg.setSubject("Welcome to 34ideas");
        msg.setMsg("Please click (or copy and paste into a browser) the link to verify your email. " + Request.current().host + "/verify/email/" + u.uuid);
        Mail.send(msg);
        // add to flash
        flash.success("Please click the verification link in your email.");
        Secure.login();
    }

    public static void activate(String uuid) throws Throwable {
        // look up uuid
        User user = User.find("byUuidAndValid", uuid, Valid.Y).first();
        notFoundIfNull(user);
        // activate user
        user.activate();
        flash.success("email verified correctly, please login");
        flash.put("username", user.username);
        Secure.login();
    }

    /**
     * this will display the public page for any username
     * @param username
     */
    public static void show(String username) {
        User user = User.find("byUsernameAndValid", username, Valid.Y).first();
        notFoundIfNull(user);
        List<Idea> ideas = Idea.find("byUserAndValid", user, Valid.Y).order("created").fetchAll();
        render(user, ideas);
    }

    public static void forgotUsername() {
        render();
    }

    public static void sendUsername(@Required @Email String email) {
        if (validation.hasErrors()) {
            params.flash();
            validation.keep();
            forgotUsername();
        }


    }
}
