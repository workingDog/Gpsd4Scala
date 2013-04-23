package com.kodekutters.gpsd4scala.test

import com.kodekutters.gpsd4scala.core.{GPSdLinker}
import akka.actor.{ActorSystem, Props}
import com.kodekutters.messages._
import com.kodekutters.gpsd4scala.collector.BasicCollector
import com.kodekutters.messages.RegisterCollector
import com.kodekutters.messages.Watch
import com.kodekutters.collector.FileLogger


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

    val collector = system.actorOf(Props(classOf[BasicCollector]))
    //    val logger = system.actorOf(Props(classOf[FileLogger], "testlogger.txt"))
    val linker = system.actorOf(Props(classOf[GPSdLinker], "localhost", 2947))
    linker ! RegisterCollector(collector)
    //   linker ! RegisterCollector(logger)
    Thread.sleep(1000)
    linker ! Start
    Thread.sleep(1000)
    linker ! Watch(true, true)
    Thread.sleep(1000)
    linker ! CloseCollectors

  }


}