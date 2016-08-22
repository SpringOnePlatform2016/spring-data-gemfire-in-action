#!/bin/bash
echo "";
echo "   _   _     _   _   _   _   _   _     _   _     _   _   _   _   _   _  ";
echo "  / \ / \   / \ / \ / \ / \ / \ / \   / \ / \   / \ / \ / \ / \ / \ / \ ";
echo " ( I | n ) ( M | e | m | o | r | y ) ( I | n ) ( A | c | t | i | o | n )";
echo "  \_/ \_/   \_/ \_/ \_/ \_/ \_/ \_/   \_/ \_/   \_/ \_/ \_/ \_/ \_/ \_/ ";
echo "SpringPlatform 2016";
echo "";
echo "Running: cf export-gemfire hero-cache -l logs.zip";
cf export-gemfire hero-cache -l logs.zip;
echo "";
echo "Done";
