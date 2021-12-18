name := "akka-http-handle-exceptions-example"

version := "0.1"

scalaVersion := "2.12.12"

val akkaVersion = "2.5.20"
val akkaHttpVersion = "10.1.7"

libraryDependencies ++= Seq(
  // akka stream
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  // akka http
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
)