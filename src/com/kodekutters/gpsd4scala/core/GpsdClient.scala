package com.kodekutters.gpsd4scala.core

import akka.actor._
import akka.io.{Tcp, IO}
import com.kodekutters.messages._
import java.net.InetSocketAddress
import akka.util.ByteString
import scala.collection.mutable

/**
 * Author: Ringo Wathelet
 * Date: 18/04/13 
 * Version: 1
 */

/**
 * the client that connects to the gpsd server.
 *
 * @param address of the gpsd server
 * @param collectorList the list of collectors
 */
class GpsdClient(val address: InetSocketAddress, val collectorList: mutable.HashSet[ActorRef]) extends Actor with ActorLogging {

  // the parser to decode the messages from the server into corresponding scala objects
  val parser = new GpsdParser()

  import Tcp._
  import context.system


  def receive = {

    case Start => println("in GpsdClient Start: " + address.toString)
      IO(Tcp) ! Connect(address)

    case Stop => println("in GpsdClient Stop")
      sender ! Tcp.Close
      self ! Close

    case Pause => println("in GpsdClient Pause")

    case CommandFailed(_: Connect) => println("in GpsdClient CommandFailed")
      context stop self

    case c@Connected(remote, local) => println("in GpsdClient Connected remote=" + remote + " local=" + local)
      val connection = sender
      connection ! Register(self)
      context become {

      case Received(data) =>
        val decodedList = parser.parse(data)
        if (decodedList.isDefined)
          collectorList.foreach(collector => decodedList.get.foreach(dataObj => collector ! Collect(dataObj)))

      case Watch(enable, dumpData, device) => println("in GpsdClient sending Watch")
        val jsonString = if (device.isEmpty)
          s"""{ "class": "WATCH", "enable": $enable, "json": $dumpData}"""
        else
          s"""{ "class": "WATCH", "enable": $enable, "json": $dumpData, "device": $device }"""
        connection ! Write(ByteString("?WATCH=" + jsonString + "\n"))

      case Poll => println("in GpsdClient sending Poll")
        connection ! Write(ByteString("?POLL;" + "\n"))

      case Version => println("in GpsdClient sending Version")
        connection ! Write(ByteString("?VERSION;" + "\n"))

      case CommandFailed(w: Write) => log.info("\nin GpsdClient CommandFailed")

      case Close => println("in GpsdClient Connected Close")
        connection ! Close

      case data: ByteString => println("in GpsdClient Connected write data")
        connection ! Write(data)

      case _: ConnectionClosed => println("in GpsdClient Connected ConnectionClosed")
        context stop self

      case x => log.info("\nin GpsdClient Connected x=" + x.toString)

    }

    case x => log.info("\nin GpsdClient x=" + x.toString)

  }

}
