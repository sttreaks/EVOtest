package converter.domain

import scalaj.http.Http

object ExchangeRateSource {
  val privatBankExchangeRateUrl: String = "https://api.privatbank.ua/p24api/exchange_rates?json"

  def getExchangeCourse(date: String) = Http(privatBankExchangeRateUrl)
    .postForm
    .header("content-type", "application/json")
    .param("date", date)
    .asString
    .body

}
