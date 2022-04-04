package com.emlakjet.videostore.repository.entities.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.emlakjet.videostore.domain.Bill;
import com.emlakjet.videostore.repository.entities.BillEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Mapper
public abstract class BillEntityMapper {

	private static final String PREFIX = "TR";
	private static final String BILL_NO_FORMAT = "%04d";

	public abstract Bill entityToDomain(BillEntity entity);

	public abstract BillEntity domainToEntity(Bill domain);

	public abstract List<Bill> entityToDomain(List<BillEntity> entityList);

	public abstract List<BillEntity> domainToEntity(List<Bill> domainList);

	@AfterMapping
	protected void convertNameToUpperCase(@MappingTarget Bill domain) {
		Long id = domain.getId();
		String billNo = PREFIX + String.format(BILL_NO_FORMAT, id);
		log.trace("bill no is generated:{}", billNo);
		domain.setBillNo(billNo);
	}
}
