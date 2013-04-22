
# Overview

Gpsd4Scala is a Scala/Akka client for connecting to the [gpsd](http://www.catb.org/gpsd/) daemon.

This scala project is to help developers in retrieving GPS data from
GPS devices connected to a computer (e.g. Raspberry PI). This is done through the intermediate
gpsd server. The scala client first connects to the gpsd service, then reads the GPS data
from it and presents that data to the developer as scala objects.

From [gpsd](http://www.catb.org/gpsd/) home page:
"gpsd is a service daemon that monitors one or more GPSes or AIS receivers
attached to a host computer through serial or USB ports, making all data on
the location/course/velocity of the sensors available to be queried on TCP
port 2947 of the host computer. With gpsd, multiple location-aware client
applications (such as navigational and wardriving software) can share access
to receivers without contention or loss of data. Also, gpsd responds to queries
with a format that is substantially easier to parse than the NMEA 0183 emitted
by most GPSes. The gpsd distribution includes a linkable C service library,
a C++ wrapper class, and a Python module that developers of gpsd-aware applications
can use to encapsulate all communication with gpsd. Third-party client bindings
for Java and Perl also exist."

gpsd data is provided as [JSON](http://www.json.org/) structures that are decoded by Gpsd4Scala
into equivalent scala objects.

# Status

Gpsd4Scala is still in development, not yet ready for use.


# How to setup

First download and install gpsd from http://www.catb.org/gpsd/.
Launch gpsd (for my tests, I typed: gpsd /dev/ttyUSB0 at the prompt).

With the gpsd server running in the background the Gpsd4Scala client library can
be used to connect and retrieve GPS data from the server on port 2947 (see the examples).

# Dependencies

Gpsd4Scala makes use of the JSON library spray-json from the [Spray](http://spray.io/) open source toolkit.
The source code and explanations for spray-json can be found [here](https://github.com/spray/spray-json).
For convenience, jar files including spray-json associated dependency on [parboiled](https://github.com/sirthias/parboiled/wiki)
are included here in the lib directory.
Gpsd4Scala also uses [Akka](http://akka.io/).

Currently (April 2013) Gpsd4Scala is based on connecting to gpsd-3.8, spray-json-2.10-1.2.3,
using scala 2.10.1, Akka 2.2-M2 and I'm using IntelliJ IDEA 12.

# How to use

Typically the following can be setup:

    // create a collector that will receive the decoded gps data
    val collector = system.actorOf(Props(new BasicCollector()))
    // create a client session to connect to gpsd
    val linker = system.actorOf(Props(new GPSdLinker("localhost", 2947)))
    // register the collector
    linker ! RegisterCollector(collector)
    Thread.sleep(1000)
    // start the client to connect to the gpsd server
    linker ! Start
    Thread.sleep(1000)

The collector is where the data arrives, this is where you do something with it.
Here is the typical structure of a collector actor showing the data arriving with the Collector(obj) message:

    class BasicCollector extends Actor with Collector {
      def receive = {
        case Collect(obj) => collect(obj)
      }
      def collect(obj: TypeObject) {
        obj match {
          case x: PollObject => println("PollObject: " + obj.toString())
          case x: VersionObject => println("VersionObject: " + obj.toString())
          case x: DeviceObject => println("DeviceObject: " + obj.toString())
          case x: WatchObject => println("WatchObject: " + obj.toString())
          case _ => println("not recognised object: " + obj.toString())
        }
      }
    }

There are a few example collectors (in the collector directory). The FileLogger simply
records all data to a text file.

The GoogleEarthCollector example shows the location in Google Earth as a placemark.
Note this collector depends on [scalakml](https://github.com/workingDog/scalakml) and
[scalaxal](https://github.com/workingDog/scalaxal) libraries.


