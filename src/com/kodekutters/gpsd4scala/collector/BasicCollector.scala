package com.kodekutters.gpsd4scala.collector

import akka.actor.Actor
import com.kodekutters.gpsd4scala.types._
import com.kodekutters.gpsd4scala.messages.{CloseCollectors, Collect}

/**
 * Author: Ringo Wathelet
 * Date: 19/04/13 
 * Version: 1
 */

/**
 * a basic example of collecting the data from the gpsd server
 */
class BasicCollector extends Actor with Collector {

  def receive = {
    case Collect(obj) => collect(obj)
    case CloseCollectors => println("in BasicCollector CloseCollectors")
  }

  def collect(obj: TypeObject) {
    obj match {
      case x: PollObject => println("in BasicCollector PollObject: " + obj.toString())
      case x: VersionObject => println("in BasicCollector VersionObject: " + obj.toString())
      case x: GSTObject => println("in BasicCollector GSTObject: " + obj.toString())
      case x: DeviceObject => println("in BasicCollector DeviceObject: " + obj.toString())
      case x: DevicesObject => println("in BasicCollector DevicesObject: " + obj.toString())
      case x: SKYObject => println("in BasicCollector SKYObject: " + obj.toString())
      case x: TPVObject => println("in BasicCollector TPVObject: " + obj.toString())
      case x: ATTObject => println("in BasicCollector ATTObject: " + obj.toString())
      case x: PPSObject => println("in BasicCollector PPSObject: " + obj.toString())
      case x: WatchObject => println("in BasicCollector WatchObject: " + obj.toString())
      case x: ErrorObject => println("in BasicCollector ErrorObject: " + obj.toString())
      case _ => println("in BasicCollector not recognised object: " + obj.toString())
    }
  }

}