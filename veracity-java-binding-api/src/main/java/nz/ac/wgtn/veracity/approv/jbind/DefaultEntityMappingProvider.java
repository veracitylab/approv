package nz.ac.wgtn.veracity.approv.jbind;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class DefaultEntityMappingProvider implements EntityMappingProvider , DefaultMappingSources {

    @Override
    public Set<EntityMapping> getEntityMappings() throws URISyntaxException {

        Set<EntityMapping> entityMappings = new HashSet<>();
        for (String resource:BINDING_DEFS) {
            JSONTokener tokener = new JSONTokener(Bindings.class.getResourceAsStream(resource));
            JSONObject jRoot = new JSONObject(tokener);
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

                entityMappings.add(map);
            }
        }
        return entityMappings;
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
