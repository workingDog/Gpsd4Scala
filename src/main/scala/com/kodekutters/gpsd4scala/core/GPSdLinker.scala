package com.kodekutters.gpsd4scala.core

import akka.actor._
import java.net.InetSocketAddress
import com.kodekutters.gpsd4scala.messages.ConnectionFailed

/**
 * Author: Ringo Wathelet
 * Date: 18/04/13 
 * Version: 1
 */

/**
 * main entry point to link to the gpsd server
 *
 * @param address the IP address of the gpsd server, e.g. localhost:2947
 */
class GPSdLinker(address: java.net.InetSocketAddress) extends Actor with ActorLogging with CollectorManager {

  def this(server: String, port: Int = 2947) = this(new InetSocketAddress(server, port))

  // the client that connects to the gpsd server
  val gpsdClient = context.actorOf(GpsdClient.props(address, collectorList), "client")

  // manage the collectors, then the linker receive
  def receive = manageCollectors orElse linkerReceive

  def linkerReceive: Receive = {

    // from the client, typically when no connection could be established
    case ConnectionFailed =>
      log.info("\n......connection failed, probably because the gpsd daemon is not running")
      // report to the parent that the connection failed
      context.parent ! ConnectionFailed
      context stop self

    // forward all other commands to the client
    case cmd => gpsdClient forward cmd
  }
}

object GPSdLinker {
  def props(address: java.net.InetSocketAddress): Props = Props(new GPSdLinker(address))

  def props(server: String, port: Int = 2947): Props = Props(new GPSdLinker(server, port))
}
