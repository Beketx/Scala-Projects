name := "Akka_hello"

version := "0.1"

scalaVersion := "2.13.3"

val AkkaVersion = "2.6.8"

libraryDependencies ++=  Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "org.scalatest" % "scalatest_2.13" % "3.2.0" % "test",
  "com.typesafe.akka" %% "akka-slf4j"       % AkkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.slf4j" % "slf4j-jdk14" % "1.7.30" % Test,
  "com.typesafe.akka"         %%  "akka-persistence"                    % AkkaVersion,
  "com.typesafe.akka"         %%  "akka-persistence-query-experimental" % AkkaVersion,
)
