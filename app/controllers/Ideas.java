package controllers;

import java.util.List;
import java.util.Random;
import models.Idea;
import models.Topic;
import models.Valid;
import models.Vote;
import play.data.validation.Validation;
import play.mvc.Controller;

/**
 *
 * @author goof
 */
public class Ideas extends Controller {

    /**
     *
     * @param path the topic path
     * @param id
     */
    public static void show(String topicPath, String ideaPath) {
        Idea idea = Idea.find("byPathAndValid", ideaPath, Valid.Y).first();
        notFoundIfNull(idea);
        render(idea);
    }

    /**
     *
     * @param path the path of the topic
     */
    public static void randomIdea(String topicPath) {
        Topic topic = Topic.find("byPathAndValid", topicPath, Valid.Y).first();
        notFoundIfNull(topic);
        Idea idea = Idea.find("byTopic", topic).from(new Random().nextInt((int)(Idea.count("byTopic", topic)))).order("created").first();
        notFoundIfNull(idea);
        show(idea.topic.path, idea.path);
    }

    /**
     *
     * @param id the idea id
     */
    public static void vote(String id, String value) {
        Idea idea = Idea.findById(id);
        notFoundIfNull(idea);
        idea.vote(value);
        randomIdea(idea.topic.path);
    }

    /**
     *
     * @param path the topic path
     */
    public static void add(String path) {
        Topic topic = Topic.find("byPathAndValid", path, Valid.Y).first();
        notFoundIfNull(topic);
        Idea idea = new Idea();
        idea.topic = topic;
        render(idea);
    }

    /**
     *
     * @param path the topic path
     */
    public static void create(String topicId, String txt) {
        Topic topic = Topic.findById(topicId);
        notFoundIfNull(topic);
        Idea idea = new Idea();
        idea.txt = txt;
        idea.topic = topic;

        if (!idea.validateAndSave() ) {
            params.flash();
            Validation.keep();
            Ideas.add(topic.path);
        }
        show(idea.topic.path, idea.path);
    }

    public static void newest(String topicPath) {
        notFoundIfNull(topicPath);
        Topic topic = Topic.find("byPathAndValid", topicPath, Valid.Y).first();
        notFoundIfNull(topic);
        List<Idea> ideas = Idea.find("byTopicAndValid", topic, Valid.Y).order("-created").fetchAll();

        render(topic, ideas);

    }


    public static void popular(String topicPath) {
        notFoundIfNull(topicPath);
        Topic topic = Topic.find("byPathAndValid", topicPath, Valid.Y).first();
        notFoundIfNull(topic);
        List<Idea> ideas = Idea.find("byTopicAndValid", topic, Valid.Y).order("-approval").order("-gVotes").fetchAll();

        render(topic, ideas);

    }

}
