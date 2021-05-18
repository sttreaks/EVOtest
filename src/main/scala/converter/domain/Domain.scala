package converter.domain

object Domain {
  object OptionPicker extends upickle.AttributeTagged {

    override implicit def OptionWriter[A: Writer]: Writer[Option[A]] =
      implicitly[Writer[A]].comap[Option[A]] {
        case None => null.asInstanceOf[A]
        case Some(x) => x
      }

    override implicit def OptionReader[A: Reader]: Reader[Option[A]] =
      new Reader.Delegate[Any, Option[A]](implicitly[Reader[A]].map(Some(_))) {
        override def visitNull(index: Int) = None
      }

  }

  case class Inquirer(currency: String,
                      amount: Int,
                      date: String,
                     )

  case class Answer(currency: String,
                    amount: Double
                   )

  case class ExchangeRateInfo(date: String,
                              bank: String,
                              baseCurrency: Int,
                              baseCurrencyLit: String,
                              exchangeRate: List[ExchangeRate]
                             )

  case class ExchangeRate(baseCurrency: String,
                          currency: String,
                          saleRateNB: Double,
                          purchaseRateNB: Double,
                          saleRate: Option[Double] = None,
                          purchaseRate: Option[Double] = None
                         )

  object ExchangeRateInfo {
    implicit val rw: OptionPicker.ReadWriter[ExchangeRateInfo] = OptionPicker.macroRW

    def readRaw(s: String) = OptionPicker.read[ExchangeRateInfo](s)
  }

  object ExchangeRate {
    implicit val rw: OptionPicker.ReadWriter[ExchangeRate] = OptionPicker.macroRW
  }

  object Answer {
    implicit val rw: OptionPicker.ReadWriter[Answer] = OptionPicker.macroRW

    def writeRaw(a: Answer) = OptionPicker.write[Answer](a)
  }

  object Inquirer {
    implicit val rw: OptionPicker.ReadWriter[Answer] = OptionPicker.macroRW
  }
}
