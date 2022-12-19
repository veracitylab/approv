package nz.ac.wgtn.veracity.approv.jbind;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Collections;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Main API entry point.
 * Has some methods that facilitate efficient querying, to be used in instrumentation.
 * @author jens dietrich
 */
public class Bindings {

    /**
     * For now, resources providing bindings are hardcoded.
     * This could be replaced by a serviceloader-based plugin mechanism.
     */
    public static final String[] BINDING_DEFS = {
        "/bind-jdbc.json"
    };

    private static Set<ActivityMapping> activityMappings = null;
    private static Set<EntityMapping> entityMappings = null;
    static {
        try {
            parseBindings();
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
    public static Set<URI> getActivities (String calleeOwner, String calleeName,String calleeDescriptor) {
        final Predicate<? super Execution> executionFilter = exe ->
            Objects.equals(exe.getOwner(),calleeOwner) &&
            Objects.equals(exe.getName(),calleeName) &&
            Objects.equals(exe.getDescriptor(),calleeDescriptor);

        Set<URI> collect = getActivityMappings().stream()
            .filter(m -> m.getExecutions().stream().anyMatch(executionFilter))
            .map(m -> m.getActivity())
            .collect(Collectors.toSet());
        return collect;
    }
    // API methods end here


    private static void parseBindings() throws Exception {
        Set<ActivityMapping> activityMappings2 = new HashSet<>();
        Set<EntityMapping> entityMappings2 = new HashSet<>();
        for (String resource:BINDING_DEFS) {
            JSONTokener tokener = new JSONTokener(Bindings.class.getResourceAsStream(resource));
            JSONObject jRoot = new JSONObject(tokener);
            JSONArray jActivityMappings = jRoot.getJSONArray("activity-mappings");
            for (int i=0;i<jActivityMappings.length();i++) {
                JSONObject jActivityMapping = jActivityMappings.getJSONObject(i);
                System.out.println(jActivityMapping);
                ActivityMapping map = new ActivityMapping();
                map.setActivity(new URI(jActivityMapping.getString("activity")));
                map.setGroup(jActivityMapping.getString("group"));

                List<Execution> executions = new ArrayList<>();
                JSONArray jExecutions = jActivityMapping.getJSONArray("executions");
                for (int j=0;j<jExecutions.length();j++) {
                    JSONObject jExecution = jExecutions.getJSONObject(j);
                    Execution execution = new Execution();
                    JSONObject jCall = jExecution.getJSONObject("call");
                    execution.setOwner(jCall.getString("owner"));
                    execution.setName(jCall.getString("name"));
                    if (jCall.has("signature")) {
                        execution.setDescriptor(jCall.getString("signature"));
                    }
                    executions.add(execution);
                }
                map.setExecutions(Collections.unmodifiableList(executions));
                activityMappings2.add(map);
            }

            JSONArray jEntityMappings = jRoot.getJSONArray("entity-mappings");
            for (int i=0;i<jEntityMappings.length();i++) {
                JSONObject jEntityMapping = jEntityMappings.getJSONObject(i);
                System.out.println(jEntityMapping);
                EntityMapping map = new EntityMapping();
                if (jEntityMapping.has("entity")) {
                    map.setEntity(new URI(jEntityMapping.getString("entity")));
                }
                if (jEntityMapping.has("group")) {
                    map.setGroup(jEntityMapping.getString("group"));
                }

                JSONObject jCall = jEntityMapping.getJSONObject("call");
                Execution execution = new Execution();
                execution.setOwner(jCall.getString("owner"));
                execution.setName(jCall.getString("name"));
                if (jCall.has("signature")) {
                    execution.setDescriptor(jCall.getString("signature"));
                }
                map.setExecution(execution);

                if (jEntityMapping.has("source")) {
                    map.setSource(mapEntity(jEntityMapping.getString("source")));
                }
                if (jEntityMapping.has("target")) {
                    map.setTarget(mapEntity(jEntityMapping.getString("target")));
                }
                if (jEntityMapping.has("sourceIndex")) {
                    map.setSourceIndex(jEntityMapping.getInt("sourceIndex"));
                }
                if (jEntityMapping.has("targetIndex")) {
                    map.setTargetIndex(jEntityMapping.getInt("targetIndex"));
                }
                if (jEntityMapping.has("create")) {
                    map.setCreate(jEntityMapping.getBoolean("create"));
                }

                entityMappings2.add(map);
            }
        }
        activityMappings = Collections.unmodifiableSet(activityMappings2);
        entityMappings = Collections.unmodifiableSet(entityMappings2);

    }

    private static EntityRef mapEntity(String entity) {
        if (entity==null) {
            throw new IllegalArgumentException("entity cannot be null");
        }
        else if (entity.equals("this")) {
            return EntityRef.THIS;
        }
        else if (entity.equals("arg")) {
            return EntityRef.ARG;
        }
        else if (entity.equals("return")) {
            return EntityRef.RETURN;
        }
        else {
            throw new IllegalArgumentException("entity cannot be \"" + entity + "\"");
        }
    }



}
