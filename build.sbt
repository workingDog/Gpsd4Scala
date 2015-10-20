organization := "kodekutters"

name := "gpsd4scala"

version := "0.3"

scalaVersion := "2.11.7"

scalacOptions += "-deprecation"

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-actor_2.11" % "2.4.0",
  "com.typesafe.play" % "play-json_2.11" % "2.4.3",
  "org.scala-lang" % "scala-xml" % "2.11.0-M4"
)

