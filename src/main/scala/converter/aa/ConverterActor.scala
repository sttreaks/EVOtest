package converter.aa

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import converter.domain.Domain.{Answer, ExchangeRateInfo}
import converter.domain.{ExchangeRateSource, Converter}

object ConverterActor {

  sealed trait Command
  case class RequestState(sender: ActorRef[Answer], from: String, to: String, amount: String, date: String) extends Command

  def apply()(implicit system: ActorSystem[_]): Behavior[Command] =
    Behaviors.receiveMessage {
      case RequestState(sender, from, to, amount, date) =>
        system.log.info("> Got state request (http)")
        val exchangeRates: ExchangeRateInfo = ExchangeRateInfo.readRaw(ExchangeRateSource.getExchangeCourse(date))
        val ans: Option[Answer] = Converter.convert(from, to, amount.toInt, exchangeRates)
        ans match {
          case Some(value) => sender ! value
          case None => throw new Error()
        }

        Behaviors.same
    }
}
