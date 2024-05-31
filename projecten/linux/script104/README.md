# 5.4 Labo-oefeningen
## 5.4.1 Globbing
Voor de volgende oefeningen moet je globbing gebruiken, pipen naar grep is verboden!

Creëer in de directory `linux/` een aantal lege bestanden met de naam file, filea t/m filed, file1 t/m file9 en file10 t/m file19. Hier is een trucje om dat snel te doen:

```
[osboxes@osboxes ~/linux] $ touch file filea fileb filec filed
[osboxes@osboxes ~/linux] $ for i in {1..19}; do touch "file${i}"; done
[osboxes@osboxes ~/linux] $ ls
file   file11 file14 file17 file2 file5 file8 fileb
file1  file12 file15 file18 file3 file6 file9 filec
file10 file13 file16 file19 file4 file7 filea filed
```
Toon met ls telkens de gevraagde bestanden, niet meer en niet minder.

1. Alle bestanden die beginnen met file
2. Alle bestanden die beginnen met file, gevolgd door één letterteken (cijfer of letter)
3. Alle bestanden die beginnen met file, gevolgd door één letter, maar geen cijfer
4. Alle bestanden die beginnen met file, gevolgd door één cijfer, maar geen letter
5. De bestanden file12 t/m file16
6. Bestandern die beginnen met file, niet gevolgd door een 1

	1. ``` ls file*```
	2. ``` ls file?```
	3. ``` ls file[a-z]``` 
	4. ``` ls file[0-9]```
	5. ``` ls file[1][2-6]```
	6. ``` ls file[!1]```

## 5.4.2 AWK
Sommige van deze opgaven kan je op de command-line uitvoeren met een one-liner. Wanneer de opgave complexer wordt, kan je ook een script schrijven.

1. De pipeline hieronder is representatief voor vele codevoorbeelden die je online kan vinden. In dit geval worden uit /etc/passwd alle gebruikers afgedrukt die Bash als login-shell hebben. Maar eigenlijk zijn de pipes overbodig! Herschrijf de opdrachtregel zodat enkel awk gebruikt wordt en er geen I/O redirection meer plaatsvindt.

```cat /etc/passwd | grep bash | awk -F: '{print $1}'```

```awk -F: '/bash/ {print $1}' /etc/passwd```

2. Gebruik curl om de het opgegeven CSV-bestand te downloaden en lokaal op te slaan: https://raw.githubusercontent.com/HoGentTIN/dsai-en-labs/main/data/rlanders.csv. Het bestand bevat random gegenereerde data en heeft volgende kolommen:

```curl https://raw.githubusercontent.com/HoGentTIN/dsai-en-labs/main/data/rlanders.csv > data.csv```

3. Gebruik AWK (dus niet head of tail) om enkel de data in het csv-bestand af te drukken zonder de header (= eerste regel).

```awk '(NR>1)' data.csv```
