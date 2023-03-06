package nz.ac.wgtn.veracity.approv.jbind;

import java.util.Set;

/**
 * Service interface for a provider of entity bindings. To be used with service loaders.
 * @author jens dietrich
 */
public interface EntityMappingProvider {
    Set<EntityMapping> getEntityMappings() throws Exception ;
}
