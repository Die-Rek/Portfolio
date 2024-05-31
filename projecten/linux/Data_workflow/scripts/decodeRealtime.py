
from google.transit import gtfs_realtime_pb2

feed = gtfs_realtime_pb2.FeedMessage()
newf = open("/home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/realtime/decoded.txt", "w")
oldf = open("/home/osboxes/linux-22-23-Sdierick20/Data_workflow/data/realtime/encoded.txt", "rb")
feed.ParseFromString(oldf.read())
for entity in feed.entity:
  if entity.HasField('trip_update'):
    newf.write(str(entity.trip_update))
newf.close()
