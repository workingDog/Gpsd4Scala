# Gpsd4Scala Overview

Gpsd4Scala is a Scala and Akka client for connecting to the [gpsd](http://www.catb.org/gpsd/) daemon.

This scala client library is to help developers in retrieving GPS data from
GPS devices connected to a computer (e.g. Raspberry PI). This is done through the intermediate
[gpsd server](http://www.catb.org/gpsd/). The scala client first connects to the gpsd service, then reads the GPS data
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

gpsd provides data as [JSON](http://www.json.org/) objects that are decoded by Gpsd4Scala
into equivalent scala objects. See also [gpsd_json](http://catb.org/gpsd/gpsd_json.html) for
the meaning of the JSON objects returned by gpsd.

# How to setup

First download and install gpsd from http://www.catb.org/gpsd/. Launch gpsd according to the instructions.

With the gpsd server running in the background the Gpsd4Scala client library can
be used to connect and retrieve GPS data from the server on port 2947 (see the example).

# How to use

Gpsd4Scala is a library, so it is up to you to create your own application.
Here is an example:

    object Example {
      def main(args: Array[String]) {
       implicit val context = ActorSystem("Example")
       // create a collector that will receive the decoded gps data
       val collector = context.actorOf(Props(classOf[BasicCollector]))
       // create the client session actor
       val linker = context.actorOf(GPSdLinker.props("localhost", 2947), "linker")
       // register the collector
       linker ! Register(collector)
       // start the client to connect to the gpsd server
       linker ! Start
       // give it time to connect
       Thread.sleep(2000)
       // example of sending a command to the gpsd server
       linker ! Watch
      }
    }

Other commands can also be sent to the gpsd daemon such as:

  - linker ! Version
  - linker ! Poll
  - linker ! Device
  - linker ! Device(deviceObj)
  - linker ! Watch(watchObj)

Note: to give the linker time to setup the connection and process the commands, it is
advisable to wait for a second or two.

In your application, simply include the gpsd4scala jar file from the [target directory](https://github.com/workingDog/Gpsd4Scala/tree/master/target/scala-2.10).

# How to collect the data

The collector is where the data arrives, this is where you do something with the data received from the gpsd.
In your application all you have to do is to create your own collector and register it with the linker,
as shown in the example above, the rest is done for you.

Here is the typical structure of a collector actor showing the data arriving with the Collect(obj) message:

    class BasicCollector extends Actor with Collector {

      def receive = { case Collect(obj) => collect(obj) }

      def collect(obj: TypeObject) {
        obj match {
          case x: TPVObject => println("TPVObject: " + obj.toString())
          case x: VersionObject => println("VersionObject: " + obj.toString())
          case x: DeviceObject => println("DeviceObject: " + obj.toString())
          case _ => println("other object: " + obj.toString())
        }
      }
    }

where "TypeObject" such as TPVObject, DeviceObject etc... are the scala objects representing
the core gpsd socket protocol as described [here](http://catb.org/gpsd/gpsd_json.html).

Other example collectors can be found in the collector directory such as:

  - The FileLogger records data to a text file.

  - The GoogleEarthCollector example shows the GPS location in Google Earth as a placemark.
Note this collector depends on [scalakml](https://github.com/workingDog/scalakml) and
[scalaxal](https://github.com/workingDog/scalaxal) libraries. These two libraries are included
here in the lib directory.

# Dependencies

Gpsd4Scala uses the Play json library part of the [Play framework](http://www.playframework.com/download)
and [Akka](http://akka.io/).

The code can be compiled/packaged/run using: sbt compile, sbt package and sbt run.

# Status

Gpsd4Scala has not been fully tested yet, as I do not have a GPS device.
