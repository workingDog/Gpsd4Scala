package com.kodekutters.gpsd4scala.collector

import akka.actor.{ActorLogging, Actor}
import com.kodekutters.gpsd4scala.protocol._
import com.kodekutters.gpsd4scala.messages.{CloseCollector, Close, CloseAll, Collect}

/**
 * Author: Ringo Wathelet
 * Date: 19/04/13 
 * Version: 1
 */

/**
 * a basic example of collecting the data from the gpsd server,
 * it shows all the possible Report objects that can be handled
 */
class BasicCollector extends Actor with Collector with ActorLogging {

  def receive = {
    case Collect(obj) => collect(obj)
    case CloseCollector => log.info("BasicCollector Close this Collector")
  }

  def collect(obj: Report) {
    obj match {
      case x: Poll => log.info("\n BasicCollector Poll: " + obj.toString)
      case x: Version => log.info("\n BasicCollector Version: " + obj.toString)
      case x: GST => log.info("\n BasicCollector GST: " + obj.toString)
      case x: Device => log.info("\n BasicCollector Device: " + obj.toString)
      case x: Devices => log.info("\n BasicCollector Devices: " + obj.toString)
      case x: SKY => log.info("\n BasicCollector SKY: " + obj.toString)
      case x: TPV => log.info("\n BasicCollector TPV: " + obj.toString)
      case x: ATT => log.info("\n BasicCollector ATT: " + obj.toString)
      case x: PPS => log.info("\n BasicCollector PPS: " + obj.toString)
      case x: Watch => log.info("\n BasicCollector Watch: " + obj.toString)
      case x: Error => log.info("\n BasicCollector Error: " + obj.toString)
      case _ => log.info("\n BasicCollector not recognised : " + obj.toString)
    }
  }

}