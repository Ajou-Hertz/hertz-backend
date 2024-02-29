package com.ajou.hertz.domain.instrument.entity;

import com.ajou.hertz.common.entity.Address;
import com.ajou.hertz.domain.instrument.constant.BassGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.BassGuitarPickUp;
import com.ajou.hertz.domain.instrument.constant.BassGuitarPreAmplifier;
import com.ajou.hertz.domain.instrument.constant.GuitarColor;
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
@DiscriminatorValue("BASS_GUITAR")
@Entity
public class BassGuitar extends Instrument {

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BassGuitarBrand brand;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BassGuitarPickUp pickUp;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BassGuitarPreAmplifier preAmplifier;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private GuitarColor color;

	private BassGuitar(
		Long id,
		User seller,
		String title,
		InstrumentProgressStatus progressStatus,
		Address tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		BassGuitarBrand brand,
		BassGuitarPickUp pickUp,
		BassGuitarPreAmplifier preAmplifier,
		GuitarColor color
	) {
		super(id, seller, title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description);
		this.brand = brand;
		this.pickUp = pickUp;
		this.preAmplifier = preAmplifier;
		this.color = color;
	}

	public static BassGuitar create(
		User seller,
		String title,
		InstrumentProgressStatus progressStatus,
		Address tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		BassGuitarBrand brand,
		BassGuitarPickUp pickUp,
		BassGuitarPreAmplifier preAmplifier,
		GuitarColor color
	) {
		return new BassGuitar(
			null, seller, title, progressStatus, tradeAddress, qualityStatus,
			price, hasAnomaly, description, brand, pickUp, preAmplifier, color
		);
	}
}
