#!/bin/bash

PORT=$(grep '^PORT=' .env | cut -d '=' -f2 | tr -d '"')
IP_ADDR=$(ip addr show | grep 'inet ' | grep -v '127.0.0.1' | awk '{print $2}' | cut -d/ -f1)
IP_PORT="http://${IP_ADDR}:${PORT}"
echo $IP_PORT | qrencode -s 9 -l H -o "ipaddr-QR.png"
sxiv ipaddr-QR.png