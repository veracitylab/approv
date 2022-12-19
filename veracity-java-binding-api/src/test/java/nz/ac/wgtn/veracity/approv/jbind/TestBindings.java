package nz.ac.wgtn.veracity.approv.jbind;

import org.junit.jupiter.api.Test;
import java.net.URI;
import java.net.URISyntaxException;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestBindings {
    @Test
    public void testActivityMappings() {
        assertTrue(0<Bindings.getActivityMappings().size());
    }

    @Test
    public void testEntityMappings() {
        assertTrue(0<Bindings.getEntityMappings().size());
    }

    @Test
    public void testEntityMapping1() throws URISyntaxException {
        EntityMapping map = new EntityMapping();
        map.setEntity(new URI("https://veracity.wgtn.ac.nz/app-provenance#Database"));
        map.setGroup("jdbc");
        Execution execution = new Execution();
        execution.setOwner("java.sql.DriverManager");
        execution.setName("getConnection");
        map.setExecution(execution);
        map.setSource(EntityRef.ARG);
        map.setSourceIndex(0);
        map.setTarget(EntityRef.RETURN);
        map.setCreate(true);

        assertTrue(Bindings.getEntityMappings().contains(map));
    }

    @Test
    public void testEntityMapping2() throws URISyntaxException {
        EntityMapping map = new EntityMapping();
        Execution execution = new Execution();
        execution.setOwner("java.sql.Connection");
        execution.setName("createStatement");
        map.setExecution(execution);
        map.setSource(EntityRef.THIS);
        map.setTarget(EntityRef.RETURN);
        map.setCreate(false);

        assertTrue(Bindings.getEntityMappings().contains(map));
    }
}
