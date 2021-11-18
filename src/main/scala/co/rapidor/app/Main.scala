package co.rapidor
package app

import zio.*

object Main extends ZIOAppDefault:
  println("─" * 100)

  println("hello world")

  println("─" * 100)


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

  override def run = {
    (for {
      v <- testProtoQuill()
      _ <- {
        Console.printLine(v)
      }
    } yield ()).exitCode
  }
