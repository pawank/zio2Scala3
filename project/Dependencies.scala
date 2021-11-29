import sbt._

object Dependencies {
  
  val blindsight = "1.5.2"
  val terseLogback = "1.0.1"
  val blacklite = "1.0.1"
  val quillVersion = "3.10.0.Beta1.6"
  val zioHttpVersion    = "1.0.0.0-RC17"


  // Basic Logback
  val logbackClassic = "ch.qos.logback" % "logback-classic" % "1.2.3"
  val logstashLogbackEncoder = "net.logstash.logback" % "logstash-logback-encoder" % "6.6"
  val janino = "org.codehaus.janino" % "janino" % "3.0.11"
  val jansi  = "org.fusesource.jansi" % "jansi" % "1.17.1"
  val julToSlf4j = "org.slf4j" % "jul-to-slf4j" % "1.7.30"

  // https://github.com/tersesystems/blacklite#logback
  val blackliteLogback = "com.tersesystems.blacklite" % "blacklite-logback" % blacklite
  // https://github.com/tersesystems/blacklite#codec
  //val blackliteZtdCodec = "com.tersesystems.blacklite" % "blacklite-codec-zstd" % blacklite

  // https://tersesystems.github.io/terse-logback/
  val terseLogbackClassic = "com.tersesystems.logback" % "logback-classic" % terseLogback
  val logbackUniqueId = "com.tersesystems.logback" % "logback-uniqueid-appender" % terseLogback

  // https://tersesystems.github.io/blindsight/setup/index.html
  val blindsightLogstash = "com.tersesystems.blindsight" %% "blindsight-logstash" % blindsight
  // https://tersesystems.github.io/blindsight/usage/inspections.html
  val blindsightInspection = "com.tersesystems.blindsight" %% "blindsight-inspection" % blindsight
  // https://tersesystems.github.io/blindsight/usage/scripting.html
  val blindsightScripting = "com.tersesystems.blindsight" %% "blindsight-scripting" % blindsight

  val blindsightGenric = "com.tersesystems.blindsight" %% "blindsight-generic" % blindsight
  val blindsightJsonld = "com.tersesystems.blindsight" %% "blindsight-jsonld" % blindsight
  val blindsightRingbuffer = "com.tersesystems.blindsight" %% "blindsight-ringbuffer" % blindsight
  val blindsightLogbackStructuredConfig = "com.tersesystems.logback" % "logback-structured-config" % terseLogback

  val postgresql = "org.postgresql"                 % "postgresql"               % "42.2.8"
  val quillSql =  "io.getquill" %% "quill-sql" % quillVersion
  val quillJdbc = "io.getquill" %% "quill-jdbc" % quillVersion
  val quillZio = "io.getquill" %% "quill-jdbc-zio" % quillVersion
  val quillPostgresAsync =  "io.getquill" %% "quill-jasync-postgres" % quillVersion

  val refined = "eu.timepit" %% "refined"                 % "0.9.27"
  val refinedScalaz = "eu.timepit" %% "refined-scalaz"          % "0.9.27"
  val refinedScodec = "eu.timepit" %% "refined-scodec"          % "0.9.27"

  val zioHttp = "io.d11"                        %% "zhttp"                    % zioHttpVersion

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
