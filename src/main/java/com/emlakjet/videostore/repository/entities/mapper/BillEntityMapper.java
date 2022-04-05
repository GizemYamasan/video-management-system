package com.emlakjet.videostore.repository.entities.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.emlakjet.videostore.domain.Bill;
import com.emlakjet.videostore.repository.entities.BillEntity;

@Mapper(componentModel = "spring")
public abstract class BillEntityMapper {

	public abstract Bill entityToDomain(BillEntity entity);

	public abstract BillEntity domainToEntity(Bill domain);

	public abstract List<Bill> entityToDomain(List<BillEntity> entityList);

	public abstract List<BillEntity> domainToEntity(List<Bill> domainList);
}
