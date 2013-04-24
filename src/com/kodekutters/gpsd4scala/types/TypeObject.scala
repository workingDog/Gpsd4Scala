package com.kodekutters.gpsd4scala.types

import scala.Predef.String
import scala._

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

trait TypeObject {}

// todo ...  missing mag because would make case class too long, > 22
case class ATTObject(tag: Option[String] = None, device: Option[String] = None, time: Option[String] = None,
                     heading: Option[Double] = None,
                     pitch: Option[Double] = None, pitch_st: Option[String] = None,
                     yaw: Option[Double] = None, yaw_st: Option[String] = None,
                     roll: Option[Double] = None, roll_st: Option[String] = None,
                     dip: Option[Double] = None, acc_len: Option[Double] = None,
                     acc_x: Option[Double] = None, acc_y: Option[Double] = None,
                     acc_z: Option[Double] = None, gyro_x: Option[Double] = None,
                     gyro_y: Option[Double] = None, depth: Option[Double] = None,
                     temp: Option[Double] = None) extends TypeObject

case class GSTObject(tag: Option[String] = None, device: Option[String] = None,
                     time: Option[String] = None, rms: Option[Double] = None,
                     major: Option[Double] = None, minor: Option[Double] = None,
                     orient: Option[Double] = None, lat: Option[Double] = None,
                     lon: Option[Double] = None, alt: Option[Double] = None) extends TypeObject

case class SATObject(PRN: Option[Int] = None, az: Option[Double] = None,
                     el: Option[Double] = None, ss: Option[Double] = None,
                     used: Option[Boolean] = None) extends TypeObject

case class SKYObject(tag: Option[String] = None, device: Option[String] = None,
                     time: Option[String] = None, xdop: Option[Double] = None,
                     ydop: Option[Double] = None, vdop: Option[Double] = None,
                     tdop: Option[Double] = None, gdop: Option[Double] = None,
                     hdop: Option[Double] = None, pdop: Option[Double] = None,
                     satellites: List[SATObject] = List[SATObject]()) extends TypeObject

case class TPVObject(tag: Option[String] = None, device: Option[String] = None,
                     time: Option[String] = None, mode: Option[Int] = None, epc: Option[Double] = None,
                     ept: Option[Double] = None, epx: Option[Double] = None,epy: Option[Double] = None,
                     epv: Option[Double] = None, epd: Option[Double] = None,eps: Option[Double] = None,
                     lat: Option[Double] = None, lon: Option[Double] = None, alt: Option[Double] = None,
                     climb: Option[Double] = None, speed: Option[Double] = None, track: Option[Double] = None) extends TypeObject

case class ErrorObject(message: Option[String] = None) extends TypeObject

//------------------------------------------------------------------------
//----------------------------- COMMANDS ---------------------------------
//------------------------------------------------------------------------

case class DeviceObject(path: Option[String] = None, activated: Option[String] = None,
                        flags: Option[Int] = None, driver: Option[String] = None,
                        subtype: Option[String] = None, bps: Option[Int] = None,
                        parity: Option[String] = None, stopbits: Option[Int] = None,
                        native: Option[Int] = None, cycle: Option[Double] = None,
                        mincycle: Option[Double] = None) extends TypeObject

case class DevicesObject(devices: List[DeviceObject] = List[DeviceObject](),
                         remote: Option[String] = None) extends TypeObject

case class VersionObject(release: Option[String] = None, rev: Option[String] = None,
                         proto_major: Option[Double] = None, proto_minor: Option[Double] = None,
                         remote: Option[String] = None) extends TypeObject

// Note: currently only json format is supported, hence the default true
case class WatchObject(enable: Option[Boolean] = Some(true), json: Option[Boolean] = Some(true),
                       nmea: Option[Boolean] = Some(false), raw: Option[Int] = None,
                       scaled: Option[Boolean] = None, timing: Option[Boolean] = None,
                       device: Option[String] = None, remote: Option[String] = None) extends TypeObject

case class PollObject(time: Option[String] = None, active: Option[Int] = None,
                      tpv: List[TPVObject] = List[TPVObject](),
                      gst: List[GSTObject] = List[GSTObject](),
                      sky: List[SKYObject] = List[SKYObject]()) extends TypeObject

case class PPSObject(device: Option[String] = None, real_sec: Option[Double] = None,
                     real_musec: Option[Double] = None, clock_sec: Option[Double] = None,
                     clock_musec: Option[Double] = None) extends TypeObject

//------------------------------------------------------------------------
//----------------------------- SUBFRAME ---------------------------------
//------------------------------------------------------------------------

case class ALMANACObject(ID: Option[Int] = None, Health: Option[Int] = None,
                         e: Option[Double] = None, toa: Option[Int] = None,
                         deltai: Option[Double] = None, Omegad: Option[Double] = None,
                         sqrtA: Option[Double] = None, Omega0: Option[Double] = None,
                         omega: Option[Double] = None, M0: Option[Double] = None,
                         af0: Option[Double] = None, af1: Option[Double] = None) extends TypeObject

case class EPHEM1Object(WN: Option[Int] = None, IODC: Option[Int] = None,
                        L2: Option[Double] = None, ura: Option[Double] = None,
                        hlth: Option[Double] = None, L2P: Option[Int] = None,
                        Tgd: Option[Double] = None, toc: Option[Int] = None,
                        af2: Option[Double] = None, af1: Option[Double] = None,
                        af0: Option[Double] = None) extends TypeObject

case class EPHEM2Object(IODE: Option[Int] = None, Crs: Option[Double] = None,
                        deltan: Option[Double] = None, M0: Option[Double] = None,
                        Cuc: Option[Double] = None, e: Option[Int] = None,
                        Cus: Option[Double] = None, sqrtA: Option[Int] = None,
                        toe: Option[Int] = None, FIT: Option[Int] = None,
                        AODO: Option[Int] = None) extends TypeObject

case class EPHEM3Object(IODE: Option[Int] = None, IDOT: Option[Double] = None,
                        Cic: Option[Double] = None, Omega0: Option[Double] = None,
                        Cis: Option[Double] = None, i0: Option[Int] = None,
                        Crc: Option[Double] = None, omega: Option[Double] = None,
                        Omegad: Option[Double] = None) extends TypeObject

case class ERDObject(ERD: Option[Array[Int]] = Some(new Array[Int](30)), ai: Option[Int] = None) extends TypeObject

case class HEALTH2Object(SV: Option[Array[Int]] = Some(new Array[Int](24)), toa: Option[Int] = None,
                         WNa: Option[Int] = None) extends TypeObject

case class HEALTHObject(SV: Option[Array[Int]] = Some(new Array[Int](32)),
                        SVH: Option[Array[Int]] = Some(new Array[Int](8)),
                        data_id: Option[Int] = None) extends TypeObject

case class IONOObject(alpha0: Option[Double] = None, alpha1: Option[Double] = None,
                      alpha2: Option[Double] = None, alpha3: Option[Double] = None,
                      beta0: Option[Double] = None, beta1: Option[Double] = None,
                      beta2: Option[Double] = None, beta3: Option[Double] = None,
                      A0: Option[Double] = None, A1: Option[Double] = None,
                      tot: Option[Double] = None,
                      WNt: Option[Int] = None, leap: Option[Int] = None,
                      WNlsf: Option[Int] = None, DN: Option[Int] = None,
                      lsf: Option[Int] = None) extends TypeObject

case class SUBFRAMEObject(device: Option[String] = None,
                          tSV: Option[Int] = None, TOW17: Option[Int] = None,
                          frame: Option[Int] = None, scaled: Option[Boolean] = None,
                          pageid: Option[Int] = None, system_message: Option[String] = None,
                          almanac: Option[ALMANACObject] = None, ephem1: Option[EPHEM1Object] = None,
                          ephem2: Option[EPHEM2Object] = None, ephem3: Option[EPHEM3Object] = None,
                          erd: Option[ERDObject] = None, health: Option[HEALTHObject] = None,
                          health2: Option[HEALTH2Object] = None, iono: Option[IONOObject] = None) extends TypeObject
