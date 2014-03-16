package com.kodekutters.gpsd4scala.test

import com.kodekutters.gpsd4scala.core.{GPSdLinker}
import akka.actor.{Props, ActorDSL, ActorSystem}
import com.kodekutters.gpsd4scala.collector.BasicCollector
import com.kodekutters.gpsd4scala.messages.{Watch, Start, Register}

/**
 * Author: Ringo Wathelet
 * Date: 19/04/13 
 * Version: 1
 */

/**
 * test
 */

// todo

object TestApp {
  def main(args: Array[String]) {
    implicit val system = ActorSystem("TestApp")
    // create a collector that will receive the decoded gps data
    val collector = system.actorOf(Props(classOf[BasicCollector]))
    // create the client session actor
    val linker = system.actorOf(Props(classOf[GPSdLinker], "localhost", 2947))
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