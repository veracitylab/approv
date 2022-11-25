# Java Language Binding API

This component provides an API to access bindings for Java, and tests to assess the validity of the bindings against the schema.

Note that currently there is no build-in runtime API to validate binding definitions. The main reason is to avoid runtime dependencies. This
is particularly important as this component should be usable in agents, instrumenting arbitrary programs. Therefore, dependencies must be minimised to avoid versioning conflicts,
and preferably inlined. 

At the moment, bindings are hardcoded as resources. This could be changed later to a mechanism that allows plugins via the service loader mechanism. 



