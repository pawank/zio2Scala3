package co.rapidor.domain.config

import com.typesafe.config.Config
import zio._
import zio.config._
import zio.config.typesafe._
import zio.config.magnolia._

import java.io.File

object Configuration {
  case class MigrationConfig(jdbcUrl: String, user: String, password: String)
  case class AppConfig(migration: MigrationConfig)

  implicit val appConfigDescriptor: ConfigDescriptor[AppConfig] = descriptor[AppConfig].mapKey(toKebabCase)
}
