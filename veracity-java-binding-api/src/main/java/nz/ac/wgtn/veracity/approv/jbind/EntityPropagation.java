package nz.ac.wgtn.veracity.approv.jbind;

import java.util.Objects;

/**
 * Object representing the transfer of an entity at a call site.
 * An entity is refered from one object reference to another, for instance, from this or the first argument
 * to the object returned by the call.
 * @author jens dietrich
 */
public class EntityPropagation {

    private EntityRef sourceRef = null;
    private int sourceRefIndex = -1;

    private EntityRef targetRef = null;
    private int targetRefIndex = -1;

    public EntityPropagation(EntityRef sourceRef, int sourceRefIndex, EntityRef targetRef, int targetRefIndex) {
        this.sourceRef = sourceRef;
        this.sourceRefIndex = sourceRefIndex;
        this.targetRef = targetRef;
        this.targetRefIndex = targetRefIndex;
    }

    public EntityRef getSourceRef() {
        return sourceRef;
    }

    public int getSourceRefIndex() {
        return sourceRefIndex;
    }

    public EntityRef getTargetRef() {
        return targetRef;
    }

    public int getTargetRefIndex() {
        return targetRefIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityPropagation that = (EntityPropagation) o;
        return sourceRefIndex == that.sourceRefIndex && targetRefIndex == that.targetRefIndex && sourceRef == that.sourceRef && targetRef == that.targetRef;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceRef, sourceRefIndex, targetRef, targetRefIndex);
    }
}
