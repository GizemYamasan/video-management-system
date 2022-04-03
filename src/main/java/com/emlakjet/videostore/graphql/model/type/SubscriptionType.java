package com.emlakjet.videostore.graphql.model.type;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public enum SubscriptionType {

	Premium(BigDecimal.valueOf(100)), Gold(BigDecimal.valueOf(50)), Standart(BigDecimal.valueOf(25));

	private BigDecimal packagePrice;

	private SubscriptionType(BigDecimal packagePrice) {
		this.packagePrice = packagePrice;
	}

}
