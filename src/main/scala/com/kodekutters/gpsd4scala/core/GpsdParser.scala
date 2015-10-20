package com.kodekutters.gpsd4scala.core

import com.kodekutters.gpsd4scala.protocol._
import play.api.libs.json._

/**
  * Author: Ringo Wathelet
  * Date: 18/04/13
  * Version: 1
  */

/**
  * parse the (json) ByteString received from the gpsd server into Report objects
  */
class GpsdParser {

  /**
    * parse the input ByteString which may contain multiple json objects,
    * into a corresponding list of Report objects
    *
    * @param data the input ByteString to parse, this is split on new line "\\r?\\n"
    *             to extract possible multiple json objects
    * @return an optional list of Report objects corresponding to the input json objects
    */
  def parse(data: akka.util.ByteString): Option[List[Report]] = {
    try {
      // split the data string on a new line, in case it has multiple json objects
      val lines = data.utf8String.trim.split("\\r?\\n")
      val results = lines collect { case line => parseOne(line.trim) }
      Some(results.flatten.toList)
    } catch {
      case _: Throwable => None
    }
  }

  /**
    * parse a string assumed to represent only one json Report object
    * @param line the string to parse
    * @return a Report object
    */
  def parseOne(line: String): Option[Report] = {
    try {
      val jsMsg = Json.parse(line)
      val clazz = (jsMsg \ "class").getOrElse(JsString(""))
      clazz match {
        case JsString("GST") => Json.fromJson[GST](jsMsg).asOpt
        case JsString("TPV") => Json.fromJson[TPV](jsMsg).asOpt
        case JsString("ATT") => Json.fromJson[ATT](jsMsg).asOpt
        case JsString("DEVICE") => Json.fromJson[Device](jsMsg).asOpt
        case JsString("DEVICES") => Json.fromJson[Devices](jsMsg).asOpt
        case JsString("SKY") => Json.fromJson[SKY](jsMsg).asOpt
        case JsString("VERSION") => Json.fromJson[Version](jsMsg).asOpt
        case JsString("WATCH") => Json.fromJson[Watch](jsMsg).asOpt
        case JsString("POLL") => Json.fromJson[Poll](jsMsg).asOpt
        case JsString("PPS") => Json.fromJson[PPS](jsMsg).asOpt
        case JsString("ERROR") => Json.fromJson[Error](jsMsg).asOpt
        //        case JsString("ALMANAC") => Json.fromJson[ALMANACObject](jsMsg).asOpt)
        //        case JsString("EPHEM1") => Json.fromJson[EPHEM1Object](jsMsg).asOpt)
        //        case JsString("EPHEM2") => Json.fromJson[EPHEM2Object](jsMsg).asOpt)
        //        case JsString("EPHEM3") => Json.fromJson[EPHEM3Object](jsMsg).asOpt)
        //        case JsString("ERD") => Json.fromJson[ERDObject](jsMsg).asOpt)
        //        case JsString("HEALTH") => Json.fromJson[HEALTHObject](jsMsg).asOpt)
        //        case JsString("HEALTH2") => Json.fromJson[HEALTH2Object](jsMsg).asOpt)
        //        case JsString("IONO") => Json.fromJson[IONOObject](jsMsg).asOpt)
        //        case JsString("SUBFRAME") => Json.fromJson[SUBFRAMEObject](jsMsg).asOpt)
        case _ => None
      }
    } catch {
      case _: Throwable => None
    }
  }

}
