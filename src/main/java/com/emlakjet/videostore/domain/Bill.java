package com.emlakjet.videostore.domain;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Bill {

	private Long billNo;
	private BigDecimal amount;
	private User user;
	private Content content;

}
