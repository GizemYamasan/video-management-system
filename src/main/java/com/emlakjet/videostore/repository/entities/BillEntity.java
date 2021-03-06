package com.emlakjet.videostore.repository.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bill")
public class BillEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bill_no_seq")
	@GenericGenerator(name = "bill_no_seq", strategy = "com.emlakjet.videostore.repository.BillNoSequenceIdentifier")
	private String billNo;
	private BigDecimal amount;
	private LocalDateTime createdAt;
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserEntity user;
	@OneToOne
	@JoinColumn(name = "content_id", referencedColumnName = "id")
	private ContentEntity content;

}
