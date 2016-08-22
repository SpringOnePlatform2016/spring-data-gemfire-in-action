#!/bin/bash
echo "";
echo "   _   _   _   _   _     _   _   _   _  ";
echo "  / \ / \ / \ / \ / \   / \ / \ / \ / \ ";
echo " ( G | e | o | d | e ) ( D | e | m | o )";
echo "  \_/ \_/ \_/ \_/ \_/   \_/ \_/ \_/ \_/ ";
echo "Built for Geode Incubating M2 for SpringPlatform 2016";
echo "";
echo "Running: cf create-service p-gemfire GFSP1 hero-cache";
cf create-service p-gemfire GFSP1 hero-cache;
echo "";
echo "Done";
