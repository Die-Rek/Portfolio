#! /bin/bash

set -o nounset
set -o errexit
set -o pipefail

INPUT='/home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/trips*.csv'
INPUTTOO='/home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/routes*.csv'
INPUTTHREE='/home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/translations*.csv'
#De field separator opslaan zodat we deze later terug kunnen toekennen en , als field separator instellen
OLDIFS=$IFS
IFS=','

#uit het trips bestand route id en trip id halen zodat dit bestand kleiner wordt
truncate -s 0 /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/simplifyId.csv
while read -r route_id _ trip_id _ _ _ _ _ _
do
	echo "${route_id},${trip_id}" >> /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/simplifyId.csv
done < $INPUT

#uit het routes bestand de route_id, short_name en long_name van een route halen.
truncate -s 0 /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/simplifyRoutes.csv
while read -r route_id _ route_short_name route_long_name _ _ _ _ _
do
	echo "${route_id},${route_short_name},${route_long_name}" >> /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/simplifyRoutes.csv
done < $INPUTTOO

#uit het translations bestand halen we de vertaling naar het nederlands.
truncate -s 0 /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/simplifyLang.csv
while read -r trans_id lang translation
do
	if [ "${lang}" = "nl" ] || [ "${lang}" = "lang" ]; then
		echo "${trans_id},${translation}" >> /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/simplifyLang.csv
	fi
done < $INPUTTHREE

#De field separator terug gelijkstellen aan de originele waarde
IFS=$OLDIFS