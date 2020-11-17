enablePlugins(JavaAppPackaging, AshScriptPlugin)

dockerBaseImage := "openjdk:14-jre-alpine"
dockerRepository := Some("192.168.1.132:2376")
packageName in Docker := "akka-http-quickstart"

name := "service_3"

version := "0.1"

scalaVersion := "2.13.3"

val AkkaVersion = "2.6.10"
val AkkaHttpVersion = "10.2.1"
val circeVersion = "0.13.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream-kafka" % "2.0.5",
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "org.slf4j" % "slf4j-jdk14" % "1.7.30",
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "de.heikoseeberger" %% "akka-http-circe" % "1.31.0"
)