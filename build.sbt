import Dependencies._

enablePlugins(JavaAppPackaging)


ThisBuild / organization := "co.rapidor"
ThisBuild / scalaVersion := "2.13.7"
//scalaBinaryVersion in ThisBuild := "2.13"

maintainer := "pawan@rapidor.co"

run / fork := true

reStart / mainClass := Some("co.rapidor.app.Main")

nativeImageVersion := "20.3.4"

//nativeImageGraalHome := file("/Library/Java/JavaVirtualMachines/graalvm-ce-java11-21.1.0/Contents/Home").toPath

//crossScalaVersions := List("2.13.7", "2.12.12")

lazy val `zioplayground` =
  project
    .in(file("."))
    .enablePlugins(NativeImagePlugin)
    .settings(name := "ExampleScala3ZIO2")
    .settings(commonSettings)
    .settings(dependencies)
    .settings(
	//crossScalaVersions := Seq("2.13.7", "2.12.12"),
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


val quillVersion = "3.7.1"
val arcadeDbVersion = "21.10.2"

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
          //"com.arcadedb" % "arcadedb-postgresw" % arcadeDbVersion,
          //("com.arcadedb" % "arcadedb-postgresw" % arcadeDbVersion).cross(CrossVersion.for3Use2_13),
	  "org.postgresql"                 % "postgresql"               % "42.2.24"
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


