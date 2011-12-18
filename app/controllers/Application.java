package controllers;

import java.util.Calendar;
import java.util.List;
import models.Idea;
import models.Topic;
import models.User;
import models.Valid;
import play.mvc.*;



public class Application extends Controller {

    public static void index() {
        List<Topic> topics = Topic.find("byValid", Valid.Y).fetchAll();
        render(topics);
    }
    
    public static void fix(String username) {
        User user = User.find("byUsername", username).first();
        Calendar cal = Calendar.getInstance();

        // Subtract 10 days from the calendar
        cal.add(Calendar.DATE, -10);
        System.out.println("10 days ago: " + cal.getTime());
        
        List<Idea> ideas = Idea.q().filter("created <", cal.getTime()).asList();
        for (Idea idea : ideas) {
            System.out.println("idea: " + idea.txt);
            idea.user = user;
            idea.save();
        }
        index();
    }

}