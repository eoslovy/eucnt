package sejong.eucnt.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface CurrencyService {
    Map<String, String> getExchangeRates();
}
