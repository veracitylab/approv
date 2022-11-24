package nz.ac.wgtn.veracity.approv.jbind;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestBindings {
    @Test
    public void testParseSomething() {
        assertTrue(0<Bindings.getActivityMappings().size());
    }
}
