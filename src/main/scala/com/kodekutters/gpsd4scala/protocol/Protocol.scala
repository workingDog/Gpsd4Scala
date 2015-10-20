package com.kodekutters.gpsd4scala.protocol

import play.api.libs.json.Json

/**
  * Author: Ringo Wathelet
  * Date: 18/04/13
  * Version: 1
  */

// references: http://catb.org/gpsd/gpsd_json.html,
// http://catb.org/gpsd/client-howto.html and gpsd_json.c

//------------------------------------------------------------------------
//--------------------CORE SOCKET PROTOCOL REPORTS------------------------
//------------------------------------------------------------------------

trait Report

// todo ...  disabling mag because would make case class parameters limit > 22
case class ATT(tag: Option[String] = None, device: Option[String] = None, time: Option[String] = None,
               heading: Option[Double] = None,
               pitch: Option[Double] = None, pitch_st: Option[String] = None,
               yaw: Option[Double] = None, yaw_st: Option[String] = None,
               roll: Option[Double] = None, roll_st: Option[String] = None,
               dip: Option[Double] = None, acc_len: Option[Double] = None,
               acc_x: Option[Double] = None, acc_y: Option[Double] = None,
               acc_z: Option[Double] = None, gyro_x: Option[Double] = None,
               //     mag_len: Option[Double] = None, mag_x: Option[Double] = None,
               //     mag_y: Option[Double] = None, mag_z: Option[Double] = None,
               gyro_y: Option[Double] = None, depth: Option[Double] = None,
               temp: Option[Double] = None) extends Report

object ATT {
  implicit val attfmt = Json.format[ATT]
}

case class GST(tag: Option[String] = None, device: Option[String] = None,
               time: Option[String] = None, rms: Option[Double] = None,
               major: Option[Double] = None, minor: Option[Double] = None,
               orient: Option[Double] = None, lat: Option[Double] = None,
               lon: Option[Double] = None, alt: Option[Double] = None) extends Report

object GST {
  implicit val gstfmt = Json.format[GST]
}

case class SAT(PRN: Option[Int] = None, az: Option[Double] = None,
               el: Option[Double] = None, ss: Option[Double] = None,
               used: Option[Boolean] = None) extends Report

object SAT {
  implicit val satfmt = Json.format[SAT]
}

case class SKY(tag: Option[String] = None, device: Option[String] = None,
               time: Option[String] = None, xdop: Option[Double] = None,
               ydop: Option[Double] = None, vdop: Option[Double] = None,
               tdop: Option[Double] = None, gdop: Option[Double] = None,
               hdop: Option[Double] = None, pdop: Option[Double] = None,
               satellites: List[SAT] = List[SAT]()) extends Report

object SKY {
  implicit val skyfmt = Json.format[SKY]
}

case class TPV(tag: Option[String] = None, device: Option[String] = None,
               time: Option[String] = None, mode: Option[Int] = None, epc: Option[Double] = None,
               ept: Option[Double] = None, epx: Option[Double] = None, epy: Option[Double] = None,
               epv: Option[Double] = None, epd: Option[Double] = None, eps: Option[Double] = None,
               lat: Option[Double] = None, lon: Option[Double] = None, alt: Option[Double] = None,
               climb: Option[Double] = None, speed: Option[Double] = None, track: Option[Double] = None) extends Report

object TPV {
  implicit val tpvfmt = Json.format[TPV]
}

case class Error(message: Option[String] = None) extends Report

object Error {
  implicit val errorfmt = Json.format[Error]
}

//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------

case class Device(path: Option[String] = None, activated: Option[String] = None,
                  flags: Option[Int] = None, driver: Option[String] = None,
                  subtype: Option[String] = None, bps: Option[Int] = None,
                  parity: Option[String] = None, stopbits: Option[Int] = None,
                  native: Option[Int] = None, cycle: Option[Double] = None,
                  mincycle: Option[Double] = None) extends Report

object Device {
  implicit val devicefmt = Json.format[Device]
}

case class Devices(devices: List[Device] = List[Device](),
                   remote: Option[String] = None) extends Report

object Devices {
  implicit val devicesfmt = Json.format[Devices]
}

case class Version(release: Option[String] = None, rev: Option[String] = None,
                   proto_major: Option[Double] = None, proto_minor: Option[Double] = None,
                   remote: Option[String] = None) extends Report

object Version {
  implicit val versionfmt = Json.format[Version]
}

// Note: currently only json format is supported, hence the default json=true
case class Watch(enable: Option[Boolean] = Some(true), json: Option[Boolean] = Some(true),
                 nmea: Option[Boolean] = Some(false), raw: Option[Int] = None,
                 scaled: Option[Boolean] = None, timing: Option[Boolean] = None,
                 device: Option[String] = None, remote: Option[String] = None) extends Report

object Watch {
  implicit val watchfmt = Json.format[Watch]
}

case class Poll(time: Option[String] = None, active: Option[Int] = None,
                tpv: List[TPV] = List[TPV](),
                gst: List[GST] = List[GST](),
                sky: List[SKY] = List[SKY]()) extends Report

object Poll {
  implicit val pollfmt = Json.format[Poll]
}

case class PPS(device: Option[String] = None, real_sec: Option[Double] = None,
               real_musec: Option[Double] = None, clock_sec: Option[Double] = None,
               clock_musec: Option[Double] = None) extends Report

object PPS {
  implicit val ppsfmt = Json.format[PPS]
}

//------------------------------------------------------------------------
//----------------------------- SUBFRAME ---------------------------------
//------------------------------------------------------------------------

case class ALMANAC(ID: Option[Int] = None, Health: Option[Int] = None,
                   e: Option[Double] = None, toa: Option[Int] = None,
                   deltai: Option[Double] = None, Omegad: Option[Double] = None,
                   sqrtA: Option[Double] = None, Omega0: Option[Double] = None,
                   omega: Option[Double] = None, M0: Option[Double] = None,
                   af0: Option[Double] = None, af1: Option[Double] = None) extends Report

object ALMANAC {
  implicit val almanacfmt = Json.format[ALMANAC]
}

case class EPHEM1(WN: Option[Int] = None, IODC: Option[Int] = None,
                  L2: Option[Double] = None, ura: Option[Double] = None,
                  hlth: Option[Double] = None, L2P: Option[Int] = None,
                  Tgd: Option[Double] = None, toc: Option[Int] = None,
                  af2: Option[Double] = None, af1: Option[Double] = None,
                  af0: Option[Double] = None) extends Report

object EPHEM1 {
  implicit val ephem1fmt = Json.format[EPHEM1]
}

case class EPHEM2(IODE: Option[Int] = None, Crs: Option[Double] = None,
                  deltan: Option[Double] = None, M0: Option[Double] = None,
                  Cuc: Option[Double] = None, e: Option[Int] = None,
                  Cus: Option[Double] = None, sqrtA: Option[Int] = None,
                  toe: Option[Int] = None, FIT: Option[Int] = None,
                  AODO: Option[Int] = None) extends Report

object EPHEM2 {
  implicit val ephem2fmt = Json.format[EPHEM2]
}

case class EPHEM3(IODE: Option[Int] = None, IDOT: Option[Double] = None,
                  Cic: Option[Double] = None, Omega0: Option[Double] = None,
                  Cis: Option[Double] = None, i0: Option[Int] = None,
                  Crc: Option[Double] = None, omega: Option[Double] = None,
                  Omegad: Option[Double] = None) extends Report

object EPHEM3 {
  implicit val ephem3fmt = Json.format[EPHEM3]
}

case class ERD(ERD: Option[Array[Int]] = Some(new Array[Int](30)), ai: Option[Int] = None) extends Report

object ERD {
  implicit val erdfmt = Json.format[ERD]
}

case class HEALTH2(SV: Option[Array[Int]] = Some(new Array[Int](24)), toa: Option[Int] = None,
                   WNa: Option[Int] = None) extends Report

object HEALTH2 {
  implicit val health2fmt = Json.format[HEALTH2]
}

case class HEALTH(SV: Option[Array[Int]] = Some(new Array[Int](32)),
                  SVH: Option[Array[Int]] = Some(new Array[Int](8)),
                  data_id: Option[Int] = None) extends Report

object HEALTH {
  implicit val healthfmt = Json.format[HEALTH]
}

case class IONO(alpha0: Option[Double] = None, alpha1: Option[Double] = None,
                alpha2: Option[Double] = None, alpha3: Option[Double] = None,
                beta0: Option[Double] = None, beta1: Option[Double] = None,
                beta2: Option[Double] = None, beta3: Option[Double] = None,
                A0: Option[Double] = None, A1: Option[Double] = None,
                tot: Option[Double] = None,
                WNt: Option[Int] = None, leap: Option[Int] = None,
                WNlsf: Option[Int] = None, DN: Option[Int] = None,
                lsf: Option[Int] = None) extends Report

object IONO {
  implicit val ionofmt = Json.format[IONO]
}

case class SUBFRAME(device: Option[String] = None,
                    tSV: Option[Int] = None, TOW17: Option[Int] = None,
                    frame: Option[Int] = None, scaled: Option[Boolean] = None,
                    pageid: Option[Int] = None, system_message: Option[String] = None,
                    almanac: Option[ALMANAC] = None, ephem1: Option[EPHEM1] = None,
                    ephem2: Option[EPHEM2] = None, ephem3: Option[EPHEM3] = None,
                    erd: Option[ERD] = None, health: Option[HEALTH] = None,
                    health2: Option[HEALTH2] = None, iono: Option[IONO] = None) extends Report

object SUBFRAME {
  implicit val subframefmt = Json.format[SUBFRAME]
}

//------------------------------------------------------------------------
//------------------------ RTCM2 ----------------------------
//------------------------------------------------------------------------
//
//case class Rtcm2Sky(tipe: Option[Int] = None, station_id: Option[Int] = None,
//                         zcount: Option[Double] = None, seqnum: Option[Int] = None,
//                         length: Option[Int] = None,
//                         station_health: Option[Int] = None) extends TypeObject
//
//
//case class ALMANAC(tag: String = "ALMANAC",
//                         ID: Option[Int] = None, Health: Option[Int] = None,
//                         e: Option[Double] = None, toa: Option[Int] = None,
//                         deltai: Option[Double] = None, Omegad: Option[Double] = None,
//                         sqrtA: Option[Double] = None, Omega0: Option[Double] = None,
//                         omega: Option[Double] = None, M0: Option[Double] = None,
//                         af0: Option[Double] = None, af1: Option[Double] = None) extends TypeObject
//
//case class EPHEM1(clazz: String  = "EPHEM1",
//                        WN: Option[Int] = None, IODC: Option[Int] = None,
//                        L2: Option[Double] = None, ura: Option[Double] = None,
//                        hlth: Option[Double] = None, L2P: Option[Int] = None,
//                        Tgd: Option[Double] = None, toc: Option[Int] = None,
//                        af2: Option[Double] = None, af1: Option[Double] = None,
//                        af0: Option[Double] = None) extends TypeObject
//
//case class EPHEM2(clazz: String = "EPHEM2",
//                        IODE: Option[Int] = None, Crs: Option[Double] = None,
//                        deltan: Option[Double] = None, M0: Option[Double] = None,
//                        Cuc: Option[Double] = None, e: Option[Int] = None,
//                        Cus: Option[Double] = None, sqrtA: Option[Int] = None,
//                        toe: Option[Int] = None, FIT: Option[Int] = None,
//                        AODO: Option[Int] = None) extends TypeObject
//
//case class EPHEM3(clazz: String = "EPHEM3",
//                        IODE: Option[Int] = None, IDOT: Option[Double] = None,
//                        Cic: Option[Double] = None, Omega0: Option[Double] = None,
//                        Cis: Option[Double] = None, i0: Option[Int] = None,
//                        Crc: Option[Double] = None, omega: Option[Double] = None,
//                        Omegad: Option[Double] = None) extends TypeObject
//
//case class ERD(clazz: String  = "ERD",
//                     ERD: Option[Array[Int]] = Some(new Array[Int](30)), ai: Option[Int] = Some(-1)) extends TypeObject
//
//
//case class HEALTH2(clazz: String = "HEALTH2",
//                         SV: Option[Array[Int]] = Some(new Array[Int](24)), toa: Option[Int] = Some(-1),
//                         WNa: Option[Int] = Some(-1)) extends TypeObject
//
//case class HEALTH(tag: Option[String] = Some("HEALTH"),
//                        SV: Option[Array[Int]] = Some(new Array[Int](32)),
//                        SVH: Option[Array[Int]] = Some(new Array[Int](8)),
//                        data_id: Option[Int] = Some(-1)) extends TypeObject
//
//case class IONO(clazz: String  = "IONO",
//                      alpha0: Option[Double] = None, alpha1: Option[Double] = None,
//                      alpha2: Option[Double] = None, alpha3: Option[Double] = None,
//                      beta0: Option[Double] = None, beta1: Option[Double] = None,
//                      beta2: Option[Double] = None, beta3: Option[Double] = None,
//                      A0: Option[Double] = None, A1: Option[Double] = None,
//                      tot: Option[Double] = None,
//                      WNt: Option[Int] = Some(-1), leap: Option[Int] = Some(-1),
//                      WNlsf: Option[Int] = Some(-1), DN: Option[Int] = Some(-1),
//                      lsf: Option[Int] = Some(-1)) extends TypeObject
//
//case class SUBFRAME(clazz: String  = "SUBFRAME",
//                          device: Option[String] = None,
//                          subframeNumber: Option[Int] = Some(-1), satelliteNumber: Option[Int] = Some(-1),
//                          MSBs: Option[Int] = Some(-1), scaled: Option[Boolean] = Some(false),
//                          pageid: Option[Int] = Some(-1), systemMessage: Option[String] = None,
//                          almanac: Option[ALMANACObject] = None, ephem1: Option[EPHEM1Object] = None,
//                          ephem2: Option[EPHEM2Object] = None, ephem3: Option[EPHEM3Object] = None,
//                          erd: Option[ERDObject] = None, health: Option[HEALTHObject] = None,
//                          health2: Option[HEALTH2Object] = None, iono: Option[IONOObject] = None) extends TypeObject

//------------------------------------------------------------------------
//-----------------------------------------AIS----------------------------
//------------------------------------------------------------------------