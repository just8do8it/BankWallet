package com.wallet.bankwallet.service;

import com.wallet.bankwallet.exception.CurrencyConversionException;
import com.wallet.bankwallet.model.Currency;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Objects;

@Service
public class ExchangeRatesClient {

    private final String baseUrl;
    private final String accessKey;
    private final RestTemplate restTemplate;

    public ExchangeRatesClient(
            @Value("${exchangerates.api.base-url}") String baseUrl,
            @Value("${exchangerates.api.access-key}") String accessKey) {
        this.baseUrl = baseUrl;
        this.accessKey = accessKey;
        this.restTemplate = new RestTemplate();
    }

    public BigDecimal convertToEur(Currency from, BigDecimal amount) {
        if (from == Currency.EUR) {
            return amount;
        }

        String url = String.format(
                "%s/latest?access_key=%s&base=EUR&symbols=%s",
                baseUrl,
                accessKey,
                from.name()
        );

        @SuppressWarnings("unchecked")
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        Boolean success = (Boolean) Objects.requireNonNull(response).get("success");
        if (success == null || !success) {
            throw new CurrencyConversionException("Failed to fetch the currency rate");
        }

        @SuppressWarnings("unchecked")
        Map<String, Number> rates = (Map<String, Number>) response.get("rates");

        Number rateNumber = rates.get(from.name());
        BigDecimal rate = BigDecimal.valueOf(rateNumber.doubleValue());

        return amount.divide(rate, 4, RoundingMode.HALF_UP);
    }

}
