#! /bin/bash

set -o nounset
set -o errexit
set -o pipefail

datetime=$(date +"%Y-%m-%d(%T)")

#Het zip bestand verplaatsen naar de stash als er een file in de folder data/static zit
if [ -n "$(ls -A /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static)" ]; then
    mv -v /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/*.zip /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/stash/static/
fi

#Alle csv bestanden van de vorige .zip file verwijderen
if [ -n "$(ls -A /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static)" ]; then
    rm /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/*.csv
fi

#De static gegevens van de NMBS ophalen als .zip file
wget -O /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/staticEncoded"${datetime}".zip https://sncb-opendata.hafas.de/gtfs/static/c21ac6758dd25af84cca5b707f3cb3de | tee -a /home/osboxes/linux-22-23-Sdierick20/Data_workflow/scripts/logfile.txt

#De .zip file readonly maken
chmod 444 /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/staticEncoded"${datetime}".zip

#Unzippen
unzip -o /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/staticEncoded"${datetime}".zip -d /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static | tee -a /home/osboxes/linux-22-23-Sdierick20/Data_workflow/scripts/logfile.txt


#De bestanden uit de zipfile omzetten naar csv bestanden (origineel .txt bestanden)
for filename in /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/*.txt; do
    naam="${filename%.txt*}"
    echo "${naam}"
    mv "${filename}" "${naam}${datetime}".csv
done

#Script aanroepen die sommige bestanden versimpelt zodat het systeem met minder data overweg moet tijdens het verwerken
/bin/bash /home/osboxes/linux-22-23-Sdierick20/Data_workflow/scripts/simplifyCSV.sh "${datetime}"
echo "[$(date +"%Y-%m-%d(%T)")] 3 bestanden met simpelere data opgesteld." >> /home/osboxes/linux-22-23-Sdierick20/Data_workflow/scripts/logfile.txt

