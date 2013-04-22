package com.kodekutters.gpsd4scala.test

import spray.json._
import com.kodekutters.gpsd4scala.core.{GPSdLinker}
import akka.actor.{ActorSystem, Props}
import com.kodekutters.messages._
import com.kodekutters.gpsd4scala.collector.BasicCollector
import com.kodekutters.messages.RegisterCollector
import com.kodekutters.messages.Watch
import com.kodekutters.gpsd4scala.types.GSTObject
import akka.util.ByteString

/**
 * Author: Ringo Wathelet
 * Date: 19/04/13 
 * Version: 1
 */

object TestApp {
  def main(args: Array[String]) {
    test1
  }

  def test1 = {
    implicit val system = ActorSystem("TestApp")

    val collector = system.actorOf(Props(new BasicCollector()))
    val linker = system.actorOf(Props(new GPSdLinker("localhost", 2947)))
    linker ! RegisterCollector(collector)
    Thread.sleep(1000)
    linker ! Start
    Thread.sleep(1000)
    linker ! Poll
    Thread.sleep(1000)
    linker ! Watch(true, true, "")
    Thread.sleep(1000)
    linker ! CloseCollectors

  }


}