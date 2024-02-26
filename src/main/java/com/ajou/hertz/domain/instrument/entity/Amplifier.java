package com.ajou.hertz.domain.instrument.entity;

import com.ajou.hertz.common.entity.Address;
import com.ajou.hertz.domain.instrument.constant.AmplifierBrand;
import com.ajou.hertz.domain.instrument.constant.AmplifierType;
import com.ajou.hertz.domain.instrument.constant.AmplifierUsage;
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
@DiscriminatorValue("AMPLIFIER")
@Entity
public class Amplifier extends Instrument {

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AmplifierType type;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AmplifierBrand brand;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AmplifierUsage ampUsage;

	private Amplifier(
		Long id,
		User seller,
		String title,
		InstrumentProgressStatus progressStatus,
		Address tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		AmplifierType type,
		AmplifierBrand brand,
		AmplifierUsage ampUsage
	) {
		super(id, seller, title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description);
		this.type = type;
		this.brand = brand;
		this.ampUsage = ampUsage;
	}

	public static Amplifier create(
		User seller,
		String title,
		Address tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		AmplifierType type,
		AmplifierBrand brand,
		AmplifierUsage usage
	) {
		return new Amplifier(
			null, seller, title, InstrumentProgressStatus.SELLING, tradeAddress,
			qualityStatus, price, hasAnomaly, description, type, brand, usage
		);
	}
}
