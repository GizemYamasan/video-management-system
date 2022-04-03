package com.emlakjet.videostore.repository.entities;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public enum SubscriptionTypeEntity {

	Premium(BigDecimal.valueOf(100)), Gold(BigDecimal.valueOf(50)), Standart(BigDecimal.valueOf(25));

	private BigDecimal packagePrice;

	private SubscriptionTypeEntity(BigDecimal packagePrice) {
		this.packagePrice = packagePrice;
	}

}
