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
    implicit val system = ActorSystem("Example1")
    // create a collector that will receive the decoded gps data
    val collector = system.actorOf(Props(classOf[BasicCollector]))
    // create the client session actor
    val linker = ActorDSL.actor(new GPSdLinker("localhost", 2947))
    // register the collector
    linker ! Register(collector)
    Thread.sleep(1000)
    // start the client to connect to the gpsd server
    linker ! Start
    Thread.sleep(1000)
    // example of sending a command to the gpsd server
    linker ! Watch

  }
}
