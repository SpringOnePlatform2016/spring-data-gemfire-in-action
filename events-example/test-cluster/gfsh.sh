#!/bin/bash
echo "";
echo "   _   _   _   _   _     _   _   _   _  ";
echo "  / \ / \ / \ / \ / \   / \ / \ / \ / \ ";
echo " ( G | e | o | d | e ) ( D | e | m | o )";
echo "  \_/ \_/ \_/ \_/ \_/   \_/ \_/ \_/ \_/ ";
echo "Built for Geode Incubating M2 for SpringPlatform 2016";
echo "";

#Check for Java
if [ -n "$JAVA_HOME" ]; then
		echo "Using JAVA_HOME: $JAVA_HOME"
else
	export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)
fi

#Check for the Geode Binaries
if [ ! -d "apache-geode-1.0.0-incubating.M2" ]; then
	echo "Geode Binaries not found"
	echo "Downloading and unpacking: http://apache.org/dyn/closer.cgi/incubator/geode/1.0.0-incubating.M2/apache-geode-1.0.0-incubating.M2.tar.gz"
	wget http://mirrors.ibiblio.org/apache/incubator/geode/1.0.0-incubating.M2/apache-geode-1.0.0-incubating.M2.tar.gz
	tar -xvzf apache-geode-1.0.0-incubating.M2.tar.gz
fi

#Set the path
export WORKING_DIRECTORY=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
export GEODE=$WORKING_DIRECTORY/apache-geode-1.0.0-incubating.M2
export PATH=$PATH:$JAVA_HOME/bin:$GEODE/bin

echo "Gemfire Shell (gfsh) is command-line interface to launch, manage and monitor Geode/Gemfire processes"
echo "cluster.gfsh to create the test cluster data.gfsh to put data"
$GEODE/bin/gfsh
