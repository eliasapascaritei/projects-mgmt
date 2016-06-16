name := """project-app"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.6"

resolvers ++=  Seq(
  Resolver.sonatypeRepo("snapshots")
)

resolvers += "Rhinofly Internal Repository" at "http://maven-repository.rhinofly.net:8081/artifactory/libs-release-local"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "com.typesafe.play" %% "play-slick" % "0.8.1",
  "org.postgresql" % "postgresql" % "9.2-1002-jdbc4",
  "jp.t2v" %% "play2-auth"      % "0.12.0",
  "jp.t2v" %% "play2-auth-test" % "0.13.2" % "test",
  "com.typesafe.play.plugins" %% "play-plugins-mailer" % "2.3.1",
  "org.mindrot" % "jbcrypt" % "0.3m",
  "org.quartz-scheduler" % "quartz" % "2.1.3",
  "nl.rhinofly" %% "play-s3" % "5.0.2",
  "org.apache.commons" % "commons-io" % "1.3.2"
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)
