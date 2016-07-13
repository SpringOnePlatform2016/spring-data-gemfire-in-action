# Cluster Config Example

This outlines how to configure a Gemfire cluster running in PCF.

## Starting a GemFire Cluster on PCF

After installing the tile, Gemfire clusters can be created and configured

The following command will create a Gemfire cluster called customer-cache based on the specifications outlined in GemFireServicePlan1.

```shell

cf create-service p-gemfire GemFireServicePlan1 customer-cache

```

### Connecting To The Cluster With gfsh

Get the gfsh connection string

```shell

➜  cache-create-config-example git:(master) cf show-gfsh customer-cache          
Use the following command in gfsh to connect to service instance customer-cache
    connect --use-http --url=https://gf-plan-1-dashboard-ca9cbf62-4407-4e63-5754-ed545ddbea0a.system.diamond.pcf-gemfire.com/gemfire/v1
For a non HTTPS connection (not recommended -- use with caution!) use the following command:
    connect --use-http --url=http://gf-plan-1-dashboard-ca9cbf62-4407-4e63-5754-ed545ddbea0a.system.diamond.pcf-gemfire.com/gemfire/v1

```
Connect to the cluster using the connection string

```shell
➜  cache-create-config-example git:(master) ✗ gfsh
    _________________________     __
   / _____/ ______/ ______/ /____/ /
  / /  __/ /___  /_____  / _____  /
 / /__/ / ____/  _____/ / /    / /  
/______/_/      /______/_/    /_/    v8.2.1

Monitor and Manage GemFire
gfsh>connect --use-http --url=http://gf-plan-1-dashboard-ca9cbf62-4407-4e63-5754-ed545ddbea0a.system.diamond.pcf-gemfire.com/gemfire/v1
Successfully connected to: GemFire Manager HTTP service @ http://gf-plan-1-dashboard-ca9cbf62-4407-4e63-5754-ed545ddbea0a.system.diamond.pcf-gemfire.com/gemfire/v1

gfsh>list members
Member Count : 5
Coordinator  : locator1 (10.0.16.67(locator1:5664:locator)<ec><v0>:40070)

    Name     | Id
------------ | -----------------------------------------------
cacheserver0 | 10.0.16.68(cacheserver0:5694)<v3>:10560
locator0     | 10.0.16.66(locator0:5800:locator)<ec><v1>:13163
cacheserver1 | 10.0.16.69(cacheserver1:5604)<v4>:23067
locator1     | 10.0.16.67(locator1:5664:locator)<ec><v0>:40070
cacheserver2 | 10.0.16.70(cacheserver2:5545)<v2>:53596

gfsh>

```
