package com.kodekutters.gpsd4scala.collector

import com.kodekutters.gpsd4scala.protocol.Report

/**
 * Author: Ringo Wathelet
 * Date: 19/04/13 
 * Version: 1
 */

trait Collector {
  def collect(info: Report)
}
