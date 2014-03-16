package com.kodekutters.gpsd4scala.core

import akka.actor._
import java.net.InetSocketAddress
import akka.actor.SupervisorStrategy.Restart
import akka.actor.OneForOneStrategy
import scala.concurrent.duration._
import scala.util.{Failure, Success}
import scala.concurrent.{ExecutionContext, Await}
import akka.pattern.ask
import akka.util.Timeout
import ExecutionContext.Implicits.global
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

  implicit val timeout = Timeout(5 seconds)

  // the client that connects to the gpsd server
  val gpsdClient = context.actorOf(Props(new GpsdClient(address, collectorList)))

  // supervise the client here ... TODO
//  override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
//    case _ => {
//      log.info("\nin GPSdLinker supervisorStrategy Restart")
//      Restart
//    }
//  }

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
