#!/bin/bash

BU=_bu
HIVESITE=/etc/hive/conf/hive-site.xml
HIVESITEBU=$HIVESITE$BU
HIVESERVER2=/etc/default/hive-server2
HIVESERVER2BU=$HIVESERVER2$BU

if [[ ! -e $HIVESITEBU ]]
	then
		echo "Making backup of $HIVESITE to $HIVESITEBU"
		sudo cp $HIVESITE $HIVESITEBU
fi

if [[ ! -e $HIVESERVER2BU ]]
	then
		echo "Making backup of $HIVESERVER2 to $HIVESERVER2BU"
		sudo cp $HIVESERVER2 $HIVESERVER2BU
fi

sudo cp $HIVESITEBU $HIVESITE
sudo sed -i 's/<\/configuration>/<property><name>hive.aux.jars.path<\/name><value>hdfs:\/\/\/loudacre\/udfs\/customudf-1.0-SNAPSHOT.jar<\/value><\/property><\/configuration>/' $HIVESITE

sudo cp $HIVESERVER2BU $HIVESERVER2
echo "export AUX_CLASSPATH=/home/training/training_materials/bigdata/exercises/hive-udf/target/customudf-1.0-SNAPSHOT.jar" | sudo tee -a $HIVESERVER2

sudo service hive-server2 restart
