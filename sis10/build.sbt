enablePlugins(JavaAppPackaging, AshScriptPlugin)
name := "akka-typed1"

version := "0.1"

scalaVersion := "2.13.4"


val akkaVersion = "2.6.10"
val akkaHttpVersion = "10.2.1"
val circeVersion = "0.13.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed"           % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-typed"         % akkaVersion,
  "com.typesafe.akka" %% "akka-serialization-jackson" % akkaVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,

  "ch.qos.logback"    %  "logback-classic"             % "1.2.3",
  "com.typesafe.akka" %% "akka-multi-node-testkit"    % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-testkit-typed"   % akkaVersion,

  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,


  "de.heikoseeberger" %% "akka-http-circe" % "1.31.0"
)
enablePlugins(DockerPlugin)

mainClass in Compile := Some("Boot")
dockerBaseImage := "java:8-jre-alpine"
version in Docker := "latest"
dockerExposedPorts := Seq(8000)
dockerRepository := Some("sis103")