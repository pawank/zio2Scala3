package co.rapidor
package app

import zio._

object Main extends ZIOAppDefault {
  def testQuill() = {
    import io.getquill._
    val ctx = new PostgresJdbcContext(SnakeCase, "ctx")
    import ctx._
    case class Version(version: String)
    case class PgDatabase(oid: Int, datname: String)

    def pgVersionQ = quote {
      query[PgDatabase]
    }

    def selectVersionQ = quote {
      infix"""SELECT version()""".as[Query[String]]
    }

    def selectPg12VersionQ  = quote {
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
  //def testProtoQuill() = {
  //  ZIO.succeed(0)
  //}

  /*
  def testOrientDbQuill() = {
    import io.getquill._
    val ctx = new OrientDBSyncContext(Literal, "orientDbContext")
    import ctx._

    case class NameValue(id: Int, name: String, code: String)

    def selectQ = quote {
      query[NameValue].filter(c => c.id == 2)
    }

    val queryResponse1 = ctx.run(selectQ)
    val r = for {
      r1 <-
        ZIO.from({
          queryResponse1
        })
    } yield r1
    r
  }
  */

  override def run = {
    println("─" * 100)
    println("hello world")
    println("─" * 100)
    (for {
      v <- testQuill()
      //v <- testOrientDbQuill()
      _ <- {
        Console.printLine(v)
      }
    } yield ()).exitCode
  }
}
