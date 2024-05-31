#! /bin/bash

set -o nounset
set -o errexit
set -o pipefail

datetime=$(date +"%Y-%m-%d(%T)")

#csv bestand voor de totale vertraging aanmaken als deze nog niet bestaat
if [ ! -f /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/output/vertraging.csv ]; then
    echo 'dateTime,minuten,datetime' > /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/output/vertraging.csv
fi

#csv bestand voor de trein met de hoogste vertraging aanmaken als deze nog niet bestaat
if [ ! -f /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/output/hoogsteVertraging.csv ]; then
    echo 'rit,minuten,halte,datetime' > /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/output/hoogsteVertraging.csv
fi

#Het vorige verslag verwijderen zodat we zeker zijn dat er een nieuw gegenereerd word
if [ -f /home/osboxes/linux-22-23-Sdierick20/Data_workflow/Documentation/paper.md ]; then
    rm /home/osboxes/linux-22-23-Sdierick20/Data_workflow/Documentation/paper.md
fi

#Gedocodeerd bestand verwijderen
if [ -f /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/realtime/decoded.txt ]; then
    rm /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/realtime/decoded.txt
fi

#Als de realtime folder niet leeg is verplaatsen we het encoded bestand naar de stash
if [ -n "$(ls -A /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/realtime)" ]; then
    mv -v /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/realtime/encoded* /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/stash/realtime/
fi

#De realtime data ophalen van de nmbs
wget -O /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/realtime/encoded"${datetime}".txt https://sncb-opendata.hafas.de/gtfs/realtime/c21ac6758dd25af84cca5b707f3cb3de |& tee -a /home/osboxes/linux-22-23-Sdierick20/Data_workflow/scripts/logfile.txt
echo "[$(date +"%Y-%m-%d(%T)")] realtime opgehaald." >> /home/osboxes/linux-22-23-Sdierick20/Data_workflow/scripts/logfile.txt

#Het bestand readonly maken
chmod 444 /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/realtime/encoded"${datetime}".txt
echo "[$(date +"%Y-%m-%d(%T)")] schrijfrechten afgenomen op encoded." >> /home/osboxes/linux-22-23-Sdierick20/Data_workflow/scripts/logfile.txt

#Het script aanroepen om de data te verwerken
/bin/python3 /home/osboxes/linux-22-23-Sdierick20/Data_workflow/scripts/decodeRealtime.py "${datetime}"
echo "[$(date +"%Y-%m-%d(%T)")] realtime data verwerkt." >> /home/osboxes/linux-22-23-Sdierick20/Data_workflow/scripts/logfile.txt

#HEt script aanroepen om de grafiek te genereren
/bin/python3 /home/osboxes/linux-22-23-Sdierick20/Data_workflow/scripts/showData.py
echo "[$(date +"%Y-%m-%d(%T)")] data geplot op grafiek." >> /home/osboxes/linux-22-23-Sdierick20/Data_workflow/scripts/logfile.txt

#Het script aanroepen dat het verslag genereerd
/bin/python3 /home/osboxes/linux-22-23-Sdierick20/Data_workflow/scripts/generateMarkdown.py
echo "[$(date +"%Y-%m-%d(%T)")] verslag aangemaakt." >> /home/osboxes/linux-22-23-Sdierick20/Data_workflow/scripts/logfile.txt

#Het verslag kopiëren naar de README zodat je op github het verslag te zien krijgt als je de Data workflow opent 
cp /home/osboxes/linux-22-23-Sdierick20/Data_workflow/Documentation/paper.md /home/osboxes/linux-22-23-Sdierick20/Data_workflow/README.md
cp /home/osboxes/linux-22-23-Sdierick20/Data_workflow/Documentation/vertragingPlot.png /home/osboxes/linux-22-23-Sdierick20/Data_workflow/vertragingPlot.png