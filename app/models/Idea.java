package models;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import play.data.validation.Max;
import play.data.validation.Min;
import play.data.validation.Required;
import play.data.validation.URL;
import play.modules.morphia.Model;

/**
 *
 * @author goof
 */
@Entity(noClassnameStored=true)
public class Idea extends TitleModel {

    @Min(0)
    public Long gVotes;
    @Min(0)
    public Long bVotes;
    /** this is the percentage of good idea votes */
    @Min(0)
    @Max(100)
    public Long approval;
    @Reference
    @Required
    public Topic topic;
    @URL
    public String source;  // url of where the idea came from

    public Idea() {
        this("");
    }

    public Idea(String text) {
        this.bVotes = 0l;
        this.gVotes = 0l;
        this.approval = 0l;
        this.txt = text;
    }

    @Override
    public <T extends Model> T save() {
        this.createPath();
        // todo: verify unique path
        while (this.isNew() && count("byPath", path) > 0) {
            path += "-" + UUID.randomUUID().toString();
        }
        return super.save();
    }

    protected void createPath() {
        if (StringUtils.isBlank(path)) {
            if (StringUtils.isBlank(txt)) {
                path = UUID.randomUUID().toString();
            } else {
                path = StringUtils.substring(StringUtils.trim(txt), 0, 50).replaceAll("[^a-zA-Z0-9\\s]", "").replace(' ', '-');
            }
        }
    }

    public <T extends Model> T vote(String value) {
        if (Vote.GOOD.toString().equals(value)) {
            this.gVotes++;
        } else {
            this.bVotes++;
        }
        this.approval = 100*(this.gVotes)/(this.gVotes + this.bVotes);
        return this.save();
    }
    
    @Override
    public String toString() {
        return this.txt;
    }

}
