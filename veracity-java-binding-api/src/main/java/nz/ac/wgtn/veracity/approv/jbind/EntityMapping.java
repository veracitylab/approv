package nz.ac.wgtn.veracity.approv.jbind;

import java.net.URI;
import java.util.List;
import java.util.Objects;

/**
 * Mapping for a single entity.
 * @author jens dietrich
 */
public class EntityMapping {

    private URI entity = null;
    private String group = null;
    private Execution execution = null;
    private EntityRef source = null;
    private int sourceIndex = -1;
    private EntityRef target = null;
    private int targetIndex = -1;
    private boolean create = false;

    public URI getEntity() {
        return entity;
    }

    public void setEntity(URI activity) {
        this.entity = activity;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Execution getExecution() {
        return execution;
    }

    public void setExecution(Execution execution) {
        this.execution = execution;
    }

    public EntityRef getSource() {
        return source;
    }

    public void setSource(EntityRef source) {
        this.source = source;
    }

    public int getSourceIndex() {
        return sourceIndex;
    }

    public void setSourceIndex(int sourceIndex) {
        this.sourceIndex = sourceIndex;
    }

    public EntityRef getTarget() {
        return target;
    }

    public void setTarget(EntityRef target) {
        this.target = target;
    }

    public int getTargetIndex() {
        return targetIndex;
    }

    public void setTargetIndex(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    public boolean isCreate() {
        return create;
    }

    public void setCreate(boolean create) {
        this.create = create;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityMapping that = (EntityMapping) o;
        return sourceIndex == that.sourceIndex && targetIndex == that.targetIndex && create == that.create && Objects.equals(entity, that.entity) && Objects.equals(group, that.group) && Objects.equals(execution, that.execution) && source == that.source && target == that.target;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity, group, execution, source, sourceIndex, target, targetIndex, create);
    }
}
