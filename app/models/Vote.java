package models;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;
import play.data.validation.Required;

@Entity(noClassnameStored=true)
public class Vote extends BaseModel {

    public enum VoteType {
        GOOD, BAD
    }
    
    @Reference
    public User user;
    
    @Required
    public VoteType type;
    
    @Reference
    @Required
    public Idea idea;
    
    
}