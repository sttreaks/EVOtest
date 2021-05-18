package converter.domain

import converter.domain.Domain.{Answer, ExchangeRate, ExchangeRateInfo}
import scala.util.Try

object Converter {
  def findRate(currency: String, exchangeRates: ExchangeRateInfo): ExchangeRate = {
    exchangeRates.exchangeRate.filter(x => x.currency == currency).head
  }

  def getPurchaseRate(currency: String, exchangeRates: ExchangeRateInfo): Option[Double] = {
    val exchangeRate: Option[ExchangeRate] = Try(exchangeRates.exchangeRate.filter(x => x.currency == currency).head).toOption

    (currency, exchangeRate) match {
      case ("UAH", _) => Option(1)
      case (_, Some(exchangeRate)) => Option(exchangeRate.purchaseRateNB)
      case (_, None) => None
    }
  }

  def getSaleRate(currency: String, exchangeRates: ExchangeRateInfo): Option[Double] = {
    val exchangeRate: Option[ExchangeRate] = Try(exchangeRates.exchangeRate.filter(x => x.currency == currency).head).toOption

    (currency, exchangeRate) match {
      case ("UAH", _) => Option(1)
      case (_, Some(exchangeRate)) => Option(exchangeRate.saleRateNB)
      case (_, None) => None
    }
  }

  def convert(from: String, to: String, amount: Int, exchangeRates: ExchangeRateInfo): Option[Answer] = {
    val exchangeRateTo: Option[Double] = getSaleRate(to, exchangeRates)
    val exchangeRateFrom: Option[Double] = getPurchaseRate(from, exchangeRates)

    (exchangeRateFrom, exchangeRateTo) match {
      case (_, None) => None
      case (None, _) => None
      case (Some(exchangeRateFrom), Some(exchangeRateTo)) => Option(Answer(to, exchangeRateFrom * amount / exchangeRateTo))
    }
  }
}
