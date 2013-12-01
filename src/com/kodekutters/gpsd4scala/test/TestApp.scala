package com.kodekutters.gpsd4scala.test

import com.kodekutters.gpsd4scala.core.{GPSdLinker}
import akka.actor.{ActorDSL, ActorSystem}
import com.kodekutters.gpsd4scala.messages._
import com.kodekutters.gpsd4scala.collector.BasicCollector

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

      val collector = ActorDSL.actor(new BasicCollector())
      val linker = ActorDSL.actor(new GPSdLinker("localhost", 2947))
      linker ! RegisterCollector(collector)
      Thread.sleep(1000)
      linker ! Start
      Thread.sleep(1000)
      linker ! Watch
      Thread.sleep(20000)
      linker ! Stop
      Thread.sleep(1000)
      system.shutdown()
  }

}