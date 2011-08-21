/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.utils.IndexDirection;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;

/**
 *
 * @author goof
 */
public abstract class TitleModel extends BaseModel {
    @MaxSize(value=100,message="Must be less than 100 characters")
    public String title;
    @Required(message="Text is required")
    @MinSize(value=1,message="Must be at least one character")
    @MaxSize(value=10000,message="Must be less than 10,000 characters")
    public String txt;
    @Indexed(value = IndexDirection.ASC, name = "idx_path", unique = true, dropDups = true)
    public String path;

}
