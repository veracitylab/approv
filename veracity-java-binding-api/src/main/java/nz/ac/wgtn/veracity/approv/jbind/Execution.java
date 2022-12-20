package nz.ac.wgtn.veracity.approv.jbind;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Describes an execution, i.e. a method invocation.
 * @author jens dietrich
 */
public class Execution {
    private String owner = null;
    private String name = null;
    private Pattern namePattern = null;
    private String descriptor = null;
    private Pattern descriptorPattern = null;

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
        this.namePattern = isPattern(name) ? RegExUtil.glob2regex(name) : null;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
        this.descriptorPattern = isPattern(descriptor) ? RegExUtil.glob2regex(descriptor) : null;
    }

    private boolean isPattern(String s) {
        return s.contains("?") || s.contains("*");
    }

    public boolean matches (String owner,String name,String descriptor) {

        if (!Objects.equals(this.owner, owner)) {
            return false;
        }

        if (this.namePattern==null) {
            // name not set means it matches anything
            if (this.name!=null &&  !Objects.equals(this.name, name)) {
                return false;
            }
        }
        else {
            if (!namePattern.matcher(name).matches()) {
                return false;
            }
        }

        if (this.descriptorPattern==null) {
            // descriptor not set means it matches anything
            if (this.descriptor!=null && !Objects.equals(this.descriptor, descriptor)) {
                return false;
            }
        }
        else {
            if (!descriptorPattern.matcher(name).matches()) {
                return false;
            }
        }

        return true;
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
