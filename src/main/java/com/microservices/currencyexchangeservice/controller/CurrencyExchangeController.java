package com.microservices.currencyexchangeservice.controller;

import com.microservices.currencyexchangeservice.model.CurrencyExchange;
import com.microservices.currencyexchangeservice.model.CurrencyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    private final Environment environment;
    private CurrencyExchangeRepository currencyExchangeRepository;
    @Autowired
    public CurrencyExchangeController(Environment environment, CurrencyExchangeRepository currencyExchangeRepository) {
        this.environment = environment;
        this.currencyExchangeRepository = currencyExchangeRepository;

    }


    @GetMapping("currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchange(@PathVariable String from, @PathVariable String to){

        String port = environment.getProperty("local.server.port");
        CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from,to);
        currencyExchange.setEnvironment(port);
        if (currencyExchange == null) {
            throw new RuntimeException("Cannot find data for "+ from + "from " + "to" + to);
        }

        return currencyExchange;

    }
}
