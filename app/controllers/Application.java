package controllers;

import java.util.List;
import models.Topic;
import play.mvc.*;



public class Application extends Controller {

    public static void index() {
        List<Topic> topics = Topic.find().fetchAll();
        render(topics);
    }

}