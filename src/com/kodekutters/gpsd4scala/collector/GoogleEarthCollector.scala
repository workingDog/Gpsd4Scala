package com.kodekutters.collector

import akka.actor.{ActorLogging, Actor}
import scala.xml.PrettyPrinter
import com.scalakml.io.KmlPrintWriter
import com.scalakml.kml._
import com.kodekutters.messages.Collect
import com.kodekutters.gpsd4scala.collector.Collector
import com.kodekutters.gpsd4scala.types.{TPVObject, TypeObject}

/**
 * Author: Ringo Wathelet
 * Date: 30/03/13
 * Version: 1
 */

/**
 * collect TPV location messages, create a NetworkLink file and a kml file
 * with a placemark at the GPS location.
 *
 * Launch Google Earth by double clicking on the NetworkLink file.
 * The locations in the kml file are poled by the NetworkLink every 2 seconds in this example.
 *
 */
object GoogleEarthCollector {

  def apply(fileName: String): GoogleEarthCollector = new GoogleEarthCollector(fileName)

}

class GoogleEarthCollector(val testFile: String) extends Actor with Collector with ActorLogging {

  // the network link file name
  val netLinkFile = "NetworkLink_" + testFile

  // create the network link file
  val kml = new Kml(new NetworkLink("TestLink", new Link(testFile, OnInterval, 2)))
  // print the network link file
  new KmlPrintWriter(netLinkFile).write(Option(kml), new PrettyPrinter(80, 3))

  def receive = {
    case Collect(info) => collect(info)
  }

  def collect(info: TypeObject) {
    info match {
      case tpv: TPVObject =>
        // must have at least some values for the lat lon
        if (tpv.lon.isDefined && tpv.lat.isDefined) {
          val alt = if(tpv.alt.isDefined) tpv.alt.get else 0.0
          val kml = new Kml(new Placemark("test", new Point(RelativeToGround, tpv.lon.get, tpv.lat.get, alt)))
          // write the placemark to the kml file
          new KmlPrintWriter(testFile).write(Option(kml), new PrettyPrinter(80, 3))
        }
    }
  }

}

