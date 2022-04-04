package com.emlakjet.videostore.domain;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public enum SubscriptionType {

	//@formatter:off
	NO_SUBSCRIPTION(BigDecimal.valueOf(0)),
	PREMIUM(BigDecimal.valueOf(100)), 
	GOLD(BigDecimal.valueOf(50)), 
	STANDART(BigDecimal.valueOf(25));
	//@formatter:on

	private BigDecimal packagePrice;

	private SubscriptionType(BigDecimal packagePrice) {
		this.packagePrice = packagePrice;
	}

}
