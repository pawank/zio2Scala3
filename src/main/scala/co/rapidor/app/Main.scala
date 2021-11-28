package co.rapidor
package app

import com.tersesystems.blindsight._
import com.tersesystems.blindsight.DSL._

import zio.*

class Examples {
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

  override def run = {
    //import zio.logging._
    //import zio.logging.slf4j.bridge.initializeSlf4jBridge
    //val env = Logging.consoleErr() >>> initializeSlf4jBridge
    startLogback()
    val logger: Logger = LoggerFactory.getLogger
    logger.info("Starting the program")
    val example = new Examples
    val program = (for {
      //logger <- ZIO.from(startLogback())
      v <- example.testProtoQuill()
      _ <- {
        //Console.printLine(v)
        ZIO.from(logger.info(v.toString()))
      }
      _ <- ZIO.from(logger.info("Stopping the program"))
      stopLogger <- ZIO.from(stopLogback())
    } yield ()).exitCode
    //stopLogback()
    program
  }
