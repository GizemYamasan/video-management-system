package com.emlakjet.videostore.domain;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Content {

	private Long id;
	@NotNull
	private BigDecimal price;
	@NotEmpty
	private String name;
	@NotNull
	private ContentType type;

}
