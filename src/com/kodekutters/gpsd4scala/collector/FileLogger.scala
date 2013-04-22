package com.kodekutters.collector

import com.kodekutters.messages.{CloseCollectors, Collect}
import akka.actor.{ActorLogging, Actor}
import java.io.File
import com.kodekutters.gpsd4scala.collector.Collector
import com.kodekutters.gpsd4scala.types.TypeObject

/**
 * Author: Ringo Wathelet
 * Date: 2/04/13 
 * Version: 1
 */

/**
 * example of a simple plain text file logger that records all messages received
 * Note: a CloseCollectors message should be sent to the FileLogger
 * before ending the session to close the text file.
 */
object FileLogger {
  def apply(fileName: String): FileLogger = new FileLogger(fileName)
}

class FileLogger(file: File) extends Actor with Collector with ActorLogging {

  def this(fileName: String) = this(new File(fileName))

  val writer = new java.io.PrintWriter(file)

  def receive: Receive = {

    case Collect(obj) => collect(obj)

    case CloseCollectors =>
      writer.flush()
      writer.close()
  }

  // example of writing something to the file
  def collect(info: TypeObject) {
    writer.write(info.toString + "\n")
  }
}

