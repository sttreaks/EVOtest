package converter

import akka.actor.typed.{ActorSystem, SpawnProtocol}
import akka.http.scaladsl.Http.ServerBinding
import converter.aa.{ConverterActor, ConverterRouter, HttpServer}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

/**
 * http://localhost:8080/converter
 */
object AppStreamingAndHttp extends App {
  /** system setup */
  implicit val as: ActorSystem[SpawnProtocol.Command] = ActorSystem(SpawnProtocol(), "converter-app")
  implicit val ec: ExecutionContext = as.executionContext

  /** collector actor */
  val collectorRef = as.systemActorOf(ConverterActor(), "converter-actor")

  val router = new ConverterRouter(collectorRef)
  val server = HttpServer.start(router.routes)

  server.onComplete {
    case Success(ServerBinding(la)) =>
      as.log.info("Server started at http://{}:{}/converter", la.getHostString, la.getPort)
    case Failure(ex) =>
      as.log.error("Server failed to start", ex)
      as.terminate()
  }

}