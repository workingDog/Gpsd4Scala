package com.kodekutters.gpsd4scala.core

import akka.actor._
import akka.io.{Tcp, IO}
import com.kodekutters.messages._
import java.net.InetSocketAddress
import akka.util.ByteString
import scala.collection.mutable
import spray.json._

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
  import GpsdJsonProtocol._

  // the parser to decode the messages from gpsd into corresponding scala objects
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
          val decodedList = parser.parse(data)
          if (decodedList.isDefined)
            collectorList.foreach(collector => decodedList.get.foreach(dataObj => collector ! Collect(dataObj)))

        case Watch => connection ! Write(ByteString("?WATCH;"))

        case Poll => connection ! Write(ByteString("?POLL;"))

        case Version => connection ! Write(ByteString("?VERSION;"))

        case Devices => connection ! Write(ByteString("?DEVICES;"))

        case Device => connection ! Write(ByteString("?DEVICE;"))

        case Device(devObj) => connection ! Write(ByteString("?DEVICE=" + devObj.toJson))

        case Watch(watchObj) => connection ! Write(ByteString("?WATCH=" + watchObj.toJson))

        case CommandFailed(w: Write) => log.info("\nin GpsdClient CommandFailed ", w)

        case Close | Stop =>
          connection ! Close
          context stop self

        case data: ByteString => connection ! Write(data)

        case _: ConnectionClosed => context stop self

        case x => log.info("\nin GpsdClient message not processed: " + x.toString)

      }

    case x => log.info("\nin GpsdClient message not processed: " + x.toString)

  }

}
