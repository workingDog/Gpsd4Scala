package com.kodekutters.gpsd4scala.core

import akka.actor._
import akka.io.{Tcp, IO}
import java.net.InetSocketAddress
import akka.util.ByteString
import scala.collection.mutable
import com.kodekutters.gpsd4scala.messages._
import play.api.libs.json.Json


/**
 * Author: Ringo Wathelet
 * Date: 18/04/13 
 * Version: 1
 */


/**
 * the client actor that connects to the gpsd daemon.
 *
 * @param address of the gpsd server
 * @param collectorList the list of collectors
 */
class GpsdClient(val address: InetSocketAddress, val collectorList: mutable.HashSet[ActorRef]) extends Actor with ActorLogging {

  import Tcp._
  import context.system

  // the parser to decode the messages from gpsd into corresponding Report objects
  val parser = new GpsdParser()

  def receive = {

    case Start => IO(Tcp) ! Connect(address)

    case Stop =>
      sender ! Close
      context stop self

    case CommandFailed(_: Connect) =>
      // report to the parent that the connection failed
      context.parent ! ConnectionFailed
      context stop self

    case c@Connected(remote, local) =>
      val connection = sender
      connection ! Register(self)
      context become {

        // receiving data from the gpsd server
        case Received(data) =>
          // decode the data into a list of Report
          val reportList = parser.parse(data)
          // send all Report to the collectors
          if (reportList.isDefined)
            collectorList.foreach(collector => reportList.get.foreach(report => collector ! Collect(report)))

        // sending commands to the server
        case Watch => connection ! Write(ByteString("?WATCH;"))

        case Poll => connection ! Write(ByteString("?POLL;"))

        case Version => connection ! Write(ByteString("?VERSION;"))

        case Devices => connection ! Write(ByteString("?DEVICES;"))

        case Device => connection ! Write(ByteString("?DEVICE;"))

        case Device(devObj) => connection ! Write(ByteString("?DEVICE=" + Json.toJson(devObj)))

        case Watch(watchObj) => connection ! Write(ByteString("?WATCH=" + Json.toJson(watchObj)))

        case data: ByteString => connection ! Write(data)

        // receiving other types of messages
        case Close | Stop =>
          connection ! Close
          context stop self

        case x: ConnectionClosed => context stop self

        case CommandFailed(w: Write) => log.info("\nGpsdClient CommandFailed ", w)

        case x => log.info("\nGpsdClient message not actioned: " + x.toString)

      }

    case x => log.info("\nGpsdClient message not actioned: " + x.toString)

  }

}

object GpsdClient {
  def props(address: java.net.InetSocketAddress, collectorList: mutable.HashSet[ActorRef]): Props = Props(new GpsdClient(address, collectorList))
}
