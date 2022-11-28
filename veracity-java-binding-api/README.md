# Java Language Binding API

This component provides an API to access bindings for Java, and tests to assess the validity of the bindings against the schema.

Note that currently there is no build-in runtime API to validate binding definitions. The main reason is to avoid runtime dependencies. This
is particularly important as this component should be usable in agents, instrumenting arbitrary programs. Therefore, dependencies must be minimised to avoid versioning conflicts,
and preferably inlined. 

At the moment, bindings are hardcoded as resources. This could be changed later to a mechanism that allows plugins via the service loader mechanism. 


## Usage

The easiest way to use this component is to install it locally, and then use it as a Maven (Gradle?) dependency.

1. build project with `mvn install`
2. In your client project, use it as a standard dependency referring to group, artefact id and version specified in the pom of this project
3. For agents, it is recommended to build a super / fat jar. This project is designed to avoid runtime dependencies to facilitate this, the only (non test scope) dependency used is *json.org*, and this is shaded.
4. The actual API is `nz.ac.wgtn.veracity.approv.jbind.Bindings::getActivities (String calleeOwner, String calleeName,String calleeDescriptor)` -- this can be queried when instrumentation encounters a call site, it returns a set of activities (as URIs as specified in the approv ontology) that match this call site. This is to be exteded to also compute entity references. 



