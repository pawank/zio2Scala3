import Dependencies._

enablePlugins(JavaAppPackaging)


ThisBuild / organization := "com.estebanmarin"
ThisBuild / scalaVersion := "3.1.0"

maintainer := "pawan@rapidor.co"

run / fork := true

reStart / mainClass := Some("com.estebanmarin.zioplayground.Main")

nativeImageVersion := "20.3.4"

lazy val `zioplayground` =
  project
    .in(file("."))
    .enablePlugins(NativeImagePlugin)
    .settings(name := "zioplayground")
    .settings(commonSettings)
    .settings(dependencies)
    .settings(
	mainClass in (Compile, packageBin) := Some("com.estebanmarin.zioplayground.Main"),
    	assembly / mainClass := Some("com.estebanmarin.zioplayground.Main"),
    	assembly / assemblyJarName := s"${name.value}-${version.value}.jar")


lazy val commonSettings = commonScalacOptions ++ Seq(
  update / evictionWarningOptions := EvictionWarningOptions.empty
)

lazy val commonScalacOptions = Seq(
  Compile / console / scalacOptions --= Seq(
    "-Wunused:_",
    "-Xfatal-warnings",
  ),
  Test / console / scalacOptions :=
    (Compile / console / scalacOptions).value,
)

lazy val dependencies = Seq(
  libraryDependencies ++= Seq(
    // main dependencies
    "dev.zio" %% "zio" % "2.0.0-M5"
  ),
  libraryDependencies ++= Seq(
    org.scalatest.scalatest,
    org.scalatestplus.`scalacheck-1-15`,
  ).map(_ % Test)
)
