package com.emlakjet.videostore.domain;

import java.math.BigDecimal;

public enum SubscriptionType {

	Premium(BigDecimal.valueOf(100)), Gold(BigDecimal.valueOf(50)), Standart(BigDecimal.valueOf(25));

	private BigDecimal packagePrice;

	private SubscriptionType(BigDecimal packagePrice) {
		this.packagePrice = packagePrice;
	}

}
