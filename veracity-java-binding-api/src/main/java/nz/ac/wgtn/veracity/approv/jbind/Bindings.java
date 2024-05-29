package nz.ac.wgtn.veracity.approv.jbind;

import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Main API entry point.
 * Has some methods that facilitate efficient querying, to be used in instrumentation.
 * @author jens dietrich
 */
public class Bindings {


    private static Set<ActivityMapping> activityMappings = null;
    private static Set<EntityMapping> entityMappings = null;
    static {
        try {
            initBindings();
        }
        catch (Exception x) {
            System.err.println("exception initialising bindings");
            x.printStackTrace();
        }
    }

    // API methods start here
    public static Set<ActivityMapping> getActivityMappings() {
        return activityMappings;
    }

    public static Set<EntityMapping> getEntityMappings() {
        return entityMappings;
    }

    /**
     * Baseline method, not efficient at the moment.
     * @param calleeOwner
     * @param calleeName
     * @param calleeDescriptor
     * @return
     */
    // @TODO optimise access (create index, hashmap etc)
    public static Set<URI> getActivities (String calleeOwner, String calleeName,String calleeDescriptor) {

        Set<URI> collect = getActivityMappings().stream()
            .filter(m -> m.getExecutions().stream().anyMatch(exe->exe.matches(calleeOwner,calleeName,calleeDescriptor)))
            .map(m -> m.getActivity())
            .collect(Collectors.toSet());
        return collect;
    }

    /**
     * Get instructions to create entities at a call site.
     * An instruction specifies which data to take from a callsite (arg, return, this), and the
     * type of entity to create.
     * @param calleeOwner
     * @param calleeName
     * @param calleeDescriptor
     * @return
     */
    // @TODO optimise access (create index, hashmap etc)
    public static Set<EntityCreation> getEntityCreations (String calleeOwner, String calleeName,String calleeDescriptor) {
        Set<EntityCreation> collect = getEntityMappings().stream()
            .filter(m -> m.getExecution().matches(calleeOwner,calleeName,calleeDescriptor))
            .filter(m -> m.isCreate())
            .map(m -> new EntityCreation(m.getEntity(), m.getSource(), m.getSourceIndex(), m.getTarget(), m.getTargetIndex()))
            .collect(Collectors.toSet());
        return collect;
    }

    /**
     * Get instructions to transfer entities at a call site.
     * An instruction specifies how an already created entity associated with an object is propagated to another object,
     * for instance, from the first argument of a method call to the return type.
     * Propagation means that at the end, the enity is associated with an additional object.
     * @param calleeOwner
     * @param calleeName
     * @param calleeDescriptor
     * @return
     */
    // @TODO optimise access (create index, hashmap etc)
    public static Set<EntityPropagation> getEntityPropagations (String calleeOwner, String calleeName,String calleeDescriptor) {
        Set<EntityPropagation> collect = getEntityMappings().stream()
            .filter(m -> m.getExecution().matches(calleeOwner,calleeName,calleeDescriptor))
            .filter(m -> !m.isCreate())
            .map(m -> new EntityPropagation(m.getSource(),m.getSourceIndex(),m.getTarget(),m.getTargetIndex()))
            .collect(Collectors.toSet());
        return collect;
    }

    // API methods end here

    private static void initBindings() throws Exception {

        Set<ActivityMapping> activityMappings2 = new HashSet<>();
        ServiceLoader<ActivityMappingProvider> activityMappingsProviders = ServiceLoader.load(ActivityMappingProvider.class);
        activityMappingsProviders.stream().forEach(loader ->
        {
            try {
                activityMappings2.addAll(loader.get().getActivityMappings());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        activityMappings = Collections.unmodifiableSet(activityMappings2);


        Set<EntityMapping> entityMappings2 = new HashSet<>();
        ServiceLoader<EntityMappingProvider> entityMappingsProvider2 = ServiceLoader.load(EntityMappingProvider.class);
        entityMappingsProvider2.stream().forEach(loader ->
        {
            try {
                entityMappings2.addAll(loader.get().getEntityMappings());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        entityMappings = Collections.unmodifiableSet(entityMappings2);

    }





}
