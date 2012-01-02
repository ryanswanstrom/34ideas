/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.BaseModel.Valid;
import models.Idea;
import models.Topic;
import models.User;
import play.data.validation.Validation;
import play.mvc.Controller;
import play.mvc.With;

/**
 *
 * @author goof
 */
@With(Secure.class)
public class IdeasSecure extends Controller {
    
    /**
     *
     * @param path the topic path
     */
    public static void add(String path) {
        Topic topic = Topic.find("byPathAndValid", path, Valid.Y).first();
        notFoundIfNull(topic);
        Idea idea = new Idea();
        idea.topic = topic;
        render(idea);
    }

    /**
     *
     * @param topicId
     * @param txt
     */
    public static void create(String topicId, String txt) {
        Topic topic = Topic.findById(topicId);
        notFoundIfNull(topic);
        Idea idea = new Idea();
        idea.txt = txt;
        idea.topic = topic;
        idea.user = User.find("byUsernameAndValid", Security.connected(), Valid.Y).first();

        if (!idea.validateAndSave() ) {
            params.flash();
            Validation.keep();
            add(topic.path);
        }
        Ideas.show(idea.topic.path, idea.path);
    }
    
}
