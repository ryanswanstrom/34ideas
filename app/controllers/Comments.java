package controllers;

import models.Comment;
import models.Idea;
import play.data.validation.Validation;
import play.mvc.Controller;

public class Comments extends Controller {
    
    public static void create(String ideaId, Comment comment) {
        Idea idea = Idea.findById(ideaId);
        notFoundIfNull(idea);
        comment.idea = idea;
        if (!comment.validateAndCreate()) {            
            params.flash();
            Validation.keep();
        }
        
        Ideas.show(idea.topic.path, idea.path);
        
    }
    
}
