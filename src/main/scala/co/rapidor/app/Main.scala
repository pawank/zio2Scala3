package co.rapidor
package app

import com.tersesystems.blindsight.*
import com.tersesystems.blindsight.DSL.*
import zio.*

object Examples {
  def testProtoQuill() = {

    import io.getquill._
    import io.getquill.Dsl.autoQuote
    import io.getquill.autoQuote
    val ctx = new PostgresJdbcContext(SnakeCase, "ctx")
    import ctx._
    case class Version(version:String)
    case class PgDatabase(oid: Int, datname: String)

    inline def pgVersionQ = quote {
      query[PgDatabase]
    }
    inline def selectVersionQ = quote{
      infix"""SELECT version()""".as[Query[String]]
    }
    inline def selectPg12VersionQ = quote{
      infix"""SELECT version() where version() like '%PostgreSQL 12.3%'""".as[Query[String]]
    }
    val queryResponse1: List[PgDatabase] = ctx.run(pgVersionQ)
    val queryResponse2 = ctx.run(selectVersionQ)
    val queryResponse3 = ctx.run(selectPg12VersionQ)
    val r = for {
      r1 <-
        ZIO.from({
          queryResponse1
        })
      r2 <-
        ZIO.from({
          queryResponse2
        })
      r3 <-
        ZIO.from({
          queryResponse3
        })
    } yield (r1, r2, r3)
    r
  }

  //https://tersesystems.github.io/blindsight/usage/index.html
  def testBlindsightLogging(logger: com.tersesystems.blindsight.Logger) = {
    val dayOfWeek = "Monday"
    val temp = 72
    val statement: Statement = st"It is ${dayOfWeek} and the temperature is ${temp} degrees."
    logger.info(statement)
    logger.info(st"Time since epoch is ${bobj("instant_tse" -> java.time.Instant.now.toEpochMilli)}")

    import com.tersesystems.blindsight._
    import com.tersesystems.blindsight.semantic._
    import eu.timepit.refined._
    import eu.timepit.refined.api.{RefType, Refined}
    import eu.timepit.refined.auto._
    import eu.timepit.refined.numeric._
    import eu.timepit.refined.boolean._
    import eu.timepit.refined.char._
    import eu.timepit.refined.collection._
    import eu.timepit.refined.generic._
    import eu.timepit.refined.string._
    implicit def stringToStatement[R]: ToStatement[Refined[String, R]] =
      ToStatement { str =>
        Statement().withMessage(str.value)
      }

    //val notEmptyLogger: SemanticLogger[String Refined NonEmpty] =
    //  logger.semantic[String Refined NonEmpty]
    //notEmptyLogger.info(refineMV[NonEmpty]("this is a statement"))
    // will not compile
    //notEmptyLogger.info(refineMV(""))
    type OnlyPositive = Int Refined Positive
    val positive = 10

    logger.withCondition(1 == 1).info("Only logs when condition is true")

    import com.tersesystems.blindsight.inspection.InspectionMacros._
    decorateVals(dval => logger.debug(s"${dval.name} = ${dval.value}")) {
      val a = 5
      val b = 15
      a + b
    }
  }
}

object Main extends ZIOAppDefault:
  println("─" * 100)
  println("Welcome...")
  println("─" * 100)

  private def loggerContext = {
    import ch.qos.logback.classic.LoggerContext
    org.slf4j.LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
  }

  def startLogback() = {
    // startLogback should run in main class static block or as first statement
    // you need this to ensure you initialize Logback **before** JUL logging.
    loggerContext
  }

  def stopLogback(): Unit = {
    // ideally stop explicitly for async loggers (there is also a shutdown hook just in case)
    // http://logback.qos.ch/manual/configuration.html#stopContext
    loggerContext.stop()
  }

  //override val runtime = Runtime.default.mapRuntimeConfig(_.copy(supervisor = ZMXSupervisor))
  //val diagnosticsLayer: ZLayer[ZEnv, Throwable, zio.zmx.diagnostics.Diagnostics] = Diagnostics.make("localhost", 1111)

  override def run = {
    startLogback()
    //More examples at: https://github.com/tersesystems/blindsight-starter/blob/main/src/main/scala/example/Runner.scala
    val logger: Logger = LoggerFactory.getLogger
    logger.info("Starting the program")
    /*
    import java.util.UUID
    import zio.logging._
    val CorrelationId: LogAnnotation[UUID] = LogAnnotation[UUID](
      name = "correlation-id",
      initialValue = UUID.randomUUID(),
      combine = (_, r) => r,
      render = _.toString
    )

    val DebugJsonLog: LogAnnotation[String] = LogAnnotation[String](
      name = "debug-json-log",
      initialValue = "",
      combine = (_, r) => r,
      render = _.toString
    )

    val logEnv: ZLayer[Console with Clock, Nothing, Logging] =
      Logging.console(
        logLevel = LogLevel.Debug,
        format = LogFormat.ColoredLogFormat((ctx, line) => s"${ctx(CorrelationId)} ${ctx(DebugJsonLog)} [$line]")
      ) >>>
        Logging.withRootLoggerName(s"UserServer")

    val logLayer: TaskLayer[Logging] = ZEnv.live >>> logEnv
    */

    val program = (for {
      //logger <- ZIO.from(startLogback())
      v <- Examples.testProtoQuill()
      _ <- {
        //Console.printLine(v)
        ZIO.from(logger.info(v.toString()))
      }
      _ <- ZIO.from(logger.info("Stopping the program"))
      stopLogger <- ZIO.from(stopLogback())
    } yield ()).provideCustom(Console.live ++ Clock.live).exitCode
    //stopLogback()
    program
  }
