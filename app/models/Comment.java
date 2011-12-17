package models;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;

@Entity
public class Comment extends BaseModel {
    
    @Required(message="Comment is required")
    @MinSize(value=1,message="Must be at least one character")
    @MaxSize(value=10000,message="Must be less than 10,000 characters")
    public String txt;
    
    @Reference
    @Required
    public Idea idea;
    
    @Reference
    public User user;
    
}
