package com.emlakjet.videostore.graphql.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.emlakjet.videostore.graphql.model.type.Bill;

@Mapper(componentModel = "spring")
public interface BillTypeMapper {

	Bill toType(com.emlakjet.videostore.domain.Bill domain);

	List<Bill> toType(List<com.emlakjet.videostore.domain.Bill> domain);

	com.emlakjet.videostore.domain.Bill toDomain(Bill type);
}
