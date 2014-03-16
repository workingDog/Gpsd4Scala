package com.kodekutters.gpsd4scala.collector

import akka.actor.{ActorLogging, Actor}
import com.kodekutters.gpsd4scala.types._
import com.kodekutters.gpsd4scala.types.DevicesObject
import com.kodekutters.gpsd4scala.types.GSTObject
import com.kodekutters.gpsd4scala.types.DeviceObject
import com.kodekutters.gpsd4scala.messages.{CloseAll, Collect}

/**
 * Author: Ringo Wathelet
 * Date: 19/04/13 
 * Version: 1
 */

/**
 * a basic example of collecting the data from the gpsd server,
 * it shows all the possible TypeObject that can be handled
 */
class BasicCollector extends Actor with Collector with ActorLogging {

  def receive = {
    case Collect(obj) => collect(obj)
    case CloseAll => log.info("BasicCollector CloseAll Collectors")
  }

  def collect(obj: TypeObject) {
    obj match {
      case x: PollObject => log.info("\n BasicCollector PollObject: " + obj.toString())
      case x: VersionObject => log.info("\n BasicCollector VersionObject: " + obj.toString())
      case x: GSTObject => log.info("\n BasicCollector GSTObject: " + obj.toString())
      case x: DeviceObject => log.info("\n BasicCollector DeviceObject: " + obj.toString())
      case x: DevicesObject => log.info("\n BasicCollector DevicesObject: " + obj.toString())
      case x: SKYObject => log.info("\n BasicCollector SKYObject: " + obj.toString())
      case x: TPVObject => log.info("\n BasicCollector TPVObject: " + obj.toString())
      case x: ATTObject => log.info("\n BasicCollector ATTObject: " + obj.toString())
      case x: PPSObject => log.info("\n BasicCollector PPSObject: " + obj.toString())
      case x: WatchObject => log.info("\n BasicCollector WatchObject: " + obj.toString())
      case x: ErrorObject => log.info("\n BasicCollector ErrorObject: " + obj.toString())
      case _ => log.info("\n BasicCollector not recognised object: " + obj.toString())
    }
  }

}