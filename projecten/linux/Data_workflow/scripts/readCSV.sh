#! /bin/bash

set -o nounset
set -o errexit
set -o pipefail

INPUT=/home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/trips.csv
INPUTTOO=
OLDIFS=$IFS
IFS=','
[ ! -f $INPUT ] && { echo "$INPUT file not found"; exit 99; }
rm /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/id.csv
while read route_id service_id trip_id trip_headsign trip_short_name direction_id block_id shape_id trip_type
do
	echo "${trip_id},${route_id}" >> /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/id.csv
done < $INPUT 
IFS=$OLDIFS


