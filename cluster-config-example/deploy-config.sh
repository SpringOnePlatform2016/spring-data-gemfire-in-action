#!/bin/bash
echo "";
echo "   _   _     _   _   _   _   _   _     _   _     _   _   _   _   _   _  ";
echo "  / \ / \   / \ / \ / \ / \ / \ / \   / \ / \   / \ / \ / \ / \ / \ / \ ";
echo " ( I | n ) ( M | e | m | o | r | y ) ( I | n ) ( A | c | t | i | o | n )";
echo "  \_/ \_/   \_/ \_/ \_/ \_/ \_/ \_/   \_/ \_/   \_/ \_/ \_/ \_/ \_/ \_/ ";
echo "SpringPlatform 2016";
echo "";
export zipname="hero-cluster.zip";

if cf services | grep -q "hero-cache"; then
   	echo "The hero-cache exists.";
   	echo "If it did not:"
   	echo "Run: cf cs p-gemfire GemFireServicePlan1 hero-cache";
else
	echo "The hero-cache does not exist";
	echo "Running: cf cs p-gemfire GemFireServicePlan1 hero-cache";
	cf cs p-gemfire GemFireServicePlan1 hero-cache;
	echo "";
fi

if [ -f "$zipname" ]
then
	echo "Removing the old $zipname";
	echo "";
	rm -fr $zipname
fi
echo "Copying the Jar into the cluster folder"
echo "";
cp target/hero-cache.jar cluster/lib;
echo "Creating the new zip";
echo "";
zip -r $zipname cluster;
echo "Running: cf restart-gemfire hero-cache --cluster-config ./$zipname --spring-xml /hero-cache.xml";
cf restart-gemfire hero-cache --cluster-config ./$zipname --spring-xml /hero-cache.xml
echo "Done";
