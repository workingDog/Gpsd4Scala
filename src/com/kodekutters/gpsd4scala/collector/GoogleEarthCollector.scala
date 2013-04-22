//package com.kodekutters.collector
//
//import akka.actor.{ActorLogging, Actor}
//import scala.xml.PrettyPrinter
//import com.scalakml.io.KmlPrintWriter
//import com.scalakml.kml._
//import com.scalakml.kml.Point
//import com.scalakml.kml.Placemark
//import com.kodekutters.messages.Collect
//import com.scalakml.kml.Kml
//import com.kodekutters.decoder.{NMEA_0183_RMC}
//import com.kodekutters.gpsd4scala.collector.Collector
//import com.kodekutters.gpsd4scala.types.TypeObject
//
///**
// * Author: Ringo Wathelet
// * Date: 30/03/13
// * Version: 1
// */
//
///**
// * collect decoded json messages, create a NetworkLink file and a kml file
// * with a placemark at the GPS location.
// * Launch Google Earth by double clicking of the NetworkLink file.
// * The locations in the kml file are poled by the NetworkLink every 2 seconds in this example.
// *
// */
//object GoogleEarthCollector {
//
//  def apply(fileName: String): GoogleEarthCollector = new GoogleEarthCollector(fileName)
//
//}
//
//class GoogleEarthCollector(val testFile: String) extends Actor with Collector with ActorLogging {
//
//  // the network link file name
//  val netLinkFile = "NetworkLink_" + testFile
//
//  // create the network link file
//  val kml = new Kml(new NetworkLink("TestLink", new Link(testFile, OnInterval, 2)))
//  // print the network link file
//  new KmlPrintWriter(netLinkFile).write(Option(kml), new PrettyPrinter(80, 3))
//
//  def receive = {
//    case Collect(sentence, others) => collect(sentence)
//  }
//
//  def collect(info: TypeObject) {
//    info(0) match {
//      case nmea: NMEA_0183_RMC =>
//        // simply write the placemark to the kml file
//        val kml = new Kml(new Placemark("GPRMC",
//          new Point(RelativeToGround, nmea.location.lon, nmea.location.lat, nmea.location.alt)))
//        new KmlPrintWriter(testFile).write(Option(kml), new PrettyPrinter(80, 3))
//    }
//  }
//
//}

