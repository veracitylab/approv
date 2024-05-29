package nz.ac.wgtn.veracity.approv.jbind;

import org.junit.jupiter.api.Test;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestBindings {
    @Test
    public void testActivityMappings() {
        assertTrue(0<Bindings.getActivityMappings().size());
    }

    @Test
    public void testActivityMapping1() throws URISyntaxException {
        Set<URI> activites = Bindings.getActivities("java.sql.DriverManager","getConnection",null);
        assertEquals(1,activites.size());
        assertEquals(new URI("https://veracity.wgtn.ac.nz/app-provenance#DBAccess"), activites.iterator().next());
    }

    @Test
    public void testActivityMapping2() throws URISyntaxException {
        Set<URI> activites = Bindings.getActivities("java.sql.PreparedStatement","executeUpdate","()I");
        assertEquals(1,activites.size());
        assertEquals(new URI("https://veracity.wgtn.ac.nz/app-provenance#DBWrite"), activites.iterator().next());
    }

    @Test
    public void testActivityMapping3() throws URISyntaxException {
        Set<URI> activites = Bindings.getActivities("java.sql.PreparedStatement","executeUpdate","(J)I");
        assertEquals(0,activites.size()); // wrong descriptor !
    }

    // this def has wildcards
    @Test
    public void testActivityMapping4() throws URISyntaxException {
        Set<URI> activites = Bindings.getActivities("java.sql.ResultSet","getInt","(I)I");
        assertEquals(1,activites.size()); // wrong descriptor !
        assertEquals(new URI("https://veracity.wgtn.ac.nz/app-provenance#DBRead"), activites.iterator().next());
    }


    @Test
    public void testEntityMappings() {
        assertTrue(0<Bindings.getEntityMappings().size());
    }

    @Test
    public void testEntityMappingRaw1() throws URISyntaxException {
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
    public void testEntityMappingRaw2() throws URISyntaxException {
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
