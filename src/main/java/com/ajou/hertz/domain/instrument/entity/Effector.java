package com.ajou.hertz.domain.instrument.entity;

import com.ajou.hertz.common.entity.Address;
import com.ajou.hertz.domain.instrument.constant.EffectorFeature;
import com.ajou.hertz.domain.instrument.constant.EffectorType;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue("EFFECTOR")
@Entity
public class Effector extends Instrument {

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EffectorType type;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EffectorFeature feature;

	private Effector(
		Long id,
		User seller,
		String title,
		InstrumentProgressStatus progressStatus,
		Address tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		EffectorType type,
		EffectorFeature feature
	) {
		super(id, seller, title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description);
		this.type = type;
		this.feature = feature;
	}

	public static Effector create(
		User seller,
		String title,
		InstrumentProgressStatus progressStatus,
		Address tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		EffectorType type,
		EffectorFeature feature
	) {
		return new Effector(
			null, seller, title, progressStatus, tradeAddress, qualityStatus,
			price, hasAnomaly, description, type, feature
		);
	}
}
