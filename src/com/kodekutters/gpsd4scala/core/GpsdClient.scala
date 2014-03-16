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

  // the parser to decode the messages from gpsd into corresponding TypeObjects
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

    case c @ Connected(remote, local) =>
      val connection = sender
      connection ! Register(self)
      context become {

        case Received(data) =>
          // decode the data into a list of TypeObjects
          val typeObjectList = parser.parse(data)
          // send all TypeObjects to the collectors
          if (typeObjectList.isDefined)
            collectorList.foreach(collector => typeObjectList.get.foreach(typeObj => collector ! Collect(typeObj)))

        case Watch => connection ! Write(ByteString("?WATCH;"))

        case Poll => connection ! Write(ByteString("?POLL;"))

        case Version => connection ! Write(ByteString("?VERSION;"))

        case Devices => connection ! Write(ByteString("?DEVICES;"))

        case Device => connection ! Write(ByteString("?DEVICE;"))

        case Device(devObj) => connection ! Write(ByteString("?DEVICE=" + Json.toJson(devObj)))

        case Watch(watchObj) => connection ! Write(ByteString("?WATCH=" + Json.toJson(watchObj)))

        case CommandFailed(w: Write) => log.info("\nGpsdClient CommandFailed ", w)

        case Close | Stop =>
          connection ! Close
          context stop self

        case data: ByteString => connection ! Write(data)

        case _: ConnectionClosed => context stop self

        case x => log.info("\nGpsdClient message not processed: " + x.toString)

      }

    case x => log.info("\nGpsdClient message not processed: " + x.toString)

  }

}