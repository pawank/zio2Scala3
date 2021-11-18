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


val quillVersion = "3.10.0.Beta1.6"

lazy val dependencies = Seq(
  libraryDependencies ++= Seq(
	  // main dependencies
	  "dev.zio" %% "zio" % "2.0.0-M5",
	  "io.getquill" %% "quill-sql" % quillVersion,
	  // Syncronous JDBC Modules
	  "io.getquill" %% "quill-jdbc" % quillVersion,
	  // Or ZIO Modules
	  "io.getquill" %% "quill-jdbc-zio" % quillVersion,
	  // Postgres Async
	  "io.getquill" %% "quill-jasync-postgres" % quillVersion,
	  "org.postgresql"                 % "postgresql"               % "42.2.8"
  ),
  libraryDependencies ++= Seq(
    org.scalatest.scalatest,
    org.scalatestplus.`scalacheck-1-15`,
  ).map(_ % Test)
)

lazy val dbLibs = Seq(
	"io.getquill" %% "quill-sql" % quillVersion,
	// Syncronous JDBC Modules
	"io.getquill" %% "quill-jdbc" % quillVersion,
	// Or ZIO Modules
	"io.getquill" %% "quill-jdbc-zio" % quillVersion,
	// Postgres Async
	"io.getquill" %% "quill-jasync-postgres" % quillVersion,
	"org.postgresql"                 % "postgresql"               % "42.2.8"
)


