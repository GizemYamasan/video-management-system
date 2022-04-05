package com.emlakjet.videostore.repository.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class SubscriptionEntity {

	@NotNull
	private SubscriptionTypeEntity subscriptionType;
	private BigDecimal remainingAmount;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
}
