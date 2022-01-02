package co.rapidor.app.graphql

import scala.language.postfixOps
import zio.*
import zio.json.*
import co.rapidor.app.models.{Version, FlywaySchemaHistory}
import co.rapidor.app.services.DatabaseHistoryService

import caliban.GraphQL
import caliban.GraphQL.graphQL
import caliban.RootResolver
import caliban.schema.Annotations.{ GQLDeprecated, GQLDescription }
import caliban.schema.{GenericSchema, Schema}
import caliban.wrappers.ApolloTracing.apolloTracing
import caliban.wrappers.Wrappers.*

//object schema extends GenericSchema[DatabaseHistoryService]
//import schema.*

object QueriesApi {
  case class Queries(databaseHistoryList: RIO[DatabaseHistoryService, List[FlywaySchemaHistory]],
                     version: Version => RIO[DatabaseHistoryService, Option[FlywaySchemaHistory]])

  implicit val queriesSchema: Schema[DatabaseHistoryService, Queries] = Schema.gen

  val api: GraphQL[Console with Clock with DatabaseHistoryService] =
    graphQL(
      RootResolver(
        Queries(DatabaseHistoryService.getDatabaseHistoryList, args => DatabaseHistoryService.getDatabaseHistory(args.version))
      )
    ) @@
      maxFields(200) @@               // query analyzer that limit query fields
      maxDepth(30)
}
