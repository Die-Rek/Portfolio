import os
from google.transit import gtfs_realtime_pb2

os.system('./scripts/getStatic.sh')
feed = gtfs_realtime_pb2.FeedMessage()

response = open('data/realtime/encoded.txt', 'rb')
print(response)
feed.ParseFromString(response.read())

for entity in feed.entity:
  if entity.HasField('trip_update'):
    print(entity.trip_update)