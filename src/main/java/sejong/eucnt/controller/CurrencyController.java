package sejong.eucnt.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sejong.eucnt.service.CurrencyService;

import java.util.Map;

@RestController
@Slf4j
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/exchange-rates")
    public Map<String, String> getExchangeRates() {
        return currencyService.getExchangeRates();
    }
}
