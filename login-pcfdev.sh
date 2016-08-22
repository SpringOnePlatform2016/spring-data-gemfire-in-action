#!/bin/bash
cf dev start
echo "Logging Into PCF Dev"
cf login -a api.local.pcfdev.io --skip-ssl-validation
echo "user/pass"
