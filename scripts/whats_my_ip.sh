#!/bin/bash

set -o errexit

case "`uname`" in
  Darwin* )
    echo "host.docker.internal"
    ;;
  * )
    isIfConfigPResent="$( ifconfig &> /dev/null || echo 'no' )"
    if [[ "${isIfConfigPResent}" == "no" ]]; then
      hostname -I | cut -d' ' -f1
    else
      ifconfig | grep -Eo 'inet (addr:)?([0-9]*\.){3}[0-9]*' | grep -Eo '([0-9]*\.){3}[0-9]*' | grep -v '127.0.0.1' | head -n 1
    fi
    ;;
esac
