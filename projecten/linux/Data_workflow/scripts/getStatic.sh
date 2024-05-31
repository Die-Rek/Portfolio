#! /bin/bash

set -o nounset
set -o errexit
set -o pipefail

wget -O /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/staticEncoded.zip https://sncb-opendata.hafas.de/gtfs/static/c21ac6758dd25af84cca5b707f3cb3de
unzip -o /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/staticEncoded.zip -d /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static
rm /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/staticEncoded.zip


for filename in /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/*.txt; do
    naam="${filename%.txt*}"
    echo "${naam}"
    mv "${filename}" "${naam}".csv
done
rm /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/*.txt
