import models.Idea;
import models.Topic;
import org.junit.*;
import play.test.*;

public class BasicTest extends UnitTest {

    @Test
    public void addTopic() {
        long initial = Topic.count();
        Topic t = new Topic();
        t.title = "A test Topic";
        t.path = "a-test-topic";
        t.save();

        assertEquals("should be 1 more topic", initial + 1 , Topic.count());
    }


    @Test
    public void addIdea() {
        long initial = Idea.count();
        Idea i = new Idea();
        i.txt = "All code should be tested";
        i.topic = Topic.find().first();
        i.save();
        assertEquals("should be 1 more idea", initial + 1 , Idea.count());
    }

}
