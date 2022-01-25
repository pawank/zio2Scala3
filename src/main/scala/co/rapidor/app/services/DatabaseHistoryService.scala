package co.rapidor.app.services

import zio.*
import co.rapidor.app.models.{ FlywaySchemaHistory, Version }

trait DatabaseHistoryService {
  def getDatabaseHistoryList: Task[List[FlywaySchemaHistory]]
  def getDatabaseHistory(version: String): Task[Option[FlywaySchemaHistory]]

}

object DatabaseHistoryService {
  def getDatabaseHistoryList: RIO[DatabaseHistoryService, List[FlywaySchemaHistory]] =
    ZIO.serviceWithZIO(_.getDatabaseHistoryList)
  def getDatabaseHistory(
      version: String
    ): RIO[DatabaseHistoryService, Option[FlywaySchemaHistory]] =
    ZIO.serviceWithZIO(_.getDatabaseHistory(version))

}

case class DatabaseHistoryServiceLive(console: Console) extends DatabaseHistoryService {
  override def getDatabaseHistoryList: Task[List[FlywaySchemaHistory]] =
    for {
      data <- ZIO.succeed(FlywaySchemaHistory.examples)
      _ <- console.printLine(data)
    } yield data
  override def getDatabaseHistory(version: String): Task[Option[FlywaySchemaHistory]] =
    for {
      data <- ZIO.succeed(FlywaySchemaHistory.examples.headOption)
      _ <- console.printLine(data)
    } yield data

}

object DatabaseHistoryServiceLive {
  val layer: URLayer[Console, DatabaseHistoryService] =
    (DatabaseHistoryServiceLive(_)).toLayer[DatabaseHistoryService]

}
