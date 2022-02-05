import Dependencies.Blindsight.blindsightLogbackStructuredConfig
import Dependencies._

enablePlugins(JavaAppPackaging)

ThisBuild / scalaVersion := "3.1.1"
ThisBuild / version := "0.0.1"
ThisBuild / organization := "co.rapidor"
ThisBuild / organizationName := "acelr"

ThisBuild / evictionErrorLevel := Level.Warn
ThisBuild / scalafixDependencies += Dependencies.organizeImports

maintainer := "pawan@rapidor.co"

//scalacOptions += "-source:3.0-migration"

run / fork := true

reStart / mainClass := Some("co.rapidor.app.Main")

nativeImageVersion := "20.3.4"

nativeImageOptions ++= List(
  "--initialize-at-build-time",
  "--verbose",
  "--no-server",
  "--no-fallback",
  "--static",
  "--install-exit-handlers",
  "--enable-http",
  "--enable-https",
  "--enable-all-security-services",
  "--libc=musl",
  "-H:+RemoveSaturatedTypeFlows",
  "-H:+ReportExceptionStackTraces",
  "-H:+PrintAOTCompilation",
  "-H:+PrintClassInitialization",
  "-H:+PrintFeatures",
  "-H:+PrintStaticTruffleBoundaries",
  "-H:+StackTrace",
  "-H:+TraceLoggingFeature",
  "-H:+ReportExceptionStackTraces",
  //"-H:+TraceClassInitialization",
  "--allow-incomplete-classpath",
  "--report-unsupported-elements-at-runtime",
)

//nativeImageGraalHome := file("/Library/Java/JavaVirtualMachines/graalvm-ce-java11-21.1.0/Contents/Home").toPath

lazy val `zioplayground` =
  project
    .in(file("."))
    .aggregate(logging, impl)
    .enablePlugins(NativeImagePlugin)
    .settings(name := "ExampleScala3ZIO2")
    .settings(commonSettings)
    .settings(dependencies)
    .settings(
      mainClass in (Compile, packageBin) := Some("co.rapidor.app.Main"),
      assembly / mainClass := Some("co.rapidor.app.Main"),
      assembly / assemblyJarName := s"${name.value}-${version.value}.jar",
      libraryDependencies := libraryDependencies
        .value
        .map(
          _ excludeAll (
            ExclusionRule(organization = "dev.zio", name = "zio-stacktracer_2.13"),
            ExclusionRule(organization = "dev.zio", name = "zio-logging_2.13"),
            ExclusionRule(organization = "dev.zio", name = "zio-streams_2.13"),
            ExclusionRule(organization = "dev.zio", name = "zio_2.13"),
            ExclusionRule(organization = "dev.zio", name = "izumi-reflect_2.13"),
            ExclusionRule(
              organization = "dev.zio",
              name = "izumi-reflect-thirdparty-boopickle-shaded_2.13",
            ),
            ExclusionRule(organization = "com.lihaoyi", name = "fansi_2.13"),
            ExclusionRule(organization = "com.lihaoyi", name = "pprint_2.13"),
            ExclusionRule(organization = "com.lihaoyi", name = "sourcecode_2.13"),
            ExclusionRule(
              organization = "org.scala-lang.modules",
              name = "scala-collection-compat_2.13",
            ),
          )
        ),
    )

lazy val dependencies = libraryDependencies ++= Dependencies.allDependencies

lazy val testDependencies =
  libraryDependencies ++= Dependencies.testDependencies

lazy val commonSettings = commonScalacOptions ++ Seq(
  update / evictionWarningOptions := EvictionWarningOptions.empty
)

lazy val commonScalacOptions = Seq(
  Compile / console / scalacOptions --= Seq(
    //"-Xignore-scala2-macros",
    "-Wunused:_",
    "-Xfatal-warnings",
  ),
  Test / console / scalacOptions :=
    (Compile / console / scalacOptions).value,
)

lazy val logging = (project in file("logging")).settings(
  libraryDependencies += blindsightLogbackStructuredConfig
)

lazy val impl = (project in file("impl"))
  .settings()
  .dependsOn(logging)

dependencyOverrides ++= Seq(
  "com.lihaoyi" %% "sourcecode" % "0.2.7"
)

ThisBuild / assemblyMergeStrategy := {
  case PathList("io", "netty", xs @ _*) => MergeStrategy.first
  case PathList("com", "fasterxml", xs @ _*) => MergeStrategy.first
  case PathList("org", "reactivestreams", xs @ _*) => MergeStrategy.first
  case PathList("io", "getquill", "quill-sql", xs @ _*) => MergeStrategy.first
  case PathList("ch", "qos", xs @ _*) => MergeStrategy.first
  case PathList("com", "outr", xs @ _*) => MergeStrategy.first
  case PathList("javax", "annotation", xs @ _*) => MergeStrategy.first
  case PathList("com", "sun", "activation", xs @ _*) => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "ExecutionInfo$.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "ExecutionInfo.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "ProtoStreamContext.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "Row$.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "Row.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "reflect.properties" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "io.netty.versions.properties" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "reflection-config.json" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "module-info.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "StaticLoggerBinder.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "StaticMDCBinder.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "ActivationDataFlavor.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "CommandInfo.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "CommandMap.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "CommandObject.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "DataContentHandler.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "DataHandlerDataSource.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "DataSource.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "DataSourceDataContentHandler.class" =>
    MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "FileDataSource.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "FileTypeMap.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "MailcapCommandMap.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "MimeTypeParameterList.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "MimeTypeParseException.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "MimetypesFileTypeMap.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "SecuritySupport$3.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "SecuritySupport$4.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "SecuritySupport$5.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "SecuritySupport.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "URLDataSource.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "UnsupportedDataTypeException.class" =>
    MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "ObjectDataContentHandler.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "UnsupportedDataTypeException.class" =>
    MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "LineTokenizer.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "DataHandlerDataSource.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "FileDataSource.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "FileTypeMap.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "MailcapCommandMap.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "MimeTypeParseException.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "SecuritySupport$1.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "SecuritySupport$2.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "DataContentHandlerFactory.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "DataHandler$1.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "DataHandler.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith "MimeType.class" => MergeStrategy.last
  case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
  case "application.conf" => MergeStrategy.concat
  case "unwanted.txt" => MergeStrategy.discard
  case x =>
    val oldStrategy = (ThisBuild / assemblyMergeStrategy).value
    oldStrategy(x)
}
