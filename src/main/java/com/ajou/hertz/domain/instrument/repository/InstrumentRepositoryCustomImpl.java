package com.ajou.hertz.domain.instrument.repository;

import static com.ajou.hertz.domain.instrument.entity.QInstrument.*;
import static com.ajou.hertz.domain.user.entity.QUser.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.constant.InstrumentSortOption;
import com.ajou.hertz.domain.instrument.dto.request.InstrumentFilterConditions;
import com.ajou.hertz.domain.instrument.entity.AcousticAndClassicGuitar;
import com.ajou.hertz.domain.instrument.entity.Amplifier;
import com.ajou.hertz.domain.instrument.entity.AudioEquipment;
import com.ajou.hertz.domain.instrument.entity.BassGuitar;
import com.ajou.hertz.domain.instrument.entity.Effector;
import com.ajou.hertz.domain.instrument.entity.ElectricGuitar;
import com.ajou.hertz.domain.instrument.entity.Instrument;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InstrumentRepositoryCustomImpl implements InstrumentRepositoryCustom {

	private static final PathBuilder<Instrument> INSTRUMENT_PATH = new PathBuilder<>(Instrument.class, "instrument");
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<ElectricGuitar> findElectricGuitars(
		int page,
		int pageSize,
		InstrumentSortOption sort,
		InstrumentFilterConditions filterConditions
	) {
		return findInstrumentsByClassType(ElectricGuitar.class, page, pageSize, sort, filterConditions);
	}

	@Override
	public Page<BassGuitar> findBassGuitars(
		int page,
		int pageSize,
		InstrumentSortOption sort,
		InstrumentFilterConditions filterConditions
	) {
		return findInstrumentsByClassType(BassGuitar.class, page, pageSize, sort, filterConditions);
	}

	@Override
	public Page<AcousticAndClassicGuitar> findAcousticAndClassicGuitars(
		int page,
		int pageSize,
		InstrumentSortOption sort,
		InstrumentFilterConditions filterConditions
	) {
		return findInstrumentsByClassType(AcousticAndClassicGuitar.class, page, pageSize, sort, filterConditions);
	}

	@Override
	public Page<Effector> findEffectors(
		int page,
		int pageSize,
		InstrumentSortOption sort,
		InstrumentFilterConditions filterConditions
	) {
		return findInstrumentsByClassType(Effector.class, page, pageSize, sort, filterConditions);
	}

	@Override
	public Page<Amplifier> findAmplifiers(
		int page,
		int pageSize,
		InstrumentSortOption sort,
		InstrumentFilterConditions filterConditions
	) {
		return findInstrumentsByClassType(Amplifier.class, page, pageSize, sort, filterConditions);
	}

	@Override
	public Page<AudioEquipment> findAudioEquipments(
		int page,
		int pageSize,
		InstrumentSortOption sort,
		InstrumentFilterConditions filterConditions
	) {
		return findInstrumentsByClassType(AudioEquipment.class, page, pageSize, sort, filterConditions);
	}

	private <T extends Instrument> Page<T> findInstrumentsByClassType(
		Class<T> classType,
		int page,
		int pageSize,
		InstrumentSortOption sort,
		InstrumentFilterConditions filterConditions
	) {
		PageRequest pageable = PageRequest.of(page, pageSize, sort.toSort());

		List<Predicate> conditions = new ArrayList<>();
		conditions.add(instrument.instanceOf(classType));
		conditions.addAll(convertFilterConditions(filterConditions));

		List<T> content = queryFactory
			.selectFrom(instrument)
			.join(instrument.seller, user).fetchJoin()
			.where(conditions.toArray(Predicate[]::new))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(convertSortToOrderSpecifiers(pageable.getSort()))
			.fetch()
			.stream()
			.map(classType::cast)
			.toList();

		long totalCount = Optional.ofNullable(
			queryFactory.select(instrument.count())
				.from(instrument)
				.join(instrument.seller, user)
				.where(conditions.toArray(Predicate[]::new))
				.fetchOne()
		).orElse(0L);

		return new PageImpl<>(content, pageable, totalCount);
	}

	private OrderSpecifier<?>[] convertSortToOrderSpecifiers(Sort sort) {
		return sort.stream()
			.map(order -> new OrderSpecifier<>(
				order.getDirection().isAscending() ? Order.ASC : Order.DESC,
				INSTRUMENT_PATH.getString(order.getProperty())
			)).toArray(OrderSpecifier[]::new);
	}

	private List<Predicate> convertFilterConditions(InstrumentFilterConditions filterConditions) {
		List<Predicate> res = new ArrayList<>();
		res.add(progressStatusFilterCondition(filterConditions.getProgress()));
		return res;
	}

	private BooleanExpression progressStatusFilterCondition(InstrumentProgressStatus progressStatus) {
		return progressStatus != null ? instrument.progressStatus.eq(progressStatus) : null;
	}
}
