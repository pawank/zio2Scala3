import Dependencies.Blindsight.{
  blindsightGenric,
  blindsightInspection,
  blindsightJsonld,
  blindsightLogbackStructuredConfig,
  blindsightLogstash,
  blindsightRingbuffer,
  blindsightScripting,
}
import Dependencies.Caliban.{ caliban, calibanFederation, calibanTapir, calibanZioHttp }
import Dependencies.Quill.{ quillJdbc, quillPostgresAsync, quillSql, quillZio }
import Dependencies.Refined.refinedCore
import sbt._
import Keys._
import Versions._

object Dependencies {
  object Zio {
    val zio = "dev.zio" %% "zio" % zioVersion
    val zioPrelude = "dev.zio" %% "zio-prelude" % zioPreludeVersion
    val zioQuery = "dev.zio" %% "zio-query" % zioQueryVersion
    val zioInteropCats = "dev.zio" %% "zio-interop-cats" % zioInteropCatsVersion
    val zioStreams = "dev.zio" %% "zio-streams" % zioStreamsVersion
    val zioTest = "dev.zio" %% "zio-test" % zioVersion % "test"
    val zioTestSbt = "dev.zio" %% "zio-test-sbt" % zioVersion % "test"

  }

  // Scalafix rules
  val organizeImports = "com.github.liancheng" %% "organize-imports" % organizeImportsVersion

  def derevo(artifact: String): ModuleID = "tf.tofu" %% s"derevo-$artifact" % derevoVersion
  def circe(artifact: String): ModuleID = "io.circe" %% s"circe-$artifact" % circeVersion
  def http4s(artifact: String): ModuleID = "org.http4s" %% s"http4s-$artifact" % http4sVersion
  def cormorant(artifact: String): ModuleID =
    "io.chrisdavenport" %% s"cormorant-$artifact" % cormorantVersion

  object Misc {
    val newtype = "io.estatico" %% "newtype" % newtypeVersion
    val squants = "org.typelevel" %% "squants" % squantsVersion
    val fs2Core = "co.fs2" %% "fs2-core" % fs2Version
    val fs2IO = "co.fs2" %% "fs2-io" % fs2Version
    val pureconfig = "com.github.pureconfig" %% "pureconfig" % pureconfigVersion
    val flywayDb = "org.flywaydb" % "flyway-core" % flywayDbVersion

  }

  object Refined {
    val refinedCore = "eu.timepit" %% "refined" % refinedVersion
    val refinedCats = "eu.timepit" %% "refined-cats" % refinedVersion
    val refinedShapeless = "eu.timepit" %% "refined-shapeless" % refinedVersion
    val refinedScalaz = "eu.timepit" %% "refined-scalaz" % refinedVersion
    val refinedScodec = "eu.timepit" %% "refined-scodec" % refinedVersion

  }

  object Circe {
    val circeCore = circe("core")
    val circeGeneric = circe("generic")
    val circeParser = circe("parser")
    val circeRefined = circe("refined")

  }

  object Derevo {
    val derevoCore = derevo("core")
    val derevoCiris = derevo("ciris")
    val derevoCirce = derevo("circe")
    val derevoCirceMagnolia = derevo("circe-magnolia")

  }

  object Ciris {
    val cirisCore = "is.cir" %% "ciris" % cirisVersion
    val cirisEnum = "is.cir" %% "ciris-enumeratum" % cirisVersion
    val cirisRefined = "is.cir" %% "ciris-refined" % cirisVersion
    val cirisCirce = "is.cir" %% "ciris-circe" % cirisVersion
    val cirisSquants = "is.cir" %% "ciris-squants" % cirisVersion

  }

  object Doobie {
    val doobieCore = "org.tpolecat" %% "doobie-core" % doobieVersion
    val doobieH2 = "org.tpolecat" %% "doobie-h2" % doobieVersion
    val doobieHikari = "org.tpolecat" %% "doobie-hikari" % doobieVersion
    val doobiePostgres = "org.tpolecat" %% "doobie-postgres" % doobieVersion
    val doobieEnumeratum = "com.beachape" %% "enumeratum-doobie" % enumeratumDoobieVersion

  }

  object Quill {
    val quillSql = "io.getquill" %% "quill-sql" % quillVersion
    val quillJdbc = "io.getquill" %% "quill-jdbc" % quillVersion
    val quillZio = "io.getquill" %% "quill-jdbc-zio" % quillVersion
    val quillPostgresAsync = "io.getquill" %% "quill-jasync-postgres" % quillVersion
    val quillOrientdb = "io.getquill" %% "quill-orientdb" % quillVersion

  }

  object Caliban {
    val caliban = "com.github.ghostdogpr" %% "caliban" % calibanVersion
    val calibanZioHttp = "com.github.ghostdogpr" %% "caliban-zio-http" % calibanVersion
    val calibanTapir = "com.github.ghostdogpr" %% "caliban-tapir" % calibanVersion
    val calibanFederation = "com.github.ghostdogpr" %% "caliban-federation" % calibanVersion

  }

  object Blindsight {
    // https://tersesystems.github.io/blindsight/setup/index.html
    val blindsightLogstash =
      "com.tersesystems.blindsight" %% "blindsight-logstash" % blindsightVersion
    // https://tersesystems.github.io/blindsight/usage/inspections.html
    val blindsightInspection =
      "com.tersesystems.blindsight" %% "blindsight-inspection" % blindsightVersion
    // https://tersesystems.github.io/blindsight/usage/scripting.html
    val blindsightScripting =
      "com.tersesystems.blindsight" %% "blindsight-scripting" % blindsightVersion
    val blindsightGenric = "com.tersesystems.blindsight" %% "blindsight-generic" % blindsightVersion
    val blindsightJsonld = "com.tersesystems.blindsight" %% "blindsight-jsonld" % blindsightVersion
    val blindsightRingbuffer =
      "com.tersesystems.blindsight" %% "blindsight-ringbuffer" % blindsightVersion
    val blindsightLogbackStructuredConfig =
      "com.tersesystems.logback" % "logback-structured-config" % terseLogbackVersion

  }

  val jwtScala = "com.github.jwt-scala" %% "jwt-core" % "9.0.2"
  val zioHttp = "io.d11" %% "zhttp" % zioHttpVersion
  val postgresql = "org.postgresql" % "postgresql" % "42.2.8"
  // Runtime
  val logback = "ch.qos.logback" % "logback-classic" % logbackVersion % Runtime

  val pprint = "com.lihaoyi" %% "pprint" % "0.7.1"
  // Basic Logback
  val logbackClassic = "ch.qos.logback" % "logback-classic" % "1.2.3"
  val logstashLogbackEncoder = "net.logstash.logback" % "logstash-logback-encoder" % "6.6"
  val janino = "org.codehaus.janino" % "janino" % "3.0.11"
  val jansi = "org.fusesource.jansi" % "jansi" % "1.17.1"
  val julToSlf4j = "org.slf4j" % "jul-to-slf4j" % "1.7.30"

  // https://github.com/tersesystems/blacklite#logback
  val blackliteLogback = "com.tersesystems.blacklite" % "blacklite-logback" % blackliteVersion
  // https://github.com/tersesystems/blacklite#codec
  //val blackliteZtdCodec = "com.tersesystems.blacklite" % "blacklite-codec-zstd" % blacklite
  // https://tersesystems.github.io/terse-logback/
  val terseLogbackClassic = "com.tersesystems.logback" % "logback-classic" % terseLogbackVersion
  val logbackUniqueId =
    "com.tersesystems.logback" % "logback-uniqueid-appender" % terseLogbackVersion

  object CompilerPlugin {
    val betterMonadicFor = compilerPlugin(
      "com.olegpy" %% "better-monadic-for" % betterMonadicForVersion
    )
    val kindProjector = compilerPlugin(
      "org.typelevel" %% "kind-projector" % kindProjectorVersion cross CrossVersion.full
    )
    val semanticDB = compilerPlugin(
      "org.scalameta" % "semanticdb-scalac" % semanticDBVersion cross CrossVersion.full
    )

  }

  import CompilerPlugin._

  val commonDependencies: Seq[ModuleID] =
    Seq(Zio.zio, Zio.zioPrelude, Zio.zioInteropCats, Zio.zioStreams, Zio.zioQuery)

  lazy val dependencies = Seq(
    libraryDependencies ++= Seq(
      // main dependencies
      "dev.zio" %% "zio" % zioVersion,
      "dev.zio" %% "zio-json" % zioJsonVersion,
      //"dev.zio"                       % "zio-json_2.13"                 % zioJsonVersion,
      "dev.zio" %% "zio-logging" % zioLoggingVersion,
      "dev.zio" %% "zio-logging-slf4j" % zioLoggingVersion,
      //"dev.zio" %% "zio-zmx" % zioZmxVersion,
      //"dev.zio" % "zio-zmx_2.13" % zioZmxVersion,
      //"dev.zio" %% "zio-logging-slf4j-bridge" % zioLoggingVersion,
      "org.scala-lang.modules" %% "scala-collection-compat" % "2.6.0",
      "com.lihaoyi" %% "sourcecode" % "0.2.7",
    ),
    libraryDependencies += jwtScala,
    libraryDependencies += zioHttp,
    libraryDependencies += refinedCore,
    //libraryDependencies += refinedScalaz,
    //libraryDependencies += refinedScodec,
    libraryDependencies += postgresql,
    libraryDependencies += quillSql,
    libraryDependencies += quillJdbc,
    libraryDependencies += quillZio,
    libraryDependencies += quillPostgresAsync,
    //libraryDependencies += quillOrientdb,
    //libraryDependencies += ,
    // internal jdk libraries use java util logging
    libraryDependencies += julToSlf4j,

    // Basic logback
    libraryDependencies += logbackClassic,
    libraryDependencies += logstashLogbackEncoder,
    libraryDependencies += janino,
    libraryDependencies += jansi,
    libraryDependencies += pprint,
    libraryDependencies += caliban,
    libraryDependencies += calibanZioHttp,
    libraryDependencies += calibanTapir,
    libraryDependencies += calibanFederation,

    // sqlite appender
    libraryDependencies += blackliteLogback,

    // terse-logback utilities
    libraryDependencies += terseLogbackClassic,
    libraryDependencies += logbackUniqueId,

    // Blindsight API and logstash-logback-encoder integration
    libraryDependencies += blindsightLogstash,
    libraryDependencies += blindsightInspection,
    libraryDependencies += blindsightScripting,
    libraryDependencies += blindsightGenric,
    libraryDependencies += blindsightJsonld,
    libraryDependencies += blindsightRingbuffer,
    libraryDependencies += blindsightLogbackStructuredConfig,
    libraryDependencies ++= Seq(
      org.scalatest.scalatest,
      org.scalatestplus.`scalacheck-1-15`,
    ).map(_ % Test),
    libraryDependencies := libraryDependencies
      .value
      .map(
        _ excludeAll (
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

  val tradeioDependencies: Seq[ModuleID] =
    commonDependencies ++ Seq(kindProjector, betterMonadicFor, semanticDB) ++
      Seq(Misc.newtype, Misc.squants, Misc.pureconfig) ++ Seq(logback) ++ Seq(Misc.flywayDb) ++
      Seq(Derevo.derevoCore, Derevo.derevoCiris, Derevo.derevoCirceMagnolia) ++
      Seq(Refined.refinedCore, Refined.refinedCats, Refined.refinedShapeless) ++
      Seq(
        Ciris.cirisCore,
        Ciris.cirisEnum,
        Ciris.cirisRefined,
        Ciris.cirisCirce,
        Ciris.cirisSquants,
      ) ++
      Seq(Circe.circeCore, Circe.circeGeneric, Circe.circeParser, Circe.circeRefined) ++
      Seq(
        Doobie.doobieCore,
        Doobie.doobieHikari,
        Doobie.doobiePostgres,
        Doobie.doobieH2,
        Doobie.doobieEnumeratum,
      )

  val testDependencies: Seq[ModuleID] =
    Seq(Zio.zioTest, Zio.zioTestSbt)

  //val quillSql =  "io.getquill" % "quill-sql_2.13" % quillVersion
  //val quillJdbc = "io.getquill" % "quill-jdbc_2.13" % quillVersion
  //val quillZio = "io.getquill" % "quill-jdbc-zio_2.13" % quillVersion
  //val quillPostgresAsync =  "io.getquill" % "quill-jasync-postgres_2.13" % quillVersion
  //val quillOrientdb = "io.getquill" % "quill-orientdb_2.13" % quillVersion

  case object org {
    case object scalatest {
      val scalatest =
        "org.scalatest" %% "scalatest" % "3.2.9"

    }

    case object scalatestplus {
      val `scalacheck-1-15` =
        "org.scalatestplus" %% "scalacheck-1-15" % "3.2.9.0"

    }

  }

}
