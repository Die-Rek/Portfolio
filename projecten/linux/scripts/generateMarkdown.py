#! /usr/bin/python

from markdowngenerator import MarkdownGenerator
from datetime import datetime
import sys

dateTime = datetime.now()
currentDate = datetime.date(dateTime)

#Trein met de hoogste vertraging
with open('/home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/output/hoogsteVertraging.csv','r') as file: 
	data = file.readlines() 
lastRow = data[-1]
lijst = lastRow.split(',')

if len(sys.argv) > 1:
    imageDatum = sys.argv[1]
    doelbestand = sys.argv[2]
    

def generateMarkDown(imageDatum= '', doelbestand= '/home/osboxes/linux-22-23-Sdierick20/Data_workflow/Documentation/paper.md'):
    if imageDatum == '':
        imageURL = "vertragingPlot.png"
    else:
        imageURL = f'/vertragingPlot{imageDatum}.png'
    with MarkdownGenerator(
        filename=doelbestand, enable_write=False
    ) as doc:
        doc.addHeader(1, "Data verzamelen en visualiseren over de NMBS")
        doc.writeTextLine(f'{doc.addBoldedText(str(dateTime.replace(second=0, microsecond=0)))}')
        doc.addHeader(2, 'Totale vertraging')
        doc.writeTextLine(f'![Totale momentele vertraging]({imageURL})')
        doc.writeTextLine('Deze data is gegenereerd door de momentele delay van alle treinen op te tellen en dan weer te geven op een grafiek.' +
            ' De vertraging wordt weergegeven per dag. De grafieken worden niet per dag opgeslagen maar deze zijn makkelijk te reproduceren door de datum aan te passen op lijn 9 en 10')
        doc.writeTextLine('```')
        doc.writeTextLine('dateTime = datetime.now()')
        doc.writeTextLine('currentDate = datetime.date(dateTime)')
        doc.writeTextLine('```')
        doc.addHeader(2, 'Trein met de meeste vertraging')
        doc.writeTextLine('De trein de momenteel de hoogste vertraging heeft opgelopen is de:')
        doc.writeTextLine('')
        doc.writeTextLine(doc.addBoldedAndItalicizedText(f'{lijst[0]} met {lijst[1]} minuten vertraging na het passeren van volgende halte: {lijst[2]}'))
        doc.writeTextLine('')
        doc.writeTextLine('Dit resultaat is bekomen door het doorlopen van de realtime data. We zoeken eerst delaatste halte waar de trein gepasseerd is. Daarna vergelijken we de vertraging met de vertraging van de vorige treinen. Als dit gebeurd is zoeken we de route op die de trein aflegt en vertalen we de halte van Frans naar Nederlands.')


if len(sys.argv) > 1:
    generateMarkDown(imageDatum, doelbestand)
else:
    generateMarkDown()
