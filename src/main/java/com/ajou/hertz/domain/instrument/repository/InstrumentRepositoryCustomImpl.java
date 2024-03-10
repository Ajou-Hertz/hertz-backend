package com.ajou.hertz.domain.instrument.repository;

import static com.ajou.hertz.domain.instrument.entity.QInstrument.*;
import static com.ajou.hertz.domain.user.entity.QUser.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.ajou.hertz.domain.instrument.entity.ElectricGuitar;
import com.ajou.hertz.domain.instrument.entity.Instrument;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class InstrumentRepositoryCustomImpl implements InstrumentRepositoryCustom {

	private final JPAQueryFactory queryFactory;
	private static final PathBuilder<Instrument> INSTRUMENT_PATH = new PathBuilder<>(Instrument.class, "instrument");

	public InstrumentRepositoryCustomImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public Page<ElectricGuitar> findElectricGuitars(Pageable pageable) {
		List<ElectricGuitar> content = queryFactory.selectFrom(instrument)
			.join(instrument.seller, user)
			.fetchJoin()
			.where(instrument.instanceOf(ElectricGuitar.class))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(convertSortToOrderSpecifiers(pageable.getSort()))
			.fetch()
			.stream()
			.map(ElectricGuitar.class::cast)
			.toList();

		long totalCount = Optional.ofNullable(queryFactory.select(instrument.count())
			.from(instrument)
			.join(instrument.seller, user)
			.where(instrument.instanceOf(ElectricGuitar.class))
			.fetchOne()).orElse(0L);

		return new PageImpl<>(content, pageable, totalCount);
	}

	public OrderSpecifier<?>[] convertSortToOrderSpecifiers(Sort sort) {
		return sort.stream()
			.map(order -> new OrderSpecifier<>(order.getDirection().isAscending() ? Order.ASC : Order.DESC,
				INSTRUMENT_PATH.getString(order.getProperty())))
			.toArray(OrderSpecifier[]::new);
	}

}
