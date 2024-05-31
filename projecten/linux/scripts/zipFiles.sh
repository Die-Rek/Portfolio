#! /bin/bash

set -o nounset
set -o errexit
set -o pipefail

d=$(date +%m-%d-%Y)

#de bestanden id de realtime stash samen zippen want die stapelen op
(cd /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/stash/realtime/; zip archive"${d}".zip -- *.txt)

#De log file verplaatsen naar de stash
mv logfile.txt /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/stash/logs/logfile"${d}".txt

echo "[$(date +"%Y-%m-%d(%T)")] Bestanden van afgelopen dag gestasht." >> /home/osboxes/linux-22-23-Sdierick20/Data_workflow/scripts/logfile.txt

#de tekstbestanden in de stash verwijderen die in de zipfile zitten

rm -f /home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/stash/realtime/*.txt