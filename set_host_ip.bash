#!/bin/bash

# get Ipv4 address
ip=$(hostname -I | awk '{print $1}')

#path to the file
path="./app/src/main/java/com/example/findlostitemapp/api/ApiConfig.kt"

# replace the old ip with the new one
sed -i "s/http:\/\/[0-9]*\.[0-9]*\.[0-9]*\.[0-9]*:8080/http:\/\/$ip:8080/g" $path
cat $path

echo "Ip address changed to $ip"
