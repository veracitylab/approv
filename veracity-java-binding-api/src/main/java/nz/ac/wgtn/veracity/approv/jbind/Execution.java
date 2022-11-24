package nz.ac.wgtn.veracity.approv.jbind;

import java.util.Objects;

/**
 * Describes an execution, i.e. a method invocation.
 * @author jens dietrich
 */
public class Execution {
    private String owner = null;
    private String name = null;
    private String descriptor = null;
    private EntityRef entityRef = null;
    private int entityRefIndex = -1; // only used if entityRef is ARG, starting at 0, should be -1 otherwise

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public EntityRef getEntityRef() {
        return entityRef;
    }

    public void setEntityRef(EntityRef entityRef) {
        this.entityRef = entityRef;
    }

    public int getEntityRefIndex() {
        return entityRefIndex;
    }

    public void setEntityRefIndex(int entityRefIndex) {
        this.entityRefIndex = entityRefIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Execution execution = (Execution) o;
        return entityRefIndex == execution.entityRefIndex && Objects.equals(owner, execution.owner) && Objects.equals(name, execution.name) && Objects.equals(descriptor, execution.descriptor) && entityRef == execution.entityRef;
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, name, descriptor, entityRef, entityRefIndex);
    }
}
