package com.ajou.hertz.domain.instrument.repository;

import static com.ajou.hertz.domain.instrument.entity.QAcousticAndClassicGuitar.*;
import static com.ajou.hertz.domain.instrument.entity.QAmplifier.*;
import static com.ajou.hertz.domain.instrument.entity.QAudioEquipment.*;
import static com.ajou.hertz.domain.instrument.bass_guitar.entity.QBassGuitar.*;
import static com.ajou.hertz.domain.instrument.entity.QEffector.*;
import static com.ajou.hertz.domain.instrument.electric_guitar.entity.QElectricGuitar.*;
import static com.ajou.hertz.domain.user.entity.QUser.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.constant.InstrumentSortOption;
import com.ajou.hertz.domain.instrument.dto.request.AcousticAndClassicGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.AmplifierFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.AudioEquipmentFilterConditions;
import com.ajou.hertz.domain.instrument.bass_guitar.dto.request.BassGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.EffectorFilterConditions;
import com.ajou.hertz.domain.instrument.electric_guitar.dto.request.ElectricGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.InstrumentFilterConditions;
import com.ajou.hertz.domain.instrument.entity.AcousticAndClassicGuitar;
import com.ajou.hertz.domain.instrument.entity.Amplifier;
import com.ajou.hertz.domain.instrument.entity.AudioEquipment;
import com.ajou.hertz.domain.instrument.bass_guitar.entity.BassGuitar;
import com.ajou.hertz.domain.instrument.entity.Effector;
import com.ajou.hertz.domain.instrument.electric_guitar.entity.ElectricGuitar;
import com.ajou.hertz.domain.instrument.entity.Instrument;
import com.ajou.hertz.domain.user.entity.User;
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
		int page, int pageSize, InstrumentSortOption sort,
		ElectricGuitarFilterConditions filterConditions
	) {
		return findInstruments(
			page, pageSize, sort, filterConditions, electricGuitar, electricGuitar.seller,
			this::mapInstrumentFilterConditionsToPredicates
		);
	}

	@Override
	public Page<BassGuitar> findBassGuitars(
		int page, int pageSize, InstrumentSortOption sort,
		BassGuitarFilterConditions filterConditions
	) {
		return findInstruments(
			page, pageSize, sort, filterConditions, bassGuitar, bassGuitar.seller,
			this::mapInstrumentFilterConditionsToPredicates
		);
	}

	@Override
	public Page<AcousticAndClassicGuitar> findAcousticAndClassicGuitars(
		int page, int pageSize, InstrumentSortOption sort,
		AcousticAndClassicGuitarFilterConditions filterConditions
	) {
		return findInstruments(
			page, pageSize, sort, filterConditions, acousticAndClassicGuitar, acousticAndClassicGuitar.seller,
			this::mapInstrumentFilterConditionsToPredicates
		);
	}

	@Override
	public Page<Effector> findEffectors(
		int page, int pageSize, InstrumentSortOption sort,
		EffectorFilterConditions filterConditions
	) {
		return findInstruments(
			page, pageSize, sort, filterConditions, effector, effector.seller,
			this::mapInstrumentFilterConditionsToPredicates
		);
	}

	@Override
	public Page<Amplifier> findAmplifiers(
		int page, int pageSize, InstrumentSortOption sort,
		AmplifierFilterConditions filterConditions
	) {
		return findInstruments(
			page, pageSize, sort, filterConditions, amplifier, amplifier.seller,
			this::mapInstrumentFilterConditionsToPredicates
		);
	}

	@Override
	public Page<AudioEquipment> findAudioEquipments(
		int page, int pageSize, InstrumentSortOption sort,
		AudioEquipmentFilterConditions filterConditions
	) {
		return findInstruments(
			page, pageSize, sort, filterConditions, audioEquipment, audioEquipment.seller,
			this::mapInstrumentFilterConditionsToPredicates
		);
	}

	private <T extends Instrument, F extends InstrumentFilterConditions> Page<T> findInstruments(
		int page, int pageSize, InstrumentSortOption sort,
		F filterConditions,
		EntityPathBase<T> qInstrument,
		EntityPathBase<User> qSeller,
		Function<F, List<Predicate>> filterConverter
	) {
		PageRequest pageable = PageRequest.of(page, pageSize, sort.toSort());

		List<Predicate> conditions = new ArrayList<>(filterConverter.apply(filterConditions));

		List<T> content = queryFactory
			.selectFrom(qInstrument)
			.join(qSeller, user).fetchJoin()
			.where(conditions.toArray(new Predicate[0]))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(convertSortToOrderSpecifiers(pageable.getSort(), createPathBuilder(qInstrument)))
			.fetch();

		long totalCount = Optional.ofNullable(
			queryFactory
				.select(qInstrument.count())
				.from(qInstrument)
				.join(qSeller, user)
				.where(conditions.toArray(new Predicate[0]))
				.fetchOne()
		).orElse(0L);

		return new PageImpl<>(content, pageable, totalCount);
	}

	private <T extends Instrument, Q extends EntityPathBase<T>> PathBuilder<T> createPathBuilder(Q qClass) {
		return new PathBuilder<>(qClass.getType(), qClass.getMetadata());
	}

	private <T extends Instrument> OrderSpecifier<?>[] convertSortToOrderSpecifiers(
		Sort sort,
		PathBuilder<T> pathBuilder
	) {
		return sort.stream()
			.map(order -> new OrderSpecifier<>(
				order.getDirection().isAscending() ? Order.ASC : Order.DESC,
				pathBuilder.getString(order.getProperty())
			)).toArray(OrderSpecifier[]::new);
	}

	private List<Predicate> mapInstrumentFilterConditionsToPredicates(
		InstrumentFilterConditions filterConditions,
		EnumPath<InstrumentProgressStatus> progressStatusEntityPath,
		StringPath tradeAddressSidoExpression,
		StringPath tradeAddressSggExpression
	) {
		List<Predicate> res = new ArrayList<>();
		res.add(createEqualCondition(filterConditions.getProgress(), progressStatusEntityPath));
		res.add(createEqualCondition(filterConditions.getSido(), tradeAddressSidoExpression));
		res.add(createEqualCondition(filterConditions.getSgg(), tradeAddressSggExpression));
		return res;
	}

	private List<Predicate> mapInstrumentFilterConditionsToPredicates(ElectricGuitarFilterConditions filterConditions) {
		List<Predicate> res = mapInstrumentFilterConditionsToPredicates(
			filterConditions,
			electricGuitar.progressStatus,
			electricGuitar.tradeAddress.sido,
			electricGuitar.tradeAddress.sgg
		);
		res.add(createEqualCondition(filterConditions.getBrand(), electricGuitar.brand));
		res.add(createEqualCondition(filterConditions.getModel(), electricGuitar.model));
		res.add(createEqualCondition(filterConditions.getColor(), electricGuitar.color));
		return res;
	}

	private List<Predicate> mapInstrumentFilterConditionsToPredicates(BassGuitarFilterConditions filterConditions) {
		List<Predicate> res = mapInstrumentFilterConditionsToPredicates(
			filterConditions,
			bassGuitar.progressStatus,
			bassGuitar.tradeAddress.sido,
			bassGuitar.tradeAddress.sgg
		);
		res.add(createEqualCondition(filterConditions.getBrand(), bassGuitar.brand));
		res.add(createEqualCondition(filterConditions.getPickUp(), bassGuitar.pickUp));
		res.add(createEqualCondition(filterConditions.getPreAmplifier(), bassGuitar.preAmplifier));
		res.add(createEqualCondition(filterConditions.getColor(), bassGuitar.color));
		return res;
	}

	private List<Predicate> mapInstrumentFilterConditionsToPredicates(AcousticAndClassicGuitarFilterConditions filterConditions) {
		List<Predicate> res = mapInstrumentFilterConditionsToPredicates(
			filterConditions,
			acousticAndClassicGuitar.progressStatus,
			acousticAndClassicGuitar.tradeAddress.sido,
			acousticAndClassicGuitar.tradeAddress.sgg
		);
		res.add(createEqualCondition(filterConditions.getBrand(), acousticAndClassicGuitar.brand));
		res.add(createEqualCondition(filterConditions.getModel(), acousticAndClassicGuitar.model));
		res.add(createEqualCondition(filterConditions.getWood(), acousticAndClassicGuitar.wood));
		res.add(createEqualCondition(filterConditions.getPickUp(), acousticAndClassicGuitar.pickUp));
		return res;
	}

	private List<Predicate> mapInstrumentFilterConditionsToPredicates(EffectorFilterConditions filterConditions) {
		List<Predicate> res = mapInstrumentFilterConditionsToPredicates(
			filterConditions,
			effector.progressStatus,
			effector.tradeAddress.sido,
			effector.tradeAddress.sgg
		);
		res.add(createEqualCondition(filterConditions.getType(), effector.type));
		res.add(createEqualCondition(filterConditions.getFeature(), effector.feature));
		return res;
	}

	private List<Predicate> mapInstrumentFilterConditionsToPredicates(AmplifierFilterConditions filterConditions) {
		List<Predicate> res = mapInstrumentFilterConditionsToPredicates(
			filterConditions,
			amplifier.progressStatus,
			amplifier.tradeAddress.sido,
			amplifier.tradeAddress.sgg
		);
		res.add(createEqualCondition(filterConditions.getType(), amplifier.type));
		res.add(createEqualCondition(filterConditions.getBrand(), amplifier.brand));
		res.add(createEqualCondition(filterConditions.getUsage(), amplifier.usage));
		return res;
	}

	private List<Predicate> mapInstrumentFilterConditionsToPredicates(AudioEquipmentFilterConditions filterConditions) {
		List<Predicate> res = mapInstrumentFilterConditionsToPredicates(
			filterConditions,
			audioEquipment.progressStatus,
			audioEquipment.tradeAddress.sido,
			audioEquipment.tradeAddress.sgg
		);
		res.add(createEqualCondition(filterConditions.getType(), audioEquipment.type));
		return res;
	}

	private <T extends Comparable<T>> BooleanExpression createEqualCondition(T value, ComparableExpression<T> path) {
		return value != null ? path.eq(value) : null;
	}
}
