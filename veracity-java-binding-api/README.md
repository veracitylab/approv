# Java Language Binding API

This component provides an API to access bindings for Java, and tests to assess the validity of the bindings against the schema.

Note that currently there is no build-in runtime API to validate binding definitions. The main reason is to avoid runtime dependencies. This
is particularly important as this component should be usable in agents, instrumenting arbitrary programs. Therefore, dependencies must be minimised to avoid versioning conflicts,
and preferably inlined. 

At the moment, bindings are hardcoded as resources. This could be changed later to a mechanism that allows plugins via the service loader mechanism. 

## Concepts

### Activity Tracking

Activity tracking describes which API calls to intercept to track acivities defined in the *approv* ontology. 

Example:

```
..
"activity-mappings": [
    {
      "activity": "https://veracity.wgtn.ac.nz/app-provenance#DBAccess",
      "group": "jdbc",
      "executions": [
        {
          "call": {
            "owner": "java.sql.DriverManager",
            "name": "getConnection"
          }
        },
        ..      
       ]
    } ..
```

This indicates that call sites to `java.sql.DriverManager:: getConnection` methods (note overloading, the signtaure can be specified as well) should be intercepted, and an activity of type `https://veracity.wgtn.ac.nz/app-provenance#DBAccess` should be reported. 

### Entity Tracking

Entity tracking records data abstractions associated with activities. For instance, consider the following scenario:

```
"entity-mappings" : [
{
   "activity": "https://veracity.wgtn.ac.nz/app-provenance#Database",
   "group": "jdbc",
   "call": {
     "owner": "java.sql.DriverManager",
     "name": "getConnection"
   },
  "source": "arg",
  "sourceIndex": 0,
  "target": "return",
  "create":true
}
```
 
The first parameter of `java.sql.DriverManager::getConnection` is a URL identifing a database. `"create":true` means that this parameter should be captured (`arg-0` means first parameter), and id be generated from this (the string itself, or some hash like `SHA-256`), and be tracked. This id is associated with the return value of the method (this is the target, other options here are another argument (with the `targetIndex` option to specify which one), or `this`).  

While `"create":true` means that a new entity identifier (and optionally name) can be created when this call takes place (in case of a database, an agent may decide to reuse an id assoctaed with the same database URL),  `"create":false` (which is the default) indicates that that an existing id should be associated with another object. 

For instance, consider:

```
{
   "call": {
     "owner": "java.sql.Connection",
     "name": "createStatement"
   },
  "source": "this",
  "target": "return"
}
```

This means that the existing id pointing to a particular database already associated with a connection (`this`) should also be associated with the statement returned by this method. 
  


## Usage

The easiest way to use this component is to install it locally, and then use it as a Maven (Gradle?) dependency.

1. build project with `mvn install`
2. In your client project, use it as a standard dependency referring to group, artefact id and version specified in the pom of this project
3. For agents, it is recommended to build a super / fat jar. This project is designed to avoid runtime dependencies to facilitate this, the only (non test scope) dependency used is *json.org*, and this is shaded.
4. The actual API is `nz.ac.wgtn.veracity.approv.jbind.Bindings::getActivities (String calleeOwner, String calleeName,String calleeDescriptor)` -- this can be queried when instrumentation encounters a call site, it returns a set of activities (as URIs as specified in the approv ontology) that match this call site. This is to be exteded to also compute entity references. 








