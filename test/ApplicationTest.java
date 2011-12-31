
import org.junit.Test;
import play.mvc.Http.Response;
import play.test.FunctionalTest;



public class ApplicationTest extends FunctionalTest {

    @Test
    public void testThatIndexPageWorks() {
        Response response = GET("/");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
        assertContentMatch("34ideas", response);
    }

    @Test
    public void testrandomIdeaPage() {
        Response response = GET("/random/startups");
        assertStatus(302, response);
    }
    
    @Test
    public void testIdeaPage() {
        Response response = GET("/ideas/startups/kids-shirts");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
        assertContentMatch("34ideas", response);
        assertContentMatch("shirts", response);
    }

    @Test
    public void testAddPage() {
        Response response = GET("/add/idea/startups");
        // redirect
        assertStatus(302, response);
//        assertContentType("text/html", response);
//        assertCharset(play.Play.defaultWebEncoding, response);
//        assertContentMatch("34ideas", response);
//        assertContentMatch("Startup Ideas", response);
//        assertContentMatch("Submit", response);
    }
    
    @Test
    public void testPrivacyPage() {
        Response response = GET("/privacy");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
        assertContentMatch("34ideas", response);
        assertContentMatch("Privacy Policy", response);
    }
    
    @Test
    public void testTermsPage() {
        Response response = GET("/terms");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
        assertContentMatch("34ideas", response);
        assertContentMatch("Terms of Service", response);
    }

//    @Test
//    public void testUnknownPage() {
//        Response response = GET("/pagedoesnotexist");
//        assertIsOk(response);
//        assertContentType("text/html", response);
//        assertCharset(play.Play.defaultWebEncoding, response);
//        assertContentMatch("Not Found", response);
//    }
    
}