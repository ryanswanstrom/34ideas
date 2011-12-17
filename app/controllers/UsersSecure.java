package controllers;

import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class UsersSecure extends Controller {

    public static void edit() {
        Users.show(Security.connected());
    }

    public static void update() {
        Users.show(Security.connected());
    }
}
