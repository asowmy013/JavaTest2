#!/bin/bash

#Environement settings - Dont change
rm -f /etc/ssl/certs/java/cacerts
cp cacerts_new /etc/ssl/certs/java/cacerts

#Maven goal is editable based on user requirement. 
#User has to mention the path of the settings file if it's not in default path

echo "************************************"
echo "*** START THOR RUNNING...        ***"
echo "************************************"

cd /tcpBase/_API_NAME_
mkdir /tcpBase/_API_NAME_/report
retThor=0
java -Xms1875m -Xmx1875m -DSECURITY_PROPERTIES_DIRECTORY=/tcpBase/_API_NAME_/config -Ddatacenter=DCR -jar /tcpBase/_API_NAME_/it-thor-impl-jar-with-dependencies.jar --tags '~@ignore' --plugin json:/tcpBase/_API_NAME_/report/cucumberReport-_API_NAME_.json --plugin junit:/tcpBase/_API_NAME_/report/junitReport-_API_NAME_.xml /tcpBase/_API_NAME_/feature > /tcpBase/_API_NAME_/report/thor-output-_API_NAME_.log 2>&1
retThor=$?

echo "************************************"
echo "*** END   THOR RUNNING: exit = $retThor ***"
echo "************************************"
echo ""
echo "Showing /tcpBase folder : "
ls -lR /tcpBase

#Move report to dedicated folder
mv /tcpBase/_API_NAME_/report/* /tcpBase/download/.

#TCPplugin output configuration - Dont change
echo "end">/tcpBase/download/end1.log

exit $retThor
