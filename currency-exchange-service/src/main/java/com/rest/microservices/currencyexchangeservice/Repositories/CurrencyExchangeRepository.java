package com.rest.microservices.currencyexchangeservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.microservices.currencyexchangeservice.Model.CurrencyExchange;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {
	CurrencyExchange findByFromAndTo(String from,String to);
}
