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
    private EntityRef ref = null;
    private int refIndex = -1;

    public EntityCreation(URI entity, EntityRef ref, int refIndex) {
        this.entity = entity;
        this.ref = ref;
        this.refIndex = refIndex;
    }

    public URI getEntity() {
        return entity;
    }

    public EntityRef getRef() {
        return ref;
    }

    public int getRefIndex() {
        return refIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityCreation that = (EntityCreation) o;
        return refIndex == that.refIndex && Objects.equals(entity, that.entity) && ref == that.ref;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity, ref, refIndex);
    }
}
