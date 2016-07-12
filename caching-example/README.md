# Caching Sample

## What this sample is?

A basic sample of how to create a Spring Boot application that makes use of the JSR-107 approach to working with an in memory cache. The other thing of note is this sample assumes the cache in in a Cloud Environment (specifically PCF)

## When to use this approach?

Use this approach when:

1. Application needs to work with a slow,fragile and or expensive backing service (ie: LDAP, legacy DB or mainframe)

2. The data in this backing service is read more than its written

3. No time to learn the internals of a complex caching product

4. Want the flexibility to change products in the event the current product is EOL or becomes too expensive

## Important Related Projects

Spring Cloud Connectors allows us to connect to the Cache service managed by Pivotal Cloud Foundry:

http://cloud.spring.io/spring-cloud-connectors/

This is done in coordination with:

http://cloud.spring.io/spring-cloud-connectors/spring-cloud-cloud-foundry-connector.html