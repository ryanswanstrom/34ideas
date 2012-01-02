package models;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.utils.IndexDirection;
import java.util.Date;
import java.util.UUID;
import play.data.validation.Email;
import play.data.validation.Required;

@Entity(noClassnameStored = true)
public class User extends BaseModel {

    @Required
    @Indexed(value=IndexDirection.ASC, name="idx_username", unique=true, dropDups=true) 
    public String username;
    @Email
    @Required
    @Indexed(value=IndexDirection.ASC, name="idx_email", unique=true, dropDups=true) 
    public String email;
    @Required
    public String password;
    @Required
    public String salt;
    public Integer hashIters;
    public Date lastLogin;
    public Valid active;
    public String uuid;

    public User() {
        this.active = Valid.N;
        this.uuid = UUID.randomUUID().toString();
        this.created = new Date();
    }

    public void activate() {
        this.active = Valid.Y;
        this.save();
    }

    @Override
    public String toString() {
        return this.username;
    }
}
