#! /usr/bin/python

import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from markdowngenerator import MarkdownGenerator
from datetime import datetime

dateTime = datetime.now()
currentDate = datetime.date(dateTime)
# Data inlezen en dubbels verwijderen
vertraging = pd.read_csv('/home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/output/vertraging.csv')
# Timestamp correct interpreteren als een tijd-object
vertraging = vertraging[vertraging["dateTime"].str.contains(str(currentDate))]
vertraging.dateTime = pd.to_datetime(vertraging.dateTime, format='%Y-%m-%d(%H:%M:%S)')
# Timestamp als index voor de tabel gebruiken 
vertraging = vertraging.set_index('dateTime')



# Plots opslaan van de gemeten waarden
sns.lineplot(data=vertraging, x='dateTime', y='minuten', marker='o')
plt.xticks(rotation=45, ha='right', rotation_mode='anchor')
plt.subplots_adjust(bottom=0.2)
plt.savefig('/home/osboxes/linux-22-23-Sdierick20/Data_workflow/Documentation/vertragingPlot.png')
plt.clf()