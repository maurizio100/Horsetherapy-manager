#!/bin/bash

if [ -n "$1" ]; then
	echo "instantiate database server"
	java -cp hsqldb/lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:$1 --dbname.0 $1
else
	echo "argument non existent"
fi
