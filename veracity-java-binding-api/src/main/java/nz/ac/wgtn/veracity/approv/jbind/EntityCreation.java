package nz.ac.wgtn.veracity.approv.jbind;

import java.net.URI;
import java.util.Objects;

/**
 * Object representing the creation of an entity at a call site.
 * An entity of type entity is created for the return type, receiver or parameter of a method call (ref),
 * if ref is EntityRef.ARG , the refIndex specified the index.
 * @author jens dietrich
 */
public class EntityCreation {
    private URI entity = null;
    private EntityRef sourceRef = null;
    private int sourceRefIndex = -1;
    private EntityRef targetRef = null;
    private int targetRefIndex = -1;

    public EntityCreation(URI entity, EntityRef sourceRef, int sourceRefIndex, EntityRef targetRef, int targetRefIndex) {
        this.entity = entity;
        this.sourceRef = sourceRef;
        this.sourceRefIndex = sourceRefIndex;
        this.targetRef = targetRef;
        this.targetRefIndex = targetRefIndex;
    }

    public URI getEntity() {
        return entity;
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
        EntityCreation that = (EntityCreation) o;
        return sourceRefIndex == that.sourceRefIndex && targetRefIndex == that.targetRefIndex && Objects.equals(entity, that.entity) && sourceRef == that.sourceRef && targetRef == that.targetRef;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity, sourceRef, sourceRefIndex, targetRef, targetRefIndex);
    }
}
