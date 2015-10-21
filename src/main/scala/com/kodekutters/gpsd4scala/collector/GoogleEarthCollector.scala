package com.kodekutters.collector

import akka.actor.{ActorLogging, Actor}
import scala.xml.PrettyPrinter
import com.scalakml.io.KmlPrintWriter
import com.scalakml.kml._
import com.kodekutters.gpsd4scala.collector.Collector
import com.kodekutters.gpsd4scala.protocol.{TPV, Report}
import com.kodekutters.gpsd4scala.messages.Collect

/**
 * Author: Ringo Wathelet
 * Date: 30/03/13
 * Version: 1
 */

/**
 * another example collector.
 *
 * collect TPV location messages, create a NetworkLink file and a kml file
 * with a placemark at the GPS location.
 *
 * Launch Google Earth by double clicking on the created NetworkLink file.
 * The location in the kml file is poled by the NetworkLink every 2 seconds in this example.
 *
 */
object GoogleEarthCollector {

  def apply(fileName: String): GoogleEarthCollector = new GoogleEarthCollector(fileName)

}

/**
 * An example collector showing the gps location in Google Earth
 * @param testFile the kml file name containing the location,
 *                 an associated NetworkLink file will also be created
 */
class GoogleEarthCollector(val testFile: String) extends Actor with Collector with ActorLogging {

  // create a kml network link
  val kml = new Kml(new NetworkLink("TestLink", new Link(testFile, OnInterval, 2)))

  // print the network link to file
  new KmlPrintWriter("NetworkLink_" + testFile).write(kml, new PrettyPrinter(80, 3))

  def receive = {
    case Collect(info) => collect(info)
  }

  def collect(info: Report) {
    info match {
      case tpv: TPV =>
        // if have a 2d or 3d fix, proceed
        if (tpv.mode.get > 1) {
          // must have at least some values for the lat lon
          if (tpv.lon.isDefined && tpv.lat.isDefined) {
            val alt = if (tpv.alt.isDefined) tpv.alt.get else 0.0
            val kml = new Kml(new Placemark("test", new Point(RelativeToGround, tpv.lon.get, tpv.lat.get, alt)))
            // write the placemark to the kml file
            new KmlPrintWriter(testFile).write(kml, new PrettyPrinter(80, 3))
          }
        }
    }
  }

}