package controllers;

import java.util.List;
import java.util.Random;
import models.BaseModel.Valid;
import models.Comment;
import models.Idea;
import models.Topic;
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
        Comment comment = new Comment();
        comment.idea = idea;
        render(idea, comment);
    }

    /**
     *
     * @param path the path of the topic
     */
    public static void randomIdea(String topicPath) {
        Topic topic = Topic.find("byPathAndValid", topicPath, Valid.Y).first();
        notFoundIfNull(topic);
        Idea idea = Idea.find("byTopicAndValid", topic, Valid.Y).from(new Random().nextInt((int)(Idea.count("byTopicAndValid", topic, Valid.Y)))).order("created").first();
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
        idea.vote(value, Security.connected());
        randomIdea(idea.topic.path);
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
        List<Idea> ideas = Idea.find("byTopicAndValid", topic, Valid.Y).order("-gVotes").order("-approval").fetchAll();

        render(topic, ideas);

    }

}
