#! /bin/bash

set -o nounset
set -o errexit
set -o pipefail

d=$(date +%m-%d-%Y)

#het bestand waar het markdown bestand weergegeven moet worden
doelbestand=/home/osboxes/linux-22-23-Sdierick20/Data_workflow/documentatieSite/content/posts/paper"${d}".md

#De grafiek kopiëren naar het doelbestand waar het mardownbestand de image kan raadplegen
cp /home/osboxes/linux-22-23-Sdierick20/Data_workflow/Documentation/vertragingPlot.png /home/osboxes/linux-22-23-Sdierick20/Data_workflow/documentatieSite/static/vertragingPlot"${d}".png

#er wordt nu een markdown bestand aangemaakt met een datum zodat deze gepubliceerd en bijgehouden kan worden
/bin/python3 /home/osboxes/linux-22-23-Sdierick20/Data_workflow/scripts/generateMarkdown.py "${d}" "${doelbestand}"

#een header aan het markdown bestand toevoegen zodat we een titel krijgen op de hugo site.
echo -e "---\ntitle: "Data "${d}""\ndate: ${d}\ndraft: true\n---\n$(cat /home/osboxes/linux-22-23-Sdierick20/Data_workflow/documentatieSite/content/posts/paper"${d}".md)" > /home/osboxes/linux-22-23-Sdierick20/Data_workflow/documentatieSite/content/posts/paper"${d}".md