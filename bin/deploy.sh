#!/usr/bin/env bash

## Build the dictionary archives.
sudo wget https://github.com/sanskrit-coders/dict-tools/raw/master/bin/artifacts/dict-tools.jar -P ~/dict-tools/bin/artifacts/
make all
