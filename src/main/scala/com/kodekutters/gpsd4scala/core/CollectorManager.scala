package com.kodekutters.gpsd4scala.core

import akka.actor.{ActorRef, Actor}
import scala.collection.mutable
import com.kodekutters.gpsd4scala.messages._
import com.kodekutters.gpsd4scala.messages.Register
import com.kodekutters.gpsd4scala.messages.Collect
import com.kodekutters.gpsd4scala.messages.DeRegister

/**
 * Author: Ringo Wathelet
 * Date: 18/04/13 
 * Version: 1
 */

/**
 * keeper of the list of collectors, forwards all messages to the collectors
 */
trait CollectorManager {
  this: Actor =>

  val collectorList = new mutable.HashSet[ActorRef]()

  protected def manageCollectors: Receive = {

    case Register(collector) => collectorList += collector

    case DeRegister(collector) => collectorList -= collector

    case Collect(data, other) => collectorList.foreach(collector => collector forward Collect(data, other))

    case CloseAll => collectorList.foreach(collector => collector forward CloseCollector)

    case Close(collector) => collector forward CloseCollector

  }

}