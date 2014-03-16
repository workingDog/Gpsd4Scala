package com.kodekutters.gpsd4scala.core

import scala.Some
import com.kodekutters.gpsd4scala.types._
import play.api.libs.json._

/**
 * Author: Ringo Wathelet
 * Date: 18/04/13
 * Version: 1
 */

/**
 * parse the (json) ByteString received from the gpsd server into TypeObjects
 */
class GpsdParser {

  /**
   * parse the input ByteString which may contain multiple json objects,
   * into a corresponding list of TypeObjects
   *
   * @param data the input ByteString to parse, this is split on new line "\\r?\\n"
   *             to extract possible multiple json objects
   * @return an optional list of TypeObjects corresponding to the input json objects
   */
  def parse(data: akka.util.ByteString): Option[List[TypeObject]] = {
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
   * parse a string assumed to represent only one json TypeObject
   * @param line the string to parse
   * @return a TypeObject
   */
   def parseOne(line: String): Option[TypeObject] = {
    try {
      val jsMsg = Json.parse(line)
      val clazz = jsMsg \ "class"
      clazz match {
        case JsString("GST") => Json.fromJson[GSTObject](jsMsg).asOpt
        case JsString("TPV") => Json.fromJson[TPVObject](jsMsg).asOpt
        case JsString("ATT") => Json.fromJson[ATTObject](jsMsg).asOpt
        case JsString("DEVICE") => Json.fromJson[DeviceObject](jsMsg).asOpt
        case JsString("DEVICES") => Json.fromJson[DevicesObject](jsMsg).asOpt
        case JsString("SKY") => Json.fromJson[SKYObject](jsMsg).asOpt
        case JsString("VERSION") => Json.fromJson[VersionObject](jsMsg).asOpt
        case JsString("WATCH") => Json.fromJson[WatchObject](jsMsg).asOpt
        case JsString("POLL") => Json.fromJson[PollObject](jsMsg).asOpt
        case JsString("PPS") => Json.fromJson[PPSObject](jsMsg).asOpt
        case JsString("ERROR") => Json.fromJson[ErrorObject](jsMsg).asOpt
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
    }
  }

}
