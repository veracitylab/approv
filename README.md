## The Approv ("Application-Provenance") Ontology

This project contains an ontology that defines a schema to be used to represent provevance data gathered from programs (by means of instrumentation or static analysis), in some serialisation format like [RDF/JSON](https://www.w3.org/TR/rdf-json/).



The project contains an imported version of the prov-dm ontology. The import was done using the following command: 

`curl -sH "Accept: application/rdf+xml" -L http://www.w3.org/ns/prov`


### Language Bindings for Java 

`veracity-java-binding-api` contains an API that can be used by an agent instrumenting code to bind code executions to approv concepts. 