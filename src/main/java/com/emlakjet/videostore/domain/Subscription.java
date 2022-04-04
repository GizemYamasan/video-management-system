package com.emlakjet.videostore.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {

	private SubscriptionType subscriptionType;
	private BigDecimal remainingAmount;
	private LocalDateTime startDate;
	private LocalDateTime endDate;

}
