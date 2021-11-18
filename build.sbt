import Dependencies._

enablePlugins(JavaAppPackaging)


ThisBuild / organization := "co.rapidor"
ThisBuild / scalaVersion := "3.1.0"

maintainer := "pawan@rapidor.co"

run / fork := true

reStart / mainClass := Some("co.rapidor.app.Main")

nativeImageVersion := "20.3.4"

//nativeImageGraalHome := file("/Library/Java/JavaVirtualMachines/graalvm-ce-java11-21.1.0/Contents/Home").toPath

lazy val `zioplayground` =
  project
    .in(file("."))
    .enablePlugins(NativeImagePlugin)
    .settings(name := "ExampleScala3ZIO2")
    .settings(commonSettings)
    .settings(dependencies)
    .settings(
	mainClass in (Compile, packageBin) := Some("co.rapidor.app.Main"),
    	assembly / mainClass := Some("co.rapidor.app.Main"),
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
	  "dev.zio" %% "zio" % "2.0.0-M5",
	  "io.getquill" %% "quill-sql" % "3.10.0.Beta1.6",
	  // Syncronous JDBC Modules
	  "io.getquill" %% "quill-jdbc" % "3.10.0.Beta1.6",
	  // Or ZIO Modules
	  "io.getquill" %% "quill-jdbc-zio" % "3.10.0.Beta1.6",
	  // Postgres Async
	  "io.getquill" %% "quill-jasync-postgres" % "3.10.0.Beta1.6",
	  "org.postgresql"                 % "postgresql"               % "42.2.8"
  ),
  libraryDependencies ++= Seq(
    org.scalatest.scalatest,
    org.scalatestplus.`scalacheck-1-15`,
  ).map(_ % Test)
)

lazy val dbLibs = Seq(
	"io.getquill" %% "quill-sql" % "3.10.0.Beta1.6",
	// Syncronous JDBC Modules
	"io.getquill" %% "quill-jdbc" % "3.10.0.Beta1.6",
	// Or ZIO Modules
	"io.getquill" %% "quill-jdbc-zio" % "3.10.0.Beta1.6",
	// Postgres Async
	"io.getquill" %% "quill-jasync-postgres" % "3.10.0.Beta1.6",
	"org.postgresql"                 % "postgresql"               % "42.2.8"
)


