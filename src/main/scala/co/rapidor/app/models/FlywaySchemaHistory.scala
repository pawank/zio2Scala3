package co.rapidor.app.models

import java.time.LocalDateTime

case class Version(version: String)

case class FlywaySchemaHistory(
    installedRank: Int,
    version: Option[String],
    description: String,
    `type`: String,
    script: String,
    checksum: Int,
    installedBy: String,
    installedOn: LocalDateTime,
    executionTime: Int,
    success: Boolean,
  )

object FlywaySchemaHistory {
  val examples = List(
    FlywaySchemaHistory(
      installedRank = 1,
      version = Some("123"),
      description = "Sample",
      `type` = "test",
      script = "",
      checksum = 1,
      installedBy = "admin",
      installedOn = LocalDateTime.now,
      executionTime = 2,
      success = true,
    ),
    FlywaySchemaHistory(
      installedRank = 2,
      version = Some("2"),
      description = "Sample",
      `type` = "test",
      script = "",
      checksum = 2,
      installedBy = "admin",
      installedOn = LocalDateTime.now,
      executionTime = 3,
      success = false,
    ),
  )

}
