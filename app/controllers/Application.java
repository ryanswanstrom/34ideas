package controllers;

import java.util.List;
import models.BaseModel.Valid;
import models.Topic;
import models.Vote;
import play.mvc.*;

public class Application extends Controller {

    public static void index() {
        List<Topic> topics = Topic.find("byValid", Valid.Y).order("-created").fetchAll();
        render(topics);
    }

    public static void tos() {
        render();
    }

    public static void privacy() {
        render();
    }

    /**
     * This method could easily be moved to a Votes Controller if one is 
     * ever needed.
     */
    public static void showAllVotes() {
        List<Vote> votes = Vote.find("byValid", Valid.Y).order("-created").fetch(10);
        render(votes);
    }
}