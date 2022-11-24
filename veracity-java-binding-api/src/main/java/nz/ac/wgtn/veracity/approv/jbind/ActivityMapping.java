package nz.ac.wgtn.veracity.approv.jbind;

import java.net.URI;
import java.util.List;
import java.util.Objects;

/**
 * Mapping for a single activity.
 * @author jens dietrich
 */
public class ActivityMapping {

    private URI activity = null;
    private String group = null;
    private List<Execution> executions = null;

    public URI getActivity() {
        return activity;
    }

    public void setActivity(URI activity) {
        this.activity = activity;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<Execution> getExecutions() {
        return executions;
    }

    public void setExecutions(List<Execution> executions) {
        this.executions = executions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityMapping that = (ActivityMapping) o;
        return Objects.equals(activity, that.activity) && Objects.equals(group, that.group) && Objects.equals(executions, that.executions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activity, group, executions);
    }
}
