import models.Comment;
import models.Idea;
import models.Topic;
import models.Vote;
import models.Vote.VoteType;
import org.junit.*;
import play.data.validation.Validation;
import play.test.*;

public class BasicTest extends UnitTest {

    @Before
    public void setup() {
        MorphiaFixtures.deleteAllModels();
        Validation.clear();
        MorphiaFixtures.loadModels("../conf/initial-data.yml");
    }

    @Test
    public void getData() {
        assertEquals("should be 3 topics", 3, Topic.count());
        assertEquals("should be 20 ideas", 20, Idea.count());
    }

    @Test
    public void addTopic() {
        long initial = Topic.count();
        Topic t = new Topic();
        t.title = "A test Topic";
        t = t.save();
        assertEquals("should be 1 more topic", initial + 1 , Topic.count());
    }

    @Test
    public void addIdea() {
        long initial = Idea.count();
        Idea i = new Idea();
        i.txt = "All code should be tested";
        i.topic = Topic.find().first();
        i = i.save();
        assertEquals("should be 1 more idea", initial + 1 , Idea.count());
        assertEquals("path should be set", "All-code-should-be-tested", i.path);
    }

    @Test
    public void testVotes() {
        Idea idea = Idea.find().first();
        assertEquals("should be no good votes", Long.valueOf(0), idea.gVotes);
        assertEquals("should be no bad votes", Long.valueOf(0), idea.bVotes);
        
        long voteCount = Vote.count();

        idea = idea.vote(VoteType.BAD.toString());
        assertEquals("should be 1 more vote", voteCount + 1 , Vote.count());
        assertEquals("should be no good votes", Long.valueOf(0), idea.gVotes);
        assertEquals("should be 1 bad vote", Long.valueOf(1), idea.bVotes);
        idea = idea.vote(VoteType.GOOD.toString());
        assertEquals("should be 2 more vote", voteCount + 2 , Vote.count());
        assertEquals("should be 1 good vote", Long.valueOf(1), idea.gVotes);
        assertEquals("should be 1 bad vote", Long.valueOf(1), idea.bVotes);
        idea = idea.vote("unknown string");
        assertEquals("should be 3 more vote", voteCount + 3 , Vote.count());
        assertEquals("should be 1 good vote", Long.valueOf(1), idea.gVotes);
        assertEquals("should be 2 bad votes", Long.valueOf(2), idea.bVotes);
    }

    @Test
    public void testComment() {
        assertEquals("should be 1 comment", 1, Comment.count());
        Comment comment = new Comment();
        assertFalse("no txt or idea", comment.validateAndSave());
        comment.idea = Idea.find().first();
        assertFalse("txt is null", comment.validateAndSave());
        comment.txt = "There are many sites like this.";
        assertTrue("this should work", comment.validateAndSave());
    }
    
    @Test
    public void testUser() {
        
    }
}
