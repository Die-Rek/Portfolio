#! /bin/bash

set -o nounset
set -o errexit
set -o pipefail

wget -O /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/realtime/encoded.txt https://sncb-opendata.hafas.de/gtfs/realtime/c21ac6758dd25af84cca5b707f3cb3de 

python3 decodeRealtime.py