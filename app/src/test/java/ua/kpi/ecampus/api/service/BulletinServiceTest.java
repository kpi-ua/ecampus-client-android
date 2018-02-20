package ua.kpi.ecampus.api.service;

import com.squareup.okhttp3.HttpUrl;
import com.squareup.okhttp3.mockwebserver.Dispatcher;
import com.squareup.okhttp3.mockwebserver.MockResponse;
import com.squareup.okhttp3.mockwebserver.MockWebServer;
import com.squareup.okhttp3.mockwebserver.RecordedRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import rx.observers.TestSubscriber;
import ua.kpi.ecampus.model.Recipient;
import ua.kpi.ecampus.model.pojo.Bulletin;
import ua.kpi.ecampus.model.pojo.Item;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class BulletinServiceTest extends BaseServiceTest {

    private MockWebServer server;
    private BulletinService service;
    private final String BULLETIN_ID = "1";
    private final String SUBDIV_ID = "1000";
    private final int LAST_ID = 3;
    private final int LIMIT = 10;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        server = new MockWebServer();
        server.start();
        final Dispatcher dispatcher = new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) throws
                    InterruptedException {
                String p = request.getPath();
                MockResponse response = new MockResponse().setResponseCode(200);
                if (p.matches("/board/all.*") ||
                        p.matches("/board/moderator/all.*")) {
                    return response.setBody(jsonReader.readString
                            ("json/bulletins.json"));
                } else if (p.equals("/board") ||
                        p.equals("/board/" + BULLETIN_ID)) {
                    return response.setBody(jsonReader.readString
                            ("json/ok.json"));
                } else if (p.equals("/board/" + BULLETIN_ID + "/recipient")) {
                    return response.setBody(jsonReader.readString
                            ("json/bull_recipients.json"));
                } else if (p.equals("/subdivision/" + SUBDIV_ID +
                        "/children")) {
                    return response.setBody(jsonReader.readString
                            ("json/desc_subdivision.json"));
                } else if (p.equals("/roles")) {
                    return response.setBody(jsonReader.readString("json/roles" +
                            ".json"));
                } else if (p.equals("/subdivision/" + SUBDIV_ID + "/group")) {
                    return response.setBody(jsonReader.readString
                            ("json/groups.json"));
                }
                return new MockResponse().setResponseCode(404);
            }
        };
        server.setDispatcher(dispatcher);
        HttpUrl baseUrl = server.url("/");
        service = ServiceCreator.createTestService(baseUrl.toString(),
                BulletinService.class);
    }

    @Test
    public void getBulletins() throws Exception {
        TestSubscriber<List<Bulletin>> testSubscriber = new TestSubscriber<>();
        service.getBulletins(getToken(), LIMIT, LAST_ID).subscribe
                (testSubscriber);

        // no errors
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<Bulletin> actual = testSubscriber.getOnNextEvents().get(0);
        // size does not exceed limit
        assertTrue(actual.size() <= LIMIT);
        // actual does not contain bulletin with id = lastId
        for (Bulletin b : actual)
            if (b.getId().equals(String.valueOf(LAST_ID)))
                fail("List must not contain element with id=LAST_ID");

        // correctness of fields
        Bulletin b = testSubscriber.getOnNextEvents().get(0).get(0);
        assertEquals("201", b.getId());
        assertEquals("Hello", b.getSubject());
        assertEquals("Test user", b.getCreatorName());
    }

    @Test
    public void getModeratorBulletins() throws Exception {
        TestSubscriber<List<Bulletin>> testSubscriber = new TestSubscriber<>();
        service.getModeratorBulletins(getToken(), LIMIT, -1).subscribe
                (testSubscriber);

        // no errors
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<Bulletin> actual = testSubscriber.getOnNextEvents().get(0);
        // size does not exceed limit
        assertTrue(actual.size() <= LIMIT);

        // correctness of fields
        Bulletin b = testSubscriber.getOnNextEvents().get(0).get(1);
        assertEquals("202", b.getId());
        assertEquals("Attention", b.getSubject());
    }

    @Test
    public void addBulletin() throws Exception {
        TestSubscriber<String> testSubscriber = new TestSubscriber<>();
        service.createBulletin(getToken(), getBulletin()).subscribe
                (testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertValue("OK");
    }

    @Test
    public void updateBulletin() throws Exception {
        TestSubscriber<String> testSubscriber = new TestSubscriber<>();
        service.updateBulletin(getToken(), BULLETIN_ID, getBulletin()).subscribe
                (testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertValue("OK");
    }

    @Test
    public void getRecipients() throws Exception {
        TestSubscriber<List<Recipient>> testSubscriber = new TestSubscriber<>();
        service.getRecipientsBy(getToken(), BULLETIN_ID).subscribe
                (testSubscriber);

        // no errors
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        // correctness of fields
        Recipient r = testSubscriber.getOnNextEvents().get(0).get(0);
        assertEquals("FIOT", r.getSubdivisionName());
        assertEquals("Teacher", r.getProfileName());
    }

    @Test
    public void getDescendantSubdivisions() throws Exception {
        TestSubscriber<List<Item>> testSubscriber = new TestSubscriber<>();
        service.getDescendantSubdivisions(SUBDIV_ID).subscribe
                (testSubscriber);
        // no errors
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<Item> actual = testSubscriber.getOnNextEvents().get(0);
        assertEquals(5, actual.size());
        assertEquals(Integer.valueOf(10102), actual.get(2).getId());
        assertEquals("FIOT 3", actual.get(2).getName());
    }

    @Test
    public void getRoles() throws Exception {
        TestSubscriber<List<Item>> testSubscriber = new TestSubscriber<>();
        service.getRoles().subscribe(testSubscriber);
        // no errors
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<Item> actual = testSubscriber.getOnNextEvents().get(0);
        assertEquals(3, actual.size());
        assertEquals(Integer.valueOf(5), actual.get(1).getId());
        assertEquals("Student", actual.get(1).getName());
    }

    @Test
    public void getGroupsInSubdivision() throws Exception {
        TestSubscriber<List<Item>> testSubscriber = new TestSubscriber<>();
        service.getGroupsIn(SUBDIV_ID).subscribe(testSubscriber);
        // no errors
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<Item> actual = testSubscriber.getOnNextEvents().get(0);
        assertEquals(1, actual.size());
        assertEquals(Integer.valueOf(3), actual.get(0).getId());
        assertEquals("AB", actual.get(0).getName());
    }

    @Test
    public void getRecipients_Incorrect() {
        try {
            service.getRecipientsBy(getToken(),("IncorrectRequest")).subscribe();
            fail();
        } catch (Exception expected) {
            assertEquals("HTTP 404 OK", expected.getMessage());
        }
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    private Bulletin getBulletin() {
        return new Bulletin("1", "subject", "author", "2016");
    }

}
