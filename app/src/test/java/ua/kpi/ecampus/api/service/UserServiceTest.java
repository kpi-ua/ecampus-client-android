package ua.kpi.ecampus.api.service;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rx.observers.TestSubscriber;
import ua.kpi.ecampus.model.pojo.User;

import static org.junit.Assert.assertEquals;

public class UserServiceTest extends BaseServiceTest {

    private MockWebServer server;
    private UserService service;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        server = new MockWebServer();
        server.start();
        final Dispatcher dispatcher = new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) throws
                    InterruptedException {
                if (request.getPath().equals("/account/info")) {
                    return new MockResponse().setResponseCode(200)
                            .setBody(jsonReader.readString("json/user.json"));
                }
                return new MockResponse().setResponseCode(404);
            }
        };
        server.setDispatcher(dispatcher);
        HttpUrl baseUrl = server.url("/");
        service = ServiceCreator.createTestService(baseUrl.toString(),
                UserService.class);
    }

    @Test
    public void getUser() throws Exception {
        TestSubscriber<User> testSubscriber = new TestSubscriber<>();
        service.getUser(getToken()).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        User actual = testSubscriber.getOnNextEvents().get(0);
        assertEquals("1001", actual.id);
        assertEquals("Test account", actual.name);
        assertEquals(Integer.valueOf(300), actual.position.get(0).getId());
        assertEquals("KPI", actual.subdivision.get(0).getName());
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }
}
