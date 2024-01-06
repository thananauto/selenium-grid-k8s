#!/bin/bash
if [ $# -eq 0 ];
then
  echo "$0: pass the container name as argument"
  exit 1
elif [ $# -gt 2 ];
then
  echo "$0: Pass only a single container name: $@"
  exit 1  
else
#many ways to find the docker container status
	until [ "`docker inspect -f {{.State.Running}} $1`" == "false" ]; do
		 echo "The container $1 is in UP status "
   		 sleep 3s;
	done;
   echo "The container $1 is in EXITED status "
fi
