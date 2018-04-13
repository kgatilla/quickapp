package payments.util;

import org.joda.money.CurrencyUnit;
import org.joda.money.IllegalCurrencyException;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class MoneyBuilder {

    private static Logger log = LoggerFactory.getLogger(MoneyBuilder.class);

    public static Optional<Money> of(double amount, String currencyISOCode){
        Optional<CurrencyUnit> currency = of(currencyISOCode);
        return currency.map(c-> Money.of(c,amount));
    }

    public static Optional<CurrencyUnit> of(String currencyISOCode) {
        Optional<CurrencyUnit> currencyUnit;
        try {
            currencyUnit = Optional.of(CurrencyUnit.of(currencyISOCode));
        } catch (IllegalCurrencyException ex) {
            log.error("Currency exception:", ex.toString());
            ex.printStackTrace();
            currencyUnit = Optional.empty();
        }

        return currencyUnit;
    }


}
