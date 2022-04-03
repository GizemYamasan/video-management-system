package com.emlakjet.videostore.domain;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Subscription {

	private SubscriptionType currentSubscription;
	private BigDecimal amount;
	private BigDecimal remainingAmount;

}
