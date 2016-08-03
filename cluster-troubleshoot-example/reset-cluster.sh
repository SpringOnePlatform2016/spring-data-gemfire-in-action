#!/bin/bash
echo "";
echo "   _   _   _   _   _     _   _   _   _  ";
echo "  / \ / \ / \ / \ / \   / \ / \ / \ / \ ";
echo " ( G | e | o | d | e ) ( D | e | m | o )";
echo "  \_/ \_/ \_/ \_/ \_/   \_/ \_/ \_/ \_/ ";
echo "Built for Geode Incubating M2 for SpringPlatform 2016";
echo "";
echo "Running: cf restart-gemfire hero-cache --reset-defaults --clear-logs -t '10000'"
cf restart-gemfire hero-cache --reset-defaults --clear-logs -t "10000" 
