package com.kodekutters.gpsd4scala.messages

import akka.actor.ActorRef
import com.kodekutters.gpsd4scala.protocol.Report

/**
 * Author: Ringo Wathelet
 * Date: 31/03/13 
 * Version: 1
 */

/**
 * the messages that can be used by the actors
 */

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

case class Collect(infoObj: Report, others: Any*)

case class Device(deviceObj: com.kodekutters.gpsd4scala.protocol.Device)

case class Watch(watchObj: com.kodekutters.gpsd4scala.protocol.Watch)

case class RegisterForDevice(collector: ActorRef, deviceName: String)

case class DeRegisterForDevice(collector: ActorRef, deviceName: String)