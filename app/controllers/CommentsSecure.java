package controllers;

import models.BaseModel.Valid;
import models.Comment;
import models.Idea;
import models.User;
import play.data.validation.Validation;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class CommentsSecure extends Controller {
    
    public static void create(String ideaId, Comment comment) {
        Idea idea = Idea.findById(ideaId);
        notFoundIfNull(idea);
        comment.idea = idea;
        comment.user = User.find("byUsernameAndValid", Security.connected(), Valid.Y).first();
        if (!comment.validateAndCreate()) {            
            params.flash();
            Validation.keep();
        }
        
        Ideas.show(idea.topic.path, idea.path);
        
    }
    
}
