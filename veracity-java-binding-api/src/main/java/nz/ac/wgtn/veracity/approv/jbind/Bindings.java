package nz.ac.wgtn.veracity.approv.jbind;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.net.URI;
import java.util.*;
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


    /**
     * Baseline method, not efficient at the moment.
     * @param calleeOwner
     * @param calleeName
     * @param calleeDescriptor
     * @return
     */
    public static Set<URI> getActivities (String calleeOwner, String calleeName,String calleeDescriptor) {
        Predicate<Execution> executionFilter = exe ->
            Objects.equals(exe.getOwner(),calleeOwner) &&
            Objects.equals(exe.getName(),calleeName) &&
            Objects.equals(exe.getDescriptor(),calleeDescriptor);

        return getActivityMappings().stream()
            .filter(m -> m.getExecutions().stream().anyMatch(executionFilter))
            .map(m -> m.getActivity())
            .collect(Collectors.toSet());
    }
    // API methods end here


    private static void parseBindings() throws Exception {
        Set<ActivityMapping> activityMappings2 = new HashSet<>();
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
                    execution.setEntityRef(mapEntity(jExecution.getString("entityRef")));
                    if (jExecution.has("entityRefIndex")) {
                        execution.setEntityRefIndex(jExecution.getInt("entityRefIndex"));
                    }
                    executions.add(execution);
                }
                map.setExecutions(Collections.unmodifiableList(executions));
                activityMappings2.add(map);
            }
        }

        activityMappings = Collections.unmodifiableSet(activityMappings2);
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
