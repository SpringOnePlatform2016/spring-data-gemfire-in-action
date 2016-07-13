# Caching Sample

## What this sample demonstrates?

A basic sample of how to create a Spring Boot application that makes use of the JSR-107 approach to working with an in memory cache. The other thing of note is this sample assumes the cache in in a Cloud Environment (specifically PCF)

## When to use this approach?

Use this approach when:

1. Application needs to work with a slow,fragile and or expensive backing service (ie: LDAP, legacy DB or mainframe)

2. The data in this backing service is read more than its written

3. No time to learn the internals of a complex caching product

4. Want the flexibility to change products in the event the current product is EOL or becomes too expensive

## Prerequisites

1. [Setting Up Gemfire PCF Tile](https://github.com/jxblum/actionable-spring-gemfire/tree/master/cluster-create-example)

2. [Configure Gemfire in PCF](https://github.com/jxblum/actionable-spring-gemfire/tree/master/cluster-config-example)

## Deployment

Once logged into PCF with the PCF CLI a "cf push" from the root directory of this project will deploy the application

### Requirements for deployment

1. There needs to be a "customer-cache" Gemfire service in the space the app is being pushed too

2. This manifest uses the "java_buildpack_offline". If this buildpack is not in the PCF environment, run "cf buildpacks" to find the name of the Java buildpack

```shell

➜  caching-example git:(master) ✗ cf push
Using manifest file /Users/lshannon/Documents/actionable-spring-gemfire/caching-example/manifest.yml

Updating app caching-example in org example-apps / space development as admin...
OK

Using route caching-example.apps.diamond.pcf-gemfire.com
Uploading caching-example...
Uploading app files from: /Users/lshannon/Documents/actionable-spring-gemfire/caching-example/target/caching-example.jar
Uploading 572.5K, 102 files
Done uploading               
OK
Binding service customer-cache to app caching-example in org example-apps / space development as admin...
OK

Stopping app caching-example in org example-apps / space development as admin...
OK

Starting app caching-example in org example-apps / space development as admin...
Downloading java_buildpack_offline...
Downloaded java_buildpack_offline
Creating container
Successfully created container
Downloading app package...
Downloaded app package (30.9M)
Downloading build artifacts cache...
Downloaded build artifacts cache (109B)
Staging...
-----> Java Buildpack Version: v3.8.1 (offline) | https://github.com/cloudfoundry/java-buildpack.git#29c79f2
-----> Downloading Open Jdk JRE 1.8.0_91-unlimited-crypto from https://java-buildpack.cloudfoundry.org/openjdk/trusty/x86_64/openjdk-1.8.0_91-unlimited-crypto.tar.gz (found in cache)
       Expanding Open Jdk JRE to .java-buildpack/open_jdk_jre (1.0s)
-----> Downloading Open JDK Like Memory Calculator 2.0.2_RELEASE from https://java-buildpack.cloudfoundry.org/memory-calculator/trusty/x86_64/memory-calculator-2.0.2_RELEASE.tar.gz (found in cache)
       Memory Settings: -Xms681574K -XX:MetaspaceSize=104857K -Xss349K -Xmx681574K -XX:MaxMetaspaceSize=104857K
-----> Downloading Spring Auto Reconfiguration 1.10.0_RELEASE from https://java-buildpack.cloudfoundry.org/auto-reconfiguration/auto-reconfiguration-1.10.0_RELEASE.jar (found in cache)
Exit status 0
Staging complete
Uploading droplet, build artifacts cache...
Uploading droplet...
Uploading build artifacts cache...
Uploaded build artifacts cache (109B)
Uploaded droplet (76.4M)
Uploading complete

0 of 1 instances running, 1 starting
0 of 1 instances running, 1 starting
1 of 1 instances running

App started


OK

App caching-example was started using this command `CALCULATED_MEMORY=$($PWD/.java-buildpack/open_jdk_jre/bin/java-buildpack-memory-calculator-2.0.2_RELEASE -memorySizes=metaspace:64m..,stack:228k.. -memoryWeights=heap:65,metaspace:10,native:15,stack:10 -memoryInitials=heap:100%,metaspace:100% -stackThreads=300 -totMemory=$MEMORY_LIMIT) && JAVA_OPTS="-Djava.io.tmpdir=$TMPDIR -XX:OnOutOfMemoryError=$PWD/.java-buildpack/open_jdk_jre/bin/killjava.sh $CALCULATED_MEMORY" && SERVER_PORT=$PORT eval exec $PWD/.java-buildpack/open_jdk_jre/bin/java $JAVA_OPTS -cp $PWD/. org.springframework.boot.loader.JarLauncher`

Showing health and status for app caching-example in org example-apps / space development as admin...
OK

requested state: started
instances: 1/1
usage: 1G x 1 instances
urls: caching-example.apps.diamond.pcf-gemfire.com
last uploaded: Wed Jul 13 01:38:28 UTC 2016
stack: cflinuxfs2
buildpack: java_buildpack_offline

     state     since                    cpu    memory         disk           details   
#0   running   2016-07-12 06:39:29 PM   0.0%   455.2M of 1G   157.7M of 1G      
➜  caching-example git:(master) ✗ buildpack: java_buildpack_offline

```



## Important Related Projects

Spring Cloud Connectors allows us to connect to the Cache service managed by Pivotal Cloud Foundry:

http://cloud.spring.io/spring-cloud-connectors/

This is done in coordination with:

http://cloud.spring.io/spring-cloud-connectors/spring-cloud-cloud-foundry-connector.html


## References

https://docs.cloudfoundry.org/buildpacks/java/spring-service-bindings.html#cloud-profiles-java

http://cloud.spring.io/spring-cloud-connectors/spring-cloud-connectors.html

http://docs.pivotal.io/gemfire-cf/gfe-cli.html#install


