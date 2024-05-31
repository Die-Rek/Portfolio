#! /usr/bin/python


from google.transit import gtfs_realtime_pb2
import csv
import glob
import time
import sys
from datetime import datetime

feed = gtfs_realtime_pb2.FeedMessage()
newf = open("/home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/realtime/decoded.txt", "w")
oldf = open(glob.glob("/home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/realtime/encoded*.txt")[0], "rb")
feed.ParseFromString(oldf.read())
newf.write(str(feed))
newf.close()




# geeft de vertaling van het station terug
def vertaal(station) -> str:
  langcsv = open(glob.glob('/home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/simplifyLang*.csv')[0])
  langreader = csv.reader(langcsv)
  next(langreader, None)  #eerste lijn overslaan
  for row in langreader:
    if row[0] == station:
      return row[1]
  return station

#geeft een dictionary terug van de vorm: {stop_id: naam_Halte}
def geefHaltesDict() -> dict:
  with open(glob.glob('/home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/stops*.csv')[0]) as csvfile:
      haltes = {}
      datareader = csv.reader(csvfile)
      next(datareader, None)
      for row in datareader:
          if not row[0].startswith('S') and '_' not in row[0]:
              haltes.update({row[0] : row[2]})
  return haltes


#geeft een lijst van tuples terug van de vorm: (halte, vertraging)
def geefHaltesEnVertraging(trip, haltes) -> list:
  stops = []
  for update in trip.trip_update.stop_time_update:
    stop = update.stop_id
    stoptime = update.departure.time - update.arrival.time
    if stoptime != 0:
      if not update.HasField('departure'):
        delay = update.arrival.delay
      else:
        delay = update.departure.delay
      stops.append((vertaal(haltes[stop]), delay // 60))
  return stops

#geeft de naam van de route terug bvb. L Gand-Saint-Pierre -- Courtrai
def geefRouteNaam(trip) -> str:
  trip_id = trip.id
  idcsv = open(glob.glob('/home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/simplifyId*.csv')[0])
  idreader = csv.reader(idcsv)
  route_id = ''
  next(idreader, None)  #eerste lijn overslaan
  for row in idreader:
    if row[1] == trip_id:
      route_id = row[0]
      break

  routecsv = open(glob.glob('/home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/static/simplifyRoutes*.csv')[0])
  routereader = csv.reader(routecsv)
  route_naam = ''
  next(routereader, None) #eerste lijn overslaan
  for row in routereader:
    if row[0] == route_id:
      route_naam = f'{row[1]} {row[2]}'
      break
  return route_naam

def geefHoogsteMomenteleVertraging(entity) -> list:
  hoogsteVertraging = 0
  RitHoogsteVertraging = ''
  for trip in entity:
    update = geefHuidigStation(trip)
    vertraging = update.arrival.delay
    if vertraging > hoogsteVertraging:
      hoogsteVertraging = vertraging
      RitHoogsteVertraging = trip
      stopHoogsteVertraging = update
  haltes = geefHaltesDict()
  halte = haltes[stopHoogsteVertraging.stop_id]
  return [geefRouteNaam(RitHoogsteVertraging), hoogsteVertraging // 60, vertaal(halte)]



def geefHuidigStation(trip):
  epoch_time = int(time.time())
  huidig = ''
  previous = ''
  for stop_time_update in trip.trip_update.stop_time_update:
      current = stop_time_update
      if current.arrival.time <= epoch_time and current.departure.time > epoch_time:
        huidig = current
        break
      elif current.departure.time <= epoch_time and current.departure.time != current.arrival.time:
        previous = current
      elif current.arrival.time > epoch_time and current.departure.time != current.arrival.time:
        huidig = previous
        break
  if huidig == '':
    huidig = trip.trip_update.stop_time_update[0]
  return huidig

def isVertrokken(trip) -> bool:
    tijd = datetime.strptime(huidigeTijd, '%Y-%m-%d(%H:%M:%S)')
    epoch = round(tijd.timestamp())
    if epoch >= trip.trip_update.stop_time_update[0].departure.time:
        return True
    else:
        return False

def totaleVertraging(trip):
  teller = 0
  for rit in trip:
    if isVertrokken(rit) is True:
      teller += geefHuidigStation(rit).arrival.delay
  return teller // 60

huidigeTijd = sys.argv[1]



# for trip in feed.entity:
#   print(str(geefRouteNaam(trip)) + '  '  + str(geefHaltesEnVertraging(trip, geefHaltesDict(filename))))
print(totaleVertraging(feed.entity))
vertragingBestand = open('/home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/output/vertraging.csv', 'a')
vertragingBestand.write(f'{huidigeTijd},{totaleVertraging(feed.entity)}' + "\n")
vertragingBestand.close()

hoogsteVertragingBestand = open('/home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/output/hoogsteVertraging.csv', 'a')
hoogstevertraging = geefHoogsteMomenteleVertraging(feed.entity)
hoogsteVertragingBestand.write(f'{hoogstevertraging[0]},{hoogstevertraging[1]},{hoogstevertraging[2]},{huidigeTijd}' + "\n")
hoogsteVertragingBestand.close()