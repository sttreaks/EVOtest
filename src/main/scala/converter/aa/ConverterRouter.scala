package converter.aa

import akka.actor.typed.scaladsl.AskPattern._
import akka.actor.typed.{ActorRef, ActorSystem}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import converter.aa.ConverterActor.RequestState
import converter.domain.Domain.Answer

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration.DurationInt

class ConverterRouter(service: ActorRef[ConverterActor.Command])(implicit val system: ActorSystem[_]) {

  /** for ask pattern */
  private implicit val timeout = Timeout(5.second)
  private implicit val ec: ExecutionContextExecutor = system.executionContext

  private def obtainStateFromActor(from: String, to: String, amount: String, date: String) = service
    .ask(me => RequestState(me, from, to, amount, date))
    .map(r => Answer.writeRaw(r))

  val routes: Route =
    parameters("from", "to", "amount", "date") { (from, to, amount, date) =>
      get {
        complete(obtainStateFromActor(from, to, amount, date))
      }
    }
}
