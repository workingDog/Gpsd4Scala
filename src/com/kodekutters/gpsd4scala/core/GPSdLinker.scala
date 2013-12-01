package com.kodekutters.gpsd4scala.core

import akka.actor._
import java.net.InetSocketAddress
import akka.actor.SupervisorStrategy.Restart
import akka.actor.OneForOneStrategy
import scala.concurrent.duration._
import com.kodekutters.gpsd4scala.messages.ConnectionFailed

/**
 * Author: Ringo Wathelet
 * Date: 18/04/13 
 * Version: 1
 */

/**
 * main entry point to link to the gpsd server
 *
 * ref: http://www.catb.org/gpsd/
 *
 * @param address the socket address of the gpsd server, e.g. host name: localhost and port: 2947
 */
class GPSdLinker(address: java.net.InetSocketAddress) extends Actor with ActorLogging with CollectorManagement {

  def this(server: String, port: Int = 2947) = this(new InetSocketAddress(server, port))

  // the client that connects to the gpsd server
  val gpsdClient = ActorDSL.actor(new GpsdClient(address, collectorList))

  // supervise the client here ... TODO
//  override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
//    case _ => Restart
//  }

  def receive = collectorManagement orElse linkerReceive

  def linkerReceive: Receive = {

    // from the client, typically when no connection could be established
    case ConnectionFailed =>
      log.info("\n......connection failed, probably because the gpsd daemon is not running")
      // report to the parent that the connection failed
      context.parent ! ConnectionFailed
      context stop self

    // forward all other messages to the client
    case x => gpsdClient forward x
  }

}
