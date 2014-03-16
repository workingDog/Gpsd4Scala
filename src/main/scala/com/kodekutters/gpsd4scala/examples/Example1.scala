package com.kodekutters.gpsd4scala.examples

import akka.actor.{ActorDSL, Props, ActorSystem}
import com.kodekutters.gpsd4scala.collector.BasicCollector
import com.kodekutters.gpsd4scala.core.GPSdLinker
import com.kodekutters.gpsd4scala.messages.{Watch, Start, Register}

/**
 * Author: Ringo Wathelet
 * Date: 22/04/13 
 * Version: 1
 */

/**
 * basic example use of the library
 */
object Example1 {
  def main(args: Array[String]) {
    implicit val context = ActorSystem("Example1")
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
