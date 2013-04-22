package com.kodekutters.gpsd4scala.core

import akka.actor.{ActorRef, Actor}
import scala.collection.mutable
import com.kodekutters.messages._
import com.kodekutters.messages.RegisterCollector
import com.kodekutters.messages.DeRegisterCollector
import com.kodekutters.messages.Collect

/**
 * Author: Ringo Wathelet
 * Date: 18/04/13 
 * Version: 1
 */

/**
 * keeper of the list of collectors, forward messages to the collectors
 */
trait CollectorManagement {
  this: Actor =>

  val collectorList = new mutable.HashSet[ActorRef]()

  protected def collectorManagement: Receive = {

    case RegisterCollector(collector) => collectorList += collector

    case DeRegisterCollector(collector) => collectorList -= collector

    case Collect(data, other) => collectorList.foreach(collector => collector forward Collect(data, other))

    case CloseCollectors => collectorList.foreach(collector => collector forward CloseCollectors)

    case CloseCollector(collector) => collector forward CloseCollectors

  }

}