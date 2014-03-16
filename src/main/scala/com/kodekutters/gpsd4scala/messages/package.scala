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

//-------------------------------------------------------------
//--------------Event------------------------------------------
//-------------------------------------------------------------

case object Start

case object Stop

case object Poll

case object Watch

case object Version

case object Devices

case object Device

case object ConnectionFailed

case object CloseAll

case object CloseCollector


case class Close(collector: ActorRef)

case class Register(collector: ActorRef)

case class DeRegister(collector: ActorRef)

case class Collect(infoObj: TypeObject, others: Any*)

case class Device(deviceObj: DeviceObject)

case class Watch(watchObj: WatchObject)

case class RegisterForDevice(collector: ActorRef, deviceName: String)

case class DeRegisterForDevice(collector: ActorRef, deviceName: String)