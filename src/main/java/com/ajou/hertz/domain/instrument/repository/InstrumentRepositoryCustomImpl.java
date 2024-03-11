package com.ajou.hertz.domain.instrument.repository;

import static com.ajou.hertz.domain.instrument.entity.QBassGuitar.*;
import static com.ajou.hertz.domain.instrument.entity.QElectricGuitar.*;
import static com.ajou.hertz.domain.instrument.entity.QInstrument.*;
import static com.ajou.hertz.domain.user.entity.QUser.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.ajou.hertz.domain.instrument.constant.BassGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.BassGuitarPickUp;
import com.ajou.hertz.domain.instrument.constant.BassGuitarPreAmplifier;
import com.ajou.hertz.domain.instrument.constant.ElectricGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.ElectricGuitarModel;
import com.ajou.hertz.domain.instrument.constant.GuitarColor;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.constant.InstrumentSortOption;
import com.ajou.hertz.domain.instrument.dto.request.BassGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.ElectricGuitarFilterConditions;
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
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InstrumentRepositoryCustomImpl implements InstrumentRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<ElectricGuitar> findElectricGuitars(
		int page,
		int pageSize,
		InstrumentSortOption sort,
		ElectricGuitarFilterConditions filterConditions
	) {
		PageRequest pageable = PageRequest.of(page, pageSize, sort.toSort());

		List<Predicate> conditions =
			new ArrayList<>(convertElectricGuitarFilterConditionsToPredicates(filterConditions));

		List<ElectricGuitar> content = queryFactory
			.selectFrom(electricGuitar)
			.join(electricGuitar.seller, user).fetchJoin()
			.where(conditions.toArray(Predicate[]::new))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(convertSortToOrderSpecifiers(pageable.getSort(), createPathBuilder(electricGuitar)))
			.fetch();

		long totalCount = Optional.ofNullable(
			queryFactory.select(electricGuitar.count())
				.from(electricGuitar)
				.join(electricGuitar.seller, user)
				.where(conditions.toArray(Predicate[]::new))
				.fetchOne()
		).orElse(0L);

		return new PageImpl<>(content, pageable, totalCount);
	}

	@Override
	public Page<BassGuitar> findBassGuitars(
		int page,
		int pageSize,
		InstrumentSortOption sort,
		BassGuitarFilterConditions filterConditions
	) {
		PageRequest pageable = PageRequest.of(page, pageSize, sort.toSort());

		List<Predicate> conditions =
			new ArrayList<>(convertBassGuitarFilterConditionsToPredicates(filterConditions));

		List<BassGuitar> content = queryFactory
			.selectFrom(bassGuitar)
			.join(bassGuitar.seller, user).fetchJoin()
			.where(conditions.toArray(Predicate[]::new))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(convertSortToOrderSpecifiers(pageable.getSort(), createPathBuilder(bassGuitar)))
			.fetch();

		long totalCount = Optional.ofNullable(
			queryFactory.select(bassGuitar.count())
				.from(bassGuitar)
				.join(bassGuitar.seller, user)
				.where(conditions.toArray(Predicate[]::new))
				.fetchOne()
		).orElse(0L);

		return new PageImpl<>(content, pageable, totalCount);
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

		List<T> content = queryFactory
			.selectFrom(instrument)
			.join(instrument.seller, user).fetchJoin()
			.where(conditions.toArray(Predicate[]::new))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(convertSortToOrderSpecifiers(pageable.getSort(), createPathBuilder(instrument)))
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

	private <T extends Instrument, Q extends EntityPathBase<T>> PathBuilder<T> createPathBuilder(Q qClass) {
		return new PathBuilder<>(qClass.getType(), qClass.getMetadata());
	}

	private <T extends Instrument> OrderSpecifier<?>[] convertSortToOrderSpecifiers(
		Sort sort, PathBuilder<T> pathBuilder
	) {
		return sort.stream()
			.map(order -> new OrderSpecifier<>(
				order.getDirection().isAscending() ? Order.ASC : Order.DESC,
				pathBuilder.getString(order.getProperty())
			)).toArray(OrderSpecifier[]::new);
	}

	private List<Predicate> convertElectricGuitarFilterConditionsToPredicates(
		ElectricGuitarFilterConditions filterConditions
	) {
		List<Predicate> res = new ArrayList<>();
		res.add(applyProgressStatusCondition(filterConditions.getProgress(), electricGuitar.progressStatus));
		res.add(applyTradeAddressSidoCondition(filterConditions.getSido(), electricGuitar.tradeAddress.sido));
		res.add(applyTradeAddressSggCondition(filterConditions.getSgg(), electricGuitar.tradeAddress.sgg));
		res.add(applyElectricGuitarBrandCondition(filterConditions.getBrand()));
		res.add(applyElectricGuitarModelCondition(filterConditions.getModel()));
		res.add(applyGuitarColorCondition(filterConditions.getColor(), electricGuitar.color));
		return res;
	}

	private List<Predicate> convertBassGuitarFilterConditionsToPredicates(
		BassGuitarFilterConditions filterConditions
	) {
		List<Predicate> res = new ArrayList<>();
		res.add(applyProgressStatusCondition(filterConditions.getProgress(), bassGuitar.progressStatus));
		res.add(applyTradeAddressSidoCondition(filterConditions.getSido(), bassGuitar.tradeAddress.sido));
		res.add(applyTradeAddressSggCondition(filterConditions.getSgg(), bassGuitar.tradeAddress.sgg));
		res.add(applyBassGuitarBrandCondition(filterConditions.getBrand()));
		res.add(applyBassGuitarPickUpCondition(filterConditions.getPickUp()));
		res.add(applyBassGuitarPreAmplifier(filterConditions.getPreAmplifier()));
		res.add(applyGuitarColorCondition(filterConditions.getColor(), bassGuitar.color));
		return res;
	}

	private BooleanExpression applyProgressStatusCondition(
		InstrumentProgressStatus progressStatus,
		EnumPath<InstrumentProgressStatus> progressStatusExpression
	) {
		return createCondition(progressStatus, progressStatusExpression);
	}

	private BooleanExpression applyTradeAddressSidoCondition(String sido, StringPath tradeAddressSidoExpression) {
		return createCondition(sido, tradeAddressSidoExpression);
	}

	private BooleanExpression applyTradeAddressSggCondition(String sgg, StringPath tradeAddressSggExpression) {
		return createCondition(sgg, tradeAddressSggExpression);
	}

	private BooleanExpression applyElectricGuitarBrandCondition(ElectricGuitarBrand brand) {
		return createCondition(brand, electricGuitar.brand);
	}

	private BooleanExpression applyElectricGuitarModelCondition(ElectricGuitarModel model) {
		return createCondition(model, electricGuitar.model);
	}

	private BooleanExpression applyBassGuitarBrandCondition(BassGuitarBrand brand) {
		return createCondition(brand, bassGuitar.brand);
	}

	private BooleanExpression applyBassGuitarPickUpCondition(BassGuitarPickUp pickUp) {
		return createCondition(pickUp, bassGuitar.pickUp);
	}

	private BooleanExpression applyBassGuitarPreAmplifier(BassGuitarPreAmplifier preAmplifier) {
		return createCondition(preAmplifier, bassGuitar.preAmplifier);
	}

	private BooleanExpression applyGuitarColorCondition(GuitarColor color, EnumPath<GuitarColor> colorExpression) {
		return createCondition(color, colorExpression);
	}

	private <T extends Comparable<T>> BooleanExpression createCondition(T value, ComparableExpression<T> path) {
		return value != null ? path.eq(value) : null;
	}
}
