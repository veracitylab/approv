package nz.ac.wgtn.veracity.approv.jbind;

import java.util.Set;

/**
 * Service interface for a procider of Activity Bindings. To be used with service loaders.
 * @author jens dietrich
 */
public interface ActivityMappingProvider {
    Set<ActivityMapping> getActivityMappings() throws Exception;
}
