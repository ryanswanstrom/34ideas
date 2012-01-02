package controllers;

import java.util.List;
import models.Topic;
import models.Valid;
import play.mvc.*;

public class Application extends Controller {

    public static void index() {
        List<Topic> topics = Topic.find("byValid", Valid.Y).order("created").fetchAll();
        render(topics);
    }

    public static void tos() {
        render();
    }

    public static void privacy() {
        render();
    }

}