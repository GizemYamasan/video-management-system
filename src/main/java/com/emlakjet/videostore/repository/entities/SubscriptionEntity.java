package com.emlakjet.videostore.repository.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class SubscriptionEntity {

	private SubscriptionTypeEntity currentSubscription;
	private BigDecimal amount;
	private BigDecimal remainingAmount;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
}
