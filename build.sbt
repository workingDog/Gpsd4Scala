organization := "kodekutters"

name := "gpsd4scala"

version := "0.3"

scalaVersion := "2.11.7"

scalacOptions += "-deprecation"

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-actor_2.11" % "2.4.0",
  "com.typesafe.play" % "play-json_2.11" % "2.4.3",
  "org.specs2" % "specs2_2.11" % "3.3.1",
  "junit" % "junit" % "4.12"
//  "com.typesafe.akka" % "akka-actor_2.10" % "2.3.0-RC4" % "compile",
//  "com.typesafe.play" % "play-json_2.10" % "2.2.2",
//  "org.specs2" %% "specs2" % "2.3.8" % "test",
//  "junit" % "junit" % "4.11" % "test"
)

