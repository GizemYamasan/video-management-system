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
public class Bill {

	private Long id;
	private String billNo;
	private BigDecimal amount;
	private LocalDateTime createdAt;
	private User user;
	private Content content;

}
