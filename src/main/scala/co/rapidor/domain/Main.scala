package co.rapidor
import java.nio.file.Paths
import zio.*
import zio.stream.*

import caliban.*
import zhttp.http.*
import zhttp.service.Server

import zio.logging.*

import co.rapidor.services.DatabaseHistoryServiceLive
import co.rapidor.services.*
import co.rapidor.graphql.*

object Examples {


  def testProtoQuill() = {

    import io.getquill._
    import io.getquill.Dsl.autoQuote
    import io.getquill.autoQuote
    val ctx = new PostgresJdbcContext(SnakeCase, "ctx")
    import ctx._
    case class PgVersion(version: String)
    case class PgDatabase(oid: Int, datname: String)

    inline def pgVersionQ = quote {
      query[PgDatabase]
    }
    inline def selectVersionQ = quote {
      infix"""SELECT version()""".as[Query[String]]
    }
    inline def selectPg12VersionQ = quote {
      infix"""SELECT version() where version() like '%PostgreSQL 12.3%'""".as[Query[String]]
    }
    val queryResponse1: List[PgDatabase] = ctx.run(pgVersionQ)
    val queryResponse2 = ctx.run(selectVersionQ)
    val queryResponse3 = ctx.run(selectPg12VersionQ)
    val r = for {
      r1 <-
        ZIO.from {
          queryResponse1
        }
      r2 <-
        ZIO.from {
          queryResponse2
        }
      r3 <-
        ZIO.from {
          queryResponse3
        }
    } yield (r1, r2, r3)
    r
  }
}

object Main extends ZIOAppDefault:
  override def run = {
    val program = (for {
      _ <- ZIO.log("Starting the program")
      v <- Examples.testProtoQuill()
      _ <-
        ZIO.logDebug(v.toString())
      _ <- ZIO.log("Stopping the program")
    } yield ())
      .provideCustom(Console.live ++ Clock.live ++ DatabaseHistoryServiceLive.layer)
      .exitCode
    program
  }
