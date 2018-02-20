package ua.kpi.ecampus.util;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CollectionValidatorTest {

    @Test
    public void validateOnNull() throws Exception {
        try {
            CollectionValidator.validateOnNull(null);
            fail();
        } catch (Exception e) {
            assertEquals("The collection cannot be null", e.getMessage());
        }
    }

    @Test
    public void isEmpty_true() {
        assertTrue(CollectionValidator.isEmpty(new ArrayList<>()));
    }

    @Test
    public void isEmpty_false() {
        assertFalse(CollectionValidator.isEmpty(new ArrayList<Integer>() {{
            add(0);
        }}));
    }
}
