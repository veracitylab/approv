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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Execution execution = (Execution) o;
        return Objects.equals(owner, execution.owner) && Objects.equals(name, execution.name) && Objects.equals(descriptor, execution.descriptor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, name, descriptor);
    }
}
