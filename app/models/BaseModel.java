/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import java.util.Date;
import play.data.validation.Required;
import play.modules.morphia.Model;

/**
 *
 * @author goof
 */
public class BaseModel extends Model {

    public enum Valid {Y,N}
    
    public Date created;
    @Required(message="valid indicator required")
    public Valid valid;

    public BaseModel() {
        this.created = new Date();
        this.valid = Valid.Y;
    }

}
