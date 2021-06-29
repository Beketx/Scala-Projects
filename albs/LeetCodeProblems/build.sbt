import com.typesafe.sbt.packager.docker.ExecCmd

enablePlugins(JavaAppPackaging, AshScriptPlugin)

name := "LeetCodeProblems"

version := "0.1"

scalaVersion := "2.13.3"

dockerBaseImage := "openjdk:8-jre-alpine"
packageName in Docker := "leetcodeproblems"

val circeVersion = "0.12.3"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

val AkkaVersion = "2.6.10"
val AkkaHttpVersion = "10.2.1"


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "de.heikoseeberger" %% "akka-http-circe" % "1.31.0",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
)

dockerCommands := dockerCommands.value.map {
  case ExecCmd("CMD", _ @ _*) =>
    ExecCmd("CMD", "/opt/docker/bin/leetcodeproblems")
  case other =>
    other
}