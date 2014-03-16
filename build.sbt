organization := "kodekutters"

name := "gpsd4scala"

version := "0.2"

scalaVersion := "2.10.4-RC3"

scalacOptions += "-deprecation"

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= List(
  "com.typesafe.akka" % "akka-actor_2.10" % "2.3.0-RC4" % "compile",
  "com.typesafe.play" % "play-json_2.10" % "2.2.2",
  "org.specs2" %% "specs2" % "2.3.8" % "test",
  "junit" % "junit" % "4.11" % "test"
)

