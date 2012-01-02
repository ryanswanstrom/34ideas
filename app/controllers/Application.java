package controllers;

import java.util.List;
import models.Comment;
import models.Idea;
import models.Topic;
import models.User;
import models.Valid;
import play.Logger;
import play.mvc.*;

public class Application extends Controller {

    public static void index() {
        List<Topic> topics = Topic.find("byValid", Valid.Y).order("_created").fetchAll();
        render(topics);
    }

    public static void tos() {
        render();
    }

    public static void privacy() {
        render();
    }

    public static void update() {
        //idea
        List<Idea> items = Idea.find().order("created").fetchAll();
        for (Idea item : items) {
            item.save();
        }
        Logger.info("items updated");

        // comment
        List<Comment> items2 = Comment.find().order("created").fetchAll();
        for (Comment item : items2) {
            item.save();
        }
        Logger.info("comments updated");

        // topic
        List<Topic> items3 = Topic.find().order("created").fetchAll();
        for (Topic item : items3) {
            item.save();
        }
        Logger.info("topics updated");

        // user
        List<User> items4 = User.find().order("created").fetchAll();
        for (User item : items4) {
            item.save();
        }
        Logger.info("user updated");


        Application.index();

    }
}