package sejong.eucnt.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {

    private static final String URL = "https://finance.naver.com/marketindex/exchangeList.nhn";

    @Override
    public Map<String, String> getExchangeRates() {
        Map<String, String> exchangeRates = new HashMap<>();

        try {
            Document doc = Jsoup.connect(URL).get();
            Elements countries = doc.select(".tit");
            Elements sales = doc.select(".sale");

            for (int i = 0; i < countries.size(); i++) {
                String countryName = countries.get(i).text();
                String saleValue = sales.get(i).text();

                if (countryName.contains("USD") || countryName.contains("EUR") || countryName.contains("GBP")) {
                    exchangeRates.put(countryName, saleValue);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return exchangeRates;
    }
}