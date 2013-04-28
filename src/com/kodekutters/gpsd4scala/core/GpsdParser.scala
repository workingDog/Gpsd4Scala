package com.kodekutters.gpsd4scala.core

import scala.Some
import com.kodekutters.gpsd4scala.types._
import spray.json._

/**
 * Author: Ringo Wathelet
 * Date: 18/04/13 
 * Version: 1
 */

/**
 * set of implicits including additional jsonFormats
 * ref: https://github.com/spray/spray-json
 */
object GpsdJsonProtocol extends DefaultJsonProtocol with ExtraProductFormats {
  implicit val GSTObjectFormat = jsonFormat10(GSTObject)
  implicit val ATTObjectFormat = jsonFormat19(ATTObject)
  implicit val DeviceObjectFormat = jsonFormat11(DeviceObject)
  implicit val DevicesObjectFormat = jsonFormat2(DevicesObject)
  implicit val SATObjectFormat = jsonFormat5(SATObject)
  implicit val SKYObjectFormat = jsonFormat11(SKYObject)
  implicit val VersionObjectFormat = jsonFormat5(VersionObject)
  implicit val WatchObjectFormat = jsonFormat8(WatchObject)
  implicit val TPVObjectFormat = jsonFormat17(TPVObject)
  implicit val PollObjectFormat = jsonFormat5(PollObject)
  implicit val PPSObjectFormat = jsonFormat5(PPSObject)
  implicit val ErrorObjectFormat = jsonFormat1(ErrorObject)

  //  implicit val ALMANACObjectFormat = jsonFormat12(ALMANACObject)
  //  implicit val EPHEM1ObjectFormat = jsonFormat11(EPHEM1Object)
  //  implicit val EPHEM2ObjectFormat = jsonFormat11(EPHEM2Object)
  //  implicit val EPHEM3ObjectFormat = jsonFormat9(EPHEM3Object)
  //  implicit val ERDObjectFormat = jsonFormat2(ERDObject)
  //  implicit val HEALTHObjectFormat = jsonFormat3(HEALTHObject)
  //  implicit val HEALTH2ObjectFormat = jsonFormat3(HEALTH2Object)
  //  implicit val IONOObjectFormat = jsonFormat16(IONOObject)
  //  implicit val SUBFRAMEObjectFormat = jsonFormat15(SUBFRAMEObject)
}

/**
 * the parser decoding json objects into corresponding scala objects
 */
class GpsdParser {

  import GpsdJsonProtocol._

  /**
   * parse the input ByteString which may contain multiple json objects,
   * into a corresponding list of scala objects
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
   * parse a string assumed to be only one json object
   *
   * @param line a string representing one json object
   * @return the option of a TypeObject representing the json object
   */
  def parseOne(line: String): Option[TypeObject] = {
    try {
      val jsonObj = line.asJson
      // expect only one "class" json object
      val clazz = jsonObj.asJsObject.getFields("class").head
      clazz match {
        case JsString("GST") => Some(jsonObj.convertTo[GSTObject])
        case JsString("TPV") => Some(jsonObj.convertTo[TPVObject])
        case JsString("ATT") => Some(jsonObj.convertTo[ATTObject])
        case JsString("DEVICE") => Some(jsonObj.convertTo[DeviceObject])
        case JsString("DEVICES") => Some(jsonObj.convertTo[DevicesObject])
        case JsString("SKY") => Some(jsonObj.convertTo[SKYObject])
        case JsString("VERSION") => Some(jsonObj.convertTo[VersionObject])
        case JsString("WATCH") => Some(jsonObj.convertTo[WatchObject])
        case JsString("POLL") => Some(jsonObj.convertTo[PollObject])
        case JsString("PPS") => Some(jsonObj.convertTo[PPSObject])
        case JsString("ERROR") => Some(jsonObj.convertTo[ErrorObject])

        //        case JsString("SUBFRAME") => Some(jsonObj.convertTo[SUBFRAMEObject])
        //        case JsString("ALMANAC") => Some(jsonObj.convertTo[ALMANACObject])
        //        case JsString("EPHEM1") => Some(jsonObj.convertTo[EPHEM1Object])
        //        case JsString("EPHEM2") => Some(jsonObj.convertTo[EPHEM2Object])
        //        case JsString("EPHEM3") => Some(jsonObj.convertTo[EPHEM3Object])
        //        case JsString("ERD") => Some(jsonObj.convertTo[ERDObject])
        //        case JsString("HEALTH") => Some(jsonObj.convertTo[HEALTHObject])
        //        case JsString("HEALTH2") => Some(jsonObj.convertTo[HEALTH2Object])
        //        case JsString("IONO") => Some(jsonObj.convertTo[IONOObject])
        //        case JsString("SUBFRAME") => Some(jsonObj.convertTo[SUBFRAMEObject])

        case _ => None
      }
    } catch {
      case _: Throwable => None
    }
  }

}
