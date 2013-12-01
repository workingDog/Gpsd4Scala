package com.kodekutters.gpsd4scala.messages

import akka.actor.ActorRef
import com.kodekutters.gpsd4scala.types.{DeviceObject, WatchObject, TypeObject}

/**
 * Author: Ringo Wathelet
 * Date: 31/03/13 
 * Version: 1
 */

/**
 * the messages used by the actors
 */

  // for zero or one argument events
  sealed trait Event

  // for two or more arguments events
  sealed trait ComplexEvent

  //-------------------------------------------------------------
  //--------------Event------------------------------------------
  //-------------------------------------------------------------

  case object Start extends Event

  case object Stop extends Event

  case object Poll extends Event

  case object Watch extends Event

  case object Version extends Event

  case object Devices extends Event

  case object Device extends Event

  case object ConnectionFailed extends Event

  case object CloseCollectors extends Event

  case class CloseCollector(collector: ActorRef) extends Event

  case class RegisterCollector(collector: ActorRef) extends Event

  case class DeRegisterCollector(collector: ActorRef) extends Event

  case class Collect(infoObj: TypeObject, others: Any*) extends Event

  case class Device(deviceObj: DeviceObject) extends Event

  case class Watch(watchObj: WatchObject) extends Event


  //---------------------------------------------------------------
  //--------------ComplexEvent------------------------------------
  //---------------------------------------------------------------

  case class RegisterCollectorForDevice(collector: ActorRef, deviceName: String) extends ComplexEvent

  case class DeRegisterCollectorForDevice(collector: ActorRef, deviceName: String) extends ComplexEvent

