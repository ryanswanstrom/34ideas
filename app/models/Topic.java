package models;

import com.google.code.morphia.annotations.Entity;

/**
 *
 * @author goof
 */
@Entity(noClassnameStored=true)
public class Topic extends TitleModel {

    public Topic() {
        this("");
    }
    
    public Topic(String text) {
        this.txt = text;
    }

    @Override
    public String toString() {
        return this.title;
    }

}
