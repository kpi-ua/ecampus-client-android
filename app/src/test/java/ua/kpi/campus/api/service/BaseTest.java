package ua.kpi.campus.api.service;

import org.junit.Before;

/**
 * Base class for tests of service classes
 * Created by Admin on 18.05.2016.
 */
public class BaseTest {

    public JsonReader jsonReader;

    @Before
    public void setUp() throws Exception {
        jsonReader = new JsonReader();
    }
}
