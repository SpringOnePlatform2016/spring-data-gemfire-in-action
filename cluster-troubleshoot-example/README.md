# Troubleshooting A PCF Gemfire cluster

Should issues occur in configuring or using the Gemfire Cluster, the following tips will be helpfully.

## Getting Artifacts From The members

The following will get log files from each of the members in the cluster. This will include:

1. Log files from each processes

2. Stats files from each process. These contain detailed metrics useful for troubleshooting performance issues

```shell

cf export-gemfire customer-cache -l ./logs/logs.zip

```

## Resetting The cluster

If this commands is ran the cluster will be restored to its initial state. This will remove everything, included disk persistence files.

```shell

cf restart-gemfire customer-cluster --reset-defaults

```
