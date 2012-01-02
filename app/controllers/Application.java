package controllers;

import java.util.List;
import models.BaseModel.Valid;
import models.Topic;
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