
import models.Idea;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.modules.morphia.Model;
import play.test.MorphiaFixtures;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author goof
 */
@OnApplicationStart
public class Bootstrap extends Job {

    @Override
    public void doJob() {
        if (play.Play.mode == play.Play.mode.DEV) {
            System.out.println("delete all");
            MorphiaFixtures.deleteAllModels();
        }
        if (Idea.count() == 0) {
            System.out.println("loading yaml");
            MorphiaFixtures.loadModels("initial-data.yml");
        }
        Model.ds().ensureIndexes();
    }
}
