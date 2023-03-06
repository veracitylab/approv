package nz.ac.wgtn.veracity.approv.jbind;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class DefaultActivityMappingProvider implements ActivityMappingProvider, DefaultMappingSources {

    @Override
    public Set<ActivityMapping> getActivityMappings() throws URISyntaxException {
        Set<ActivityMapping> activityMappings = new HashSet<>();
        for (String resource:BINDING_DEFS) {
            JSONTokener tokener = new JSONTokener(Bindings.class.getResourceAsStream(resource));
            JSONObject jRoot = new JSONObject(tokener);
            JSONArray jActivityMappings = jRoot.getJSONArray("activity-mappings");
            for (int i = 0; i < jActivityMappings.length(); i++) {
                JSONObject jActivityMapping = jActivityMappings.getJSONObject(i);
                System.out.println(jActivityMapping);
                ActivityMapping map = new ActivityMapping();
                map.setActivity(new URI(jActivityMapping.getString("activity")));
                map.setGroup(jActivityMapping.getString("group"));

                List<Execution> executions = new ArrayList<>();
                JSONArray jExecutions = jActivityMapping.getJSONArray("executions");
                for (int j = 0; j < jExecutions.length(); j++) {
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
                activityMappings.add(map);
            }
        }
        return activityMappings;

    }
}
